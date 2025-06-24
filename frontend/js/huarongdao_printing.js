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
