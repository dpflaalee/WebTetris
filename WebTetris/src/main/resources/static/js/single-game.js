import { drawFullBoard, drawNext } from './common-game.js';
document.addEventListener("DOMContentLoaded", ()=>{
    //const ctx = canvas.getContext("2d");
    const canvas = document.getElementById("gameCanvas");
    const previewCanvas = document.getElementById("previewCanvas");
    const pauseResumeBtn = document.getElementById("pauseResumeBtn");
    const startRestartBtn = document.getElementById("startRestartBtn");
    
    function update() {
        fetch("/game/state")
        .then(res => res.json())
        .then(data => {
            drawFullBoard(canvas, data.board, data.current, data.ghost, data.color);
            drawNext(previewCanvas, data.nextBlocks, data.nextColor);
            document.getElementById("score").innerText = data.score;
            document.getElementById("gameOverMessage").style.display = data.gameOver ? "block" : "none";

            if (data.current.length === 0 || data.gameOver) {
            startRestartBtn.innerText = "게임 시작";
            pauseResumeBtn.disabled = true;
            } else {
            startRestartBtn.innerText = "재시작";
            pauseResumeBtn.disabled = false;
            pauseResumeBtn.innerText = data.paused ? "게임 재개" : "일시 정지";
            }
        });
    }

  //일시정지 재개
  pauseResumeBtn.addEventListener("click", () => {
    fetch("/game/state")
      .then(res => res.json())
      .then(data => {
        const url = data.paused ? "/game/resume" : "/game/pause";
        fetch(url, { method: "POST" }).then(update);
      });
  });

  //시작
  startRestartBtn.addEventListener("click", () => {
    fetch("/game/state")
      .then(res => res.json())
      .then(data => {
        const url = data.paused ? "/game/spawn" : "/game/restart";
        fetch(url, { method: "POST" }).then(update);
      });
  });

  //키조작
  document.addEventListener("keydown", e => {
    fetch("/game/state")
      .then(res => res.json())
      .then(data => {
        if (data.paused || data.gameOver) return;

        let direction = null;
        if (e.key === "ArrowLeft") direction = "left";
        if (e.key === "ArrowRight") direction = "right";
        if (e.key === "ArrowDown") direction = "down";
        if (e.key === "ArrowUp") direction = "rotate";
        if (e.key === " ") {
          fetch("/game/drop", { method: "POST" }).then(update);
          return;
        }
        if (direction) {
          fetch(`/game/move?direction=${direction}`, { method: "POST" }).then(update);
        }
      });
  });

  //낙하속도
  setInterval(() => {
    fetch("/game/state")
      .then(res => res.json())
      .then(data => {
        if (!data.paused && !data.gameOver) {
          fetch("/game/move?direction=down", { method: "POST" });
        }
        update();
      });
  }, 500);
});