#include <Servo.h>
#include <SPI.h>
#include <Arduino.h>
#include <SoftwareSerial.h>
void loop();

int Bluetooth1 = 4;   //RX sau TX
int Bluetooth2 = 5;
SoftwareSerial bluetooth(Bluetooth1,Bluetooth2);   //RX, TX


Servo myservo;  // create servo object to control a servo
// twelve servo objects can be created on most boards

int pos = 0;    // variable to store the servo position

int ledPinA = 2;
int ledPinV = 3;
int ledPinR = 10; // define digital pin 10.
int ledPinG = 11;


void setup()
{
  pinMode(ledPinA, OUTPUT);// define pin with LED connected as output.
  pinMode(ledPinV, OUTPUT);
  pinMode(ledPinG, OUTPUT);
  pinMode(ledPinR, OUTPUT);

  myservo.attach(9);  // attaches the servo on pin 9 to the servo object

   // put your setup code here, to run once:
  Serial.begin(9600);
  bluetooth.begin(9600);


  pinMode(Bluetooth1, INPUT);
  pinMode(Bluetooth2, OUTPUT);
}

void Camera1On()
{
  digitalWrite(ledPinA, HIGH); // set the LED on.  
}


void Camera1Off()
{
  digitalWrite(ledPinA, LOW); // set the LED off.  
}

void Camera2On()
{
  digitalWrite(ledPinV, HIGH); // set the LED on.  
}

void Camera2Off()
{
  digitalWrite(ledPinV, LOW); // set the LED off.  
}

void Camera3On()
{
  digitalWrite(ledPinR, HIGH); // set the LED on.  
}

void Camera3Off()
{
  digitalWrite(ledPinR, LOW); // set the LED off.  
}

void Camera4On()
{
  digitalWrite(ledPinG, HIGH); // set the LED on.  
}

void Camera4Off()
{
  digitalWrite(ledPinG, LOW); // set the LED off.  
}

void PoartaOff()
{
  for (pos = 0; pos <= 90; pos += 10) { // goes from 0 degrees to 180 degrees
    // in steps of 1 degree
    myservo.write(pos);              // tell servo to go to position in variable 'pos'
    delay(15);                       // waits 15ms for the servo to reach the position
  }
}

void PoartaOn()
{
  for (pos = 90; pos >= 0; pos -= 10) { // goes from 180 degrees to 0 degrees
    myservo.write(pos);              // tell servo to go to position in variable 'pos'
    delay(15);                       // waits 15ms for the servo to reach the position
  }
}

void loop()
{
  if(bluetooth.available())
  {
    String a = bluetooth.readString();
    Serial.println(a);
    
    if(a == "0")
      Camera1On();
    else
      if(a == "1")
        Camera1Off();
      else
        if(a == "2")
          Camera2On();
         else
          if(a == "3")
            Camera2Off();
           else
            if(a == "4")
              Camera3On();
             else
              if(a == "5")
                Camera3Off();
              else
                if(a == "6")
                  Camera4On();
                 else
                  if(a == "7")
                    Camera4Off();
                   else
                    if(a == "8")
                      PoartaOn();
                    else
                      if(a == "9")
                        PoartaOff();

  }
  
}
