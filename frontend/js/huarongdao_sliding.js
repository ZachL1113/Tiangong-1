const BASE_URL = 'http://127.0.0.1:8080';


/** 
 * @param {string} game
 * @param {number} [level]
 * @returns {Promise<Object>}
*/
async function getNewGame(game, level = null) {
  let uri = `/games/${game}/new`;
  if (game === 'huarongdao' && level !== null) {
    uri += `?level=${level}`;
  }
  return await getData(uri);
}

/**
 * 发送用户移动操作
 * @param {string} game - 游戏名，如 "2048" 或 "huarongdao"
 * @param {string} boardJson - 当前棋盘状态（JSON 字符串）
 * @param {string} direction - 方向：U / D / L / R（或 W/A/S/D）
 * @param {number} pieceId - 棋子编号（仅部分游戏使用）
 * @returns {Promise<Object>} 返回 { boardJson, valid, success }
 */

async function sendMove(game, boardJson, direction, pieceId) {
  return await postData(`/games/${game}/move`, {
    boardJson,
    direction,
    pieceId,
  });
}

async function getData(uri) {
  try {
    const response = await fetch(BASE_URL + uri);
    if (!response.ok) throw new Error('网络响应不正常');
    return await response.json();
  } catch (error) {
    console.error('获取数据失败:', error);
    throw error;
  }
}

async function postData(uri, params) {
  try {
    const response = await fetch(BASE_URL + uri, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(params),
    });
    if (!response.ok) throw new Error('网络响应不正常');
    return await response.json();
  } catch (error) {
    console.error('发送数据失败:', error);
    throw error;
  }
}

function huarongdao_print(data) {
  const boardDiv = document.getElementById("board");
  boardDiv.innerHTML = '';
  boardDiv.className = 'board';
  boardDiv.style.position = "relative";

  const bg = document.createElement("img");
  bg.src = "../image/board.png";
  bg.style.position = "absolute";
  bg.style.top = "0";
  bg.style.left = "0";
  bg.style.width = "100%";
  bg.style.height = "100%";
  bg.style.zIndex = "0";
  bg.style.pointerEvents = "none";
  boardDiv.appendChild(bg);

  const container = document.createElement("div");
  container.className = "pieces";
  container.style.position = "absolute";
  container.style.zIndex = "1";

  data.pieces.forEach(piece => {
    const div = document.createElement("div");
    div.className = `w${piece.w} h${piece.h}`;
    div.style.position = "absolute";
    div.style.top = `${piece.y * 112}px`;
    div.style.left = `${piece.x * 112}px`;
    div.dataset.id = piece.id;
    div.dataset.name = piece.name;

    const img = document.createElement("img");
    if (piece.w === 1 && piece.h === 2) {
      img.src = `../image/${piece.name}_h.png`;
    } else if (piece.w === 2 && piece.h === 1) {
      img.src = `../image/${piece.name}_w.png`;
    } else {
      img.src = `../image/${piece.name}.png`;
    }
    img.style.width = "100%";
    img.style.height = "100%";
    img.alt = piece.name;

    div.appendChild(img);
    container.appendChild(div);
  });

  boardDiv.appendChild(container);
}

function huarongdao_slide() {
  const board = document.getElementById("board");

  board.addEventListener("mousedown", e => {
    const piece = e.target.closest(".pieces > div");
    if (!piece) return;

    e.preventDefault();
    startX = e.clientX;
    startY = e.clientY;
    isDragging = true;
    activePieceId = piece.dataset.id;
    document.body.style.cursor = "grabbing";
  });

  document.addEventListener("mousemove", e => {
    if (!isDragging) return;
    const dx = e.clientX - startX;
    const dy = e.clientY - startY;
    const piece = document.querySelector(`[data-id="${activePieceId}"]`);
    if (piece) {
      piece.style.transform = `translate(${dx}px, ${dy}px)`;
    }
  });

  document.addEventListener("mouseup", e => {
    if (!isDragging || !activePieceId) return;

    const dx = e.clientX - startX;
    const dy = e.clientY - startY;
    const threshold = 10;
    let direction = "";

    if (Math.abs(dx) < threshold && Math.abs(dy) < threshold) {
      direction = "";
    } else if (Math.abs(dx) > Math.abs(dy)) {
      direction = dx > 0 ? "R" : "L";
    } else {
      direction = dy > 0 ? "D" : "U";
    }

    const piece = document.querySelector(`[data-id="${activePieceId}"]`);
    if (piece) piece.style.transform = "";
    document.body.style.cursor = "default";

    document.getElementById("Direction").textContent = 
      `Piece: ${activePieceId}, Direction: ${direction}`;

    if (direction !== "") {
      sendMove("huarongdao", JSON.stringify(currentBoard), direction, activePieceId)
        .then(result => {
          if (result.valid) {
            currentBoard = JSON.parse(result.boardJson);
            huarongdao_print(currentBoard);
            bindDragEvents();
            if (result.success) {
              alert("success!");
            }
          } else {
            alert("illegal!");
          }
        })
        .catch(err => {
          console.error("Move failed:", err);
        });
    }

    isDragging = false;
    activePieceId = null;
  });
}
