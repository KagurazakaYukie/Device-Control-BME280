include <stdio.h>

int main（）{
wiringPiSetup();
pinMode(12, OUTPUT);
digitalWrite(12, LOW);
return 0；
}