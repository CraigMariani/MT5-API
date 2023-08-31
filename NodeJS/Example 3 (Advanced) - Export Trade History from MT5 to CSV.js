// Example 3 (Advanced) - Export Trade History from MT5 to CSV
//
// PREREQUISITE: Install MTsocketAPI in your Metatrader 5 using the following link https://www.mtsocketapi.com/doc5/installation.html
//
// WARNING: All these source codes are only examples for testing purposes. 
// WARNING: We don’t provide any guarantee or responsibility about it. 
// WARNING: Use these examples at your own risk.

const Net = require('net');
const fs = require('fs');
const delimiter = ',';
const client_cmd = new Net.Socket();

client_cmd.connect(77, "localhost", function() {
        const JSONobj = {};
        JSONobj["MSG"]="TRADE_HISTORY";
        JSONobj["FROM_DATE"]="2022/09/19 13:00:00";
        JSONobj["TO_DATE"]="2022/09/20 00:00:00";
        JSONobj["MODE"]="POSITIONS";
        client_cmd.write(JSON.stringify(JSONobj) + '\r\n');
});

client_cmd.on('data', function(chunk) {
        const JSONresult = JSON.parse(chunk.toString());
        console.log(chunk.toString());
        console.log(`Number of trades: ${Object.keys(JSONresult['POSITIONS']).length}`);
        fs.writeFileSync('tradeHistory.csv','OPEN_TIME,CLOSE_TIME,TICKET,SYMBOL,PRICE_OPEN,PRICE_CLOSE,PROFIT\r\n',{ flag: 'a+' });
        for(let i = 0; i < Object.keys(JSONresult['POSITIONS']).length; i++) {
                //console.log(JSONresult['TRADES'][i]['OPEN_TIME']);
                const OPEN_TIME = JSONresult['POSITIONS'][i]['OPEN_TIME'];
                const CLOSE_TIME = JSONresult['POSITIONS'][i]['CLOSE_TIME'];
                const TICKET = JSONresult['POSITIONS'][i]['TICKET'];
                const SYMBOL = JSONresult['POSITIONS'][i]['SYMBOL'];
                const PRICE_OPEN = JSONresult['POSITIONS'][i]['PRICE_OPEN'];
                const PRICE_CLOSE = JSONresult['POSITIONS'][i]['PRICE_CLOSE'];
                const PROFIT = JSONresult['POSITIONS'][i]['PROFIT'];
                fs.writeFileSync('tradeHistory.csv', OPEN_TIME + delimiter + CLOSE_TIME + delimiter + TICKET + delimiter + SYMBOL + delimiter + PRICE_OPEN + delimiter + PRICE_CLOSE + delimiter + PROFIT + '\r\n',{ flag: 'a+' });
        }
        console.log('Finished!');
        client_cmd.end();
});
