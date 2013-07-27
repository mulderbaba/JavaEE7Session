/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

var wsUri = "ws://" + document.location.host + document.location.pathname + "whiteboardendpoint";
var websocket = new WebSocket(wsUri);
websocket.binaryType = "arraybuffer";

websocket.onmessage = function(evt) { onMessage(evt) };
websocket.onerror = function(evt) { onError(evt) };

function sendText(json) {
    console.log("sending text: " + json);
    websocket.send(json);
}

function sendBinary(bytes) {
    console.log("sending binary: " + Object.prototype.toString.call(bytes));
    websocket.send(bytes);
}
                
function onMessage(evt) {
    console.log("received: " + evt.data);
    if (typeof evt.data == "string") {
        drawImageText(evt.data);
    } else {
        drawImageBinary(evt.data);
    }
}

function onError(evt) {
    writeToScreen('<span style="color: red;">ERROR:</span> ' + evt.data);
}
