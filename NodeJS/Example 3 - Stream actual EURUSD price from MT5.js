// Example 3 - Stream actual EURUSD price from MT5
//
// PREREQUISITE: Install MTsocketAPI in your Metatrader 5 using the following link https://www.mtsocketapi.com/doc5/installation.html
//
// WARNING: All these source codes are only examples for testing purposes. 
// WARNING: We don’t provide any guarantee or responsibility about it. 
// WARNING: Use these examples at your own risk.

const Net = require('net');

const client_cmd = new Net.Socket();
const client_data = new Net.Socket();

client_cmd.connect(77, "localhost", function() {
            client_cmd.write('{"MSG":"TRACK_PRICES","SYMBOLS":["EURUSD"]}' + '\r\n');
});

client_cmd.on('data', function(chunk) {
            console.log(`${chunk.toString()}`);
            client_cmd.end();
});

client_data.connect(78, "localhost");

client_data.on('data', function(chunk) {
                    console.log(`${chunk.toString()}`);
});

