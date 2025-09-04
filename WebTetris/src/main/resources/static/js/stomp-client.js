const socket = new SockJS('/ws');
const stompClient = Stomp.over(socket);

stompClient.connect({}, ()=>{
    stompClient.subscribe('/topic/gameState', (message)=>{
        const gameState = JSON.parse(message.body);
        renderGame(gameState);
    });

    stompClient.subscribe('/topic/restartGame', (message)=>{
        const gameState = JSON.parse(message.body);
        renderGame(gameState);
    });

    document.addEventListener('keydown', (e)=>{
        switch(e.key){
            case 'ArrowLeft': stompClient.send('/app/move', {}, 'left'); break;
            case 'ArrowRight': stompClient.send('/app/move', {}, 'right'); break;
            case 'ArrowDown': stompClient.send('/app/move', {}, 'down'); break;
            case 'ArrowUp': stompClient.send('/app/move', {}, 'rotate'); break;
            case ' ': stompClient.send('/app/drop', {}, ''); break;
        }
    });
});