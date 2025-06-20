function huarongdao_print(data) {
  const boardDiv = document.getElementById("board");
  boardDiv.innerHTML = '';
  boardDiv.className = 'board';

  const piecesContainer = document.createElement("div");
  piecesContainer.className = "pieces";

  data.pieces.forEach(piece => {
    const pieceDiv = document.createElement("div");
    pieceDiv.className = `w${piece.w} h${piece.h}`;
    pieceDiv.style.top = `${piece.y * 112}px`;
    pieceDiv.style.left = `${piece.x * 112}px`;
    pieceDiv.dataset.id = piece.id;
    pieceDiv.dataset.name = piece.name;

    const img = document.createElement("img");
    if(piece.w == 1 && piece.h == 2) {
      img.src = `image/${piece.name}_h.png`;
    }else if(piece.w == 2 && piece.h == 1) {
      img.src = `image/${piece.name}_w.png`;
    }else{
      img.src = `image/${piece.name}.png`;
    }
    img.alt = piece.name;

    pieceDiv.appendChild(img);
    piecesContainer.appendChild(pieceDiv);

    pieceDiv.addEventListener("mousedown", function (e) {
      e.preventDefault();
      startX = e.clientX;
      startY = e.clientY;
      isDragging = true;
      activePieceId = piece.id;
    });

    pieceDiv.ondragstart = () => false;
  });

  boardDiv.appendChild(piecesContainer);
}