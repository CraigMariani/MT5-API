// Example 3 (Advanced) - Export Trade History from MT5 to CSV
//
// PREREQUISITE: Install MTsocketAPI in your Metatrader 5 using the following link https://www.mtsocketapi.com/doc5/installation.html
//
// WARNING: All these source codes are only examples for testing purposes. 
// WARNING: We don’t provide any guarantee or responsibility about it. 
// WARNING: Use these examples at your own risk.

import java.net.*;
import java.io.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
/**
 *  $ javac -cp .:json-simple-1.1.1.jar getHistory.java (Compile)
 *  $ java -cp .:json-simple-1.1.1.jar getHistory (Run)
 * */
class getHistory
{
    public static void main(String[] args)
    {
           try
           {
                Socket socket = new Socket("localhost", 77);

                OutputStream output = socket.getOutputStream();
                InputStream input = socket.getInputStream();

                BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(output));

                String jsonString;
                JSONObject jo = new JSONObject();
                jo.put("MSG","TRADE_HISTORY");
                jo.put("FROM_DATE","2022/09/19 07:00:00");
                jo.put("TO_DATE","2022/09/21 00:00:00");
                jo.put("MODE","POSITIONS"); //MODES: POSITIONS, ORDERS, DEALS and ORDERS_DEALS

                //System.out.println(jo.toString());
                writer.write(jo.toString() + "\r\n");
                writer.flush();

                jsonString = reader.readLine();
                System.out.println(jsonString);

                JSONParser parser = new JSONParser();
                JSONObject json = (JSONObject) parser.parse(jsonString);
                //JSONArray jarr= (JSONArray)json.get("POSITIONS");
                JSONArray jarr= (JSONArray)json.get(jo.get("MODE").toString());

                System.out.println("Creating file...");

                FileWriter myFile = new FileWriter("tradeHistory.csv");

                myFile.write("OPEN_TIME,CLOSE_TIME,TICKET,SYMBOL,MAGIC,PRICE_OPEN,PRICE_CLOSE,TYPE,LOTS,STOP_LOSS,TAKE_PROFIT,SWAP,COMMISSION,COMMENT,PROFIT\r\n");

                for(int i = 0; i < jarr.size(); i++) {
                        JSONObject item = (JSONObject) jarr.get(i);
                        //System.out.println(Integer.toString(i) + " " + item.get("OPEN_TIME"));
                        myFile.write(item.get("OPEN_TIME").toString() + "," +
                                item.get("CLOSE_TIME").toString() + "," +
                                item.get("TICKET").toString() + "," +
                                item.get("SYMBOL").toString() + "," +
                                item.get("MAGIC").toString() + "," +
                                item.get("PRICE_OPEN").toString() + "," +
                                item.get("PRICE_CLOSE").toString() + "," +
                                item.get("TYPE").toString() + "," +
                                item.get("VOLUME").toString() + "," +
                                item.get("SL").toString() + "," +
                                item.get("TP").toString() + "," +
                                item.get("SWAP").toString() + "," +
                                item.get("COMMISSION").toString() + "," +
                                item.get("COMMENT") + "," +
                                item.get("PROFIT").toString() +
                                "\r\n");
                }

                myFile.close();

                System.out.println("tradeHistory.csv created!");
           }
           catch(ParseException pe) {
               System.out.println("position: " + pe.getPosition());
               System.out.println(pe);
           }
           catch (UnknownHostException ex) {
                System.out.println("Server not found: " + ex.getMessage());
           }
           catch (IOException ex) {
                System.out.println("I/O error: " + ex.getMessage());
           }
        }
}
