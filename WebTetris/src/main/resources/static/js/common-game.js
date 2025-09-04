// 공통 상수
const BLOCK_SIZE = 30;

// 공통 렌더링 함수
export function drawFullBoard(canvas, board, current, ghost, color = "#FF5555") {
  const ctx = canvas.getContext("2d");
  const width = board[0]?.length || 10;
  const height = board.length || 20;
  const cell = Math.min(canvas.width / width, canvas.height / height);

  ctx.clearRect(0, 0, canvas.width, canvas.height);
  ctx.fillStyle = "#fafafa";
  ctx.fillRect(0, 0, canvas.width, canvas.height);

  for (let y = 0; y < height; y++) {
    for (let x = 0; x < width; x++) {
      if (board[y][x] !== 0) {
        fillCell(ctx, x, y, cell, "#1E88E5");
      }
    }
  }

  ctx.globalAlpha = 0.26;
  ghost.forEach(p => fillCell(ctx, p.x, p.y, cell, "#999"));
  ctx.globalAlpha = 1.0;

  current.forEach(p => fillCell(ctx, p.x, p.y, cell, color));
  drawGrid(ctx, width, height, cell);
}

export function fillCell(ctx, x, y, cell, fill) {
  ctx.fillStyle = fill;
  ctx.fillRect(x * cell, y * cell, cell, cell);
  ctx.strokeStyle = "rgba(0,0,0,0.1)";
  ctx.strokeRect(x * cell, y * cell, cell, cell);
}

export function drawGrid(ctx, width, height, cell) {
  ctx.strokeStyle = "rgba(0,0,0,0.05)";
  ctx.lineWidth = 1;
  for (let x = 0; x <= width; x++) {
    ctx.beginPath();
    ctx.moveTo(x * cell, 0);
    ctx.lineTo(x * cell, height * cell);
    ctx.stroke();
  }
  for (let y = 0; y <= height; y++) {
    ctx.beginPath();
    ctx.moveTo(0, y * cell);
    ctx.lineTo(width * cell, y * cell);
    ctx.stroke();
  }
}

export function drawNext(canvas, nextBlocks = [], nextColor = "#999") {
  const ctx = canvas.getContext("2d");
  ctx.clearRect(0, 0, canvas.width, canvas.height);
  if (!Array.isArray(nextBlocks) || nextBlocks.length === 0) return;

  const xs = nextBlocks.map(p => p.x);
  const ys = nextBlocks.map(p => p.y);
  const minX = Math.min(...xs), maxX = Math.max(...xs);
  const minY = Math.min(...ys), maxY = Math.max(...ys);
  const w = maxX - minX + 1;
  const h = maxY - minY + 1;

  const cell = Math.floor(Math.min(canvas.width / (w + 1), canvas.height / (h + 1)));
  const offsetX = Math.floor((canvas.width - w * cell) / 2);
  const offsetY = Math.floor((canvas.height - h * cell) / 2);

  ctx.fillStyle = nextColor;
  nextBlocks.forEach(p => {
    const dx = p.x - minX;
    const dy = p.y - minY;
    ctx.fillRect(offsetX + dx * cell, offsetY + dy * cell, cell, cell);
  });
}