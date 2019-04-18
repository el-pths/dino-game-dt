// Библиотека для работы с протоколом I2C (порты A5/SCL и A4/SDA)
#include <Wire.h>
const int MPU_addr = 0x68; // упрощенный I2C адрес нашего гироскопа/акселерометра MPU-6050.
// переменные для хранения данных возвращаемых прибором.
int16_t AcX, AcY, AcZ;
//==============================================
void setup()
{

  Wire.begin();
  Wire.beginTransmission(MPU_addr);
  // Производим запись в регистр энергосбережения MPU-6050
  Wire.write(0x6B);
  Wire.write(0);     // устанавливаем его в ноль.
  Wire.endTransmission(true);
  pinMode(2 , OUTPUT);
  digitalWrite(2, 1);
  Serial.begin(9600);

}
//================ НАЧАЛО ====================
void loop()
{
  Wire.beginTransmission(MPU_addr);
  //Готовим для чтения регистры с адреса 0x3B.
  Wire.write(0x3B);
  Wire.endTransmission(false);
  // Запрос состояния 14 регистров.
  Wire.requestFrom(MPU_addr, 14, true);
  AcX = Wire.read() << 8 | Wire.read();
  AcY = Wire.read() << 8 | Wire.read();
  AcZ = Wire.read() << 8 | Wire.read();
  // Вывод в порт данных полученных прибора.

  Serial.print(AcY);
  Serial.print(' ');
  Serial.print(AcX);
  Serial.print(' ');
  Serial.println(AcZ);

  delay(50);
}





