var stompClient = null;

function connect() {
    var socket = new SockJS('/message-ws');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
    });
}

document.getElementById("motionOne").addEventListener("click", e => {
    send(0, "1");
});

document.getElementById("voltageChange").addEventListener("click", e => {
   send(2, document.getElementById("voltageQuantity").value);
});

function send(deviceId, value) {
    stompClient.send("/app/send", {}, JSON.stringify({
        'deviceId': deviceId,
        'value': value
    }));
}

connect();