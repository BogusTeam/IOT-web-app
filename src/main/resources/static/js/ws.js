var stompClient = null;

function connect() {
    var socket = new SockJS('/message-ws');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
    });
}

function sendName() {
    stompClient.send("/app/send", {}, JSON.stringify({
        'deviceId': document.getElementById("name").innerText,
        'value': document.getElementById("orig_name").innerText
    }));
}

$(function () {
    $("#send_button").click(function () {
        sendName();
    });
});

connect();
window.scrollTo(0, document.body.scrollHeight);