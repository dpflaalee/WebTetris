import { drawFullBoard, drawNext } from './common-game.js';

// URL 쿼리에서 roomId, userId 추출
const qs = new URLSearchParams(location.search);
const roomId = qs.get('roomId');
const myUserId = qs.get('userId');

// DOM 참조
const myIdEl = document.getElementById("me-id");
const myCanvas = document.getElementById("my-canvas");
const myNextCanvas = document.getElementById("my-next");
const myScoreEl = document.getElementById("my-score");
//const opponentsWrap = document.getElementById("opponents");
const overlay = document.getElementById("overlay");
const overlayMsg = overlay?.querySelector(".msg");
const pauseBtn = document.getElementById("pauseResumeBtn");

myIdEl.textContent = myUserId || "unknown";

let stompClient = null;
let connected = false;
let isHost = false;
let paused = false;
const opponentViews = new Map();

//연결-불러오기
function connect() {
  const socket = new SockJS('/ws');
  stompClient = Stomp.over(socket);
  stompClient.debug = null;

  stompClient.connect({}, () => {
    connected = true;

    // 방 게임 상태 불러오기
    stompClient.subscribe(`/topic/game/${roomId}`, message => {
      const states = JSON.parse(message.body);
      renderAll(states);
    });

    //일시정지용 추가... 방 정보 불러오기
    stompClient.subscribe(`/topic/room/${roomId}`, message => {
      const info = JSON.parse(message.body);
      isHost = info.hostId === myUserId;
      pauseBtn.style.display = isHost ? "inline-block" : "none";
    });
    
    //종료 후 순위
    stompClient.subscribe(`/topic/game/${roomId}`, message => {
      const payload = JSON.parse(message.body);

      const states = payload.states ?? {};
      renderAll(states, true);

      if (payload.rankings) {
        const resultHtml = payload.rankings.map((entry, idx) =>
          `<div>${idx + 1}위: ${entry.userId} - ${entry.score}점</div>`).join("");
        showOverlay(resultHtml + 
          "<div><button data-action='exit' class='btn btn-primary mt-3 w-100' style='display:block;'>나가기</button></div>");
      }
    });

    //일시정지용 추가... 방 정보 요청
    stompClient.send("/app/room/info", {}, JSON.stringify({ roomId, userId: myUserId }));

    //낙하 속도
    setInterval(() => {
      if (!connected || !roomId || !myUserId) return;
      sendMove('down');
    }, 500);
  });
}

//나가기 버튼
document.addEventListener("click", function(e){
  if(e.target.dataset.action==="exit"){
    stompClient.send("/app/room/leave", {}, JSON.stringify({roomId, userId:myUserId}));
    window.location.href="/game/lobby";
  }
});

//이동방향 
function sendMove(direction) {
  if (!connected) return;
  const payload = { roomId, userId: myUserId, direction };
  const endpoint = direction === "drop" ? "/app/drop" : "/app/move";
  stompClient.send(endpoint, {}, JSON.stringify(payload));
}

// 전체 상태
function renderAll(states, isFinalResult = false) {
  // 내 상태
  const me = states[myUserId];
  if (me) {
    drawFullBoard(myCanvas, me.board, me.current, me.ghost, me.color);
    myScoreEl.textContent = me.score ?? 0;
    drawNext(myNextCanvas, me.nextBlocks, me.nextColor);

    //오버레이
    if(!isFinalResult){
      if (me.gameOver) { showOverlay("GAME OVER");
      } else if (me.paused) { showOverlay("PAUSED"); 
      } else { hideOverlay(); }
    }
  }
  //방장 일시정지 버튼 
  if (isHost) {
    const anyPaused = Object.values(states).some(s => s.paused);
    paused = anyPaused;
    pauseBtn.innerText = paused ? "게임 재개" : "일시 정지";
  }

  //상대 보드 랜더링
  const ids = Object.keys(states).filter(id => id !== myUserId);
  //syncOpponentViews(ids);

  ids.forEach(pid => {
    const st = states[pid];
    const view = opponentViews.get(pid);
    if (!view) return;
    view.labelEl.textContent = pid;
    drawFullBoard(view.canvas, st.board, st.current, st.ghost, st.color);
    view.scoreEl.textContent = `점수: ${st.score ?? 0}`;
  });
}

// 상대 보드 초기화
function initOpponentViews() {
  const opponentElements = document.querySelectorAll("#opponents .opponent");
  opponentElements.forEach(el => {
    const userId = el.dataset.userId;
    const canvas = el.querySelector(".canvas");
    const scoreEl = el.querySelector(".score");
    const labelEl = el.querySelector(".label");
    opponentViews.set(userId, { canvas, scoreEl, labelEl });
  });
}

//일시정지 버튼
pauseBtn.addEventListener("click", () => {
  if (!isHost) return;
  const payload = JSON.stringify({ roomId, userId: myUserId });

  if (!paused) { stompClient.send("/app/pause", {}, payload);
  } else { stompClient.send("/app/resume", {}, payload); }

  paused = !paused;
  pauseBtn.innerText = paused ? "재개" : "일시 정지";
});

function showOverlay(html) { overlay.style.display = "flex"; overlayMsg.innerHTML = html;}
function hideOverlay() { overlay.style.display = "none"; }

// 키 입력
document.addEventListener("keydown", e => {
  if (!connected) return;
  if (e.key === "ArrowLeft") sendMove("left");
  else if (e.key === "ArrowRight") sendMove("right");
  else if (e.key === "ArrowDown") sendMove("down");
  else if (e.key === "ArrowUp") sendMove("rotate");
  else if (e.key === " ") {
    e.preventDefault();
    sendMove("drop");
  }
});


connect();
initOpponentViews();