# Example 3 - Stream actual EURUSD price from MT5
#
# PREREQUISITE: Install MTsocketAPI in your Metatrader 5 using the following link https://www.mtsocketapi.com/doc5/installation.html
#
# WARNING: All these source codes are only examples for testing purposes. 
# WARNING: We don’t provide any guarantee or responsibility about it. 
# WARNING: Use these examples at your own risk.

conCMD <- socketConnection(host="localhost", port = 77, blocking = TRUE)
conDATA <- socketConnection(host="localhost", port = 78, blocking = TRUE)
writeLines("{\"MSG\":\"TRACK_PRICES\",\"SYMBOLS\":[\"EURUSD\"]}\r\n", conCMD)
server_resp <- readLines(conCMD, 1)
print(server_resp)
repeat {
    print(readLines(conDATA, 1))
}
close(conCMD)
close(conDATA)

