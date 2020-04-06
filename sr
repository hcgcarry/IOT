/*
 WiFiEsp example: WebClient
 This sketch connects to google website using an ESP8266 module to
 perform a simple web search.
 For more details see: http://yaab-arduino.blogspot.com/p/wifiesp-example-client.html
*/

#include "WiFiEsp.h"
#include "DHT.h"
#define dhtPin 5      //讀取DHT11 Data
#define dhtType DHT11 //選用DHT11   
DHT dht(dhtPin, dhtType); // Initialize DHT sensor


// Emulate Serial1 on pins 6/7 if not present
#ifndef HAVE_HWSERIAL1
#include "SoftwareSerial.h"
SoftwareSerial Serial1(6, 7); // RX, TX
#endif

char ssid[] = "wgs602";            // your network SSID (name)
char pass[] = "49540035";        // your network password
int status = WL_IDLE_STATUS;     // the Wifi radio's status

char server[] = "192.168.127.103";
float temperature=100;
float humidity=10;

String getHead="GET /arduino.php?temperature=" + String(temperature) + "&humidity=" + String(humidity) +" HTTP/1.1";


// Initialize the Ethernet client object
WiFiEspClient client;

void setup()
{
  dht.begin();//啟動DHT
  DHTRead(&temperature,&humidity);
  String getHead="GET /arduino.php?temperature=" + String(temperature) + "&humidity=" + String(humidity) +" HTTP/1.1";
  // initialize serial for debugging
  //Serial.begin(115200);
  Serial.begin(9600);
  // initialize serial for ESP module
  Serial1.begin(9600);
  // initialize ESP module
  WiFi.init(&Serial1);

  // check for the presence of the shield
  if (WiFi.status() == WL_NO_SHIELD) {
    Serial.println("WiFi shield not present");
    // don't continue
    while (true);
  }

  // attempt to connect to WiFi network
  while ( status != WL_CONNECTED) {
    Serial.print("Attempting to connect to WPA SSID: ");
    Serial.println(ssid);
    // Connect to WPA/WPA2 network
    status = WiFi.begin(ssid, pass);
  }

  // you're connected now, so print out the data
  Serial.println("You're connected to the network");

  printWifiStatus();

  Serial.println();
  Serial.println("Starting connection to server...");
  // if you get a connection, report back via serial
  if (client.connect(server, 80)) {
    Serial.println("Connected to server");
    // Make a HTTP request
  
    
    //client.println("GET /arduino.php?temperature=100&humidity=10 HTTP/1.1");
    client.println(getHead);
    client.println("Host: 192.168.127.103");
   
    
    client.println("Connection: close");
    client.println();   
  }
  while(!client.available()); // wait for 1st response from server
  
}

void loop()
{
   
  // if there are incoming bytes available
  // from the server, read them and print them
  while (client.available()) {
    char c = client.read();
    Serial.write(c);
  }

  // if the server's disconnected, stop the client
  if (!client.connected()) {
    Serial.println();
    Serial.println("Disconnecting from server...");
    client.stop();

    // do nothing forevermore
    while (true);
  }
}

void DHTRead(float* h,float* t){
     *h = dht.readHumidity();//讀取濕度
     *t = dht.readTemperature();//讀取攝氏溫度
  
 
    return;
  }

void printWifiStatus()
{
  // print the SSID of the network you're attached to
  Serial.print("SSID: ");
  Serial.println(WiFi.SSID());

  // print your WiFi shield's IP address
  IPAddress ip = WiFi.localIP();
  Serial.print("IP Address: ");
  Serial.println(ip);

  // print the received signal strength
  long rssi = WiFi.RSSI();
  Serial.print("Signal strength (RSSI):");
  Serial.print(rssi);
  Serial.println(" dBm");
}
