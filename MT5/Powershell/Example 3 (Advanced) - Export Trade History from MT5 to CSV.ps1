# Example 3 (Advanced) - Export Trade History from MT4 to CSV
#
# PREREQUISITE: Install MTsocketAPI in your Metatrader 4 using the following link https://www.mtsocketapi.com/doc4/installation.html
#
# WARNING: All these source codes are only examples for testing purposes. 
# WARNING: We don’t provide any guarantee or responsibility about it. 
# WARNING: Use these examples at your own risk.

$IP = "127.0.0.1"
$Port = 77

$Address = [System.Net.IPAddress]::Parse($IP)
$Socket = New-Object System.Net.Sockets.TCPClient($Address,$Port)
$Stream = $Socket.GetStream()

$Writer = New-Object System.IO.StreamWriter($Stream)
$Reader = New-Object System.IO.StreamReader($Stream)

$Cmd = "" | Select MSG,MODE,FROM_DATE,TO_DATE
$Cmd.MSG = "TRADE_HISTORY"
$Cmd.MODE = "POSITIONS"
$Cmd.FROM_DATE = "2023/08/23 00:00:00"
$Cmd.TO_DATE = "2023/08/24 00:00:00"

$Json = ConvertTo-Json $Cmd -Compress

$Writer.WriteLine($Json)
$Writer.Flush()

$Response = $Reader.ReadLine()
$DataObj = ConvertFrom-Json $Response
if ($DataObj.ERROR_ID -ge 0) {
	$DataObj.POSITIONS | Export-Csv -Path "History.csv" -Delimiter "," -NoTypeInformation
	Write-Host "Exported to CSV!"
}
else {
	Write-Host $DataObj.ERROR_DESCRIPTION
}

$Stream.Close()
$Socket.Close()

