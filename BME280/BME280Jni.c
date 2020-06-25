#include "bme280.h"
#include <stdio.h>
#include <unistd.h>
#include <wiringPi.h>
#include <wiringPiSPI.h>
#include <string.h>
#include <stdlib.h>
#include <linux/i2c-dev.h>
#include <sys/ioctl.h>
#include <sys/types.h>
#include <fcntl.h>
#include "jni.h"
#define IIC_Dev  "/dev/i2c-1"

struct bme280_dev dev;
char *w,*s,*q;
int fd;

void user_delay_ms(uint32_t period)
{
  usleep(period*1000);
}

int8_t user_i2c_read(uint8_t id, uint8_t reg_addr, uint8_t *data, uint16_t len)
{
  write(fd, &reg_addr,1);
  read(fd, data, len);
  return 0;
}

int8_t user_i2c_write(uint8_t id, uint8_t reg_addr, uint8_t *data, uint16_t len)
{
  int8_t *buf;
  buf = malloc(len +1);
  buf[0] = reg_addr;
  memcpy(buf +1, data, len);
  write(fd, buf, len +1);
  free(buf);
  return 0;
}

JNIEXPORT jstring JNICALL Java_com_company_BME280Jni_BME280Init
  (JNIEnv *env, jobject job){
  w=malloc(1024);
  s=malloc (1024);
  q=malloc(1024);
  int8_t rslt = BME280_OK;
  if ((fd = open(IIC_Dev, O_RDWR)) < 0) {
    return (*env)->NewStringUTF(env, "err");
  }
  if (ioctl(fd, I2C_SLAVE, 0x77) < 0) {
    return (*env)->NewStringUTF(env, "err");
  }
  //dev.dev_id = BME280_I2C_ADDR_PRIM;//0x76
  dev.dev_id = BME280_I2C_ADDR_SEC; //0x77
  dev.intf = BME280_I2C_INTF;
  dev.read = user_i2c_read;
  dev.write = user_i2c_write;
  dev.delay_ms = user_delay_ms;

  rslt = bme280_init(&dev);
  if(rslt==0){
	  return  (*env)->NewStringUTF(env, "noerr");
  }else{
	  return  (*env)->NewStringUTF(env, "err");
  }
}

JNIEXPORT jstring JNICALL Java_com_company_BME280Jni_BME280GetData
  (JNIEnv *env, jobject job){
	  
	int8_t rslt;
	uint8_t settings_sel;
	struct bme280_data comp_data;
	
	(&dev)->settings.osr_h = BME280_OVERSAMPLING_1X;
	(&dev)->settings.osr_p = BME280_OVERSAMPLING_16X;
	(&dev)->settings.osr_t = BME280_OVERSAMPLING_2X;
	(&dev)->settings.filter = BME280_FILTER_COEFF_16;
	(&dev)->settings.standby_time = BME280_STANDBY_TIME_62_5_MS;

	settings_sel = BME280_OSR_PRESS_SEL;
	settings_sel |= BME280_OSR_TEMP_SEL;
	settings_sel |= BME280_OSR_HUM_SEL;
	settings_sel |= BME280_STANDBY_SEL;
	settings_sel |= BME280_FILTER_SEL;
	rslt = bme280_set_sensor_settings(settings_sel, &dev);
	rslt = bme280_set_sensor_mode(BME280_NORMAL_MODE, &dev);

	(&dev)->delay_ms(70);
	rslt = bme280_get_sensor_data(BME280_ALL, &comp_data, &dev);
	
	memset(w,0,1024);
	memset(s,0,1024);
  memset(q,0,1024);
	#ifdef BME280_FLOAT_ENABLE
	sprintf(w, "%0.2f", (&comp_data)->temperature);
	sprintf(s,"%0.2f",(&comp_data)->humidity);
  sprintf(q,"%0.2f",(&comp_data)->pressure/100);
	#else
	sprintf(w, "%ld", (&comp_data)->temperature);	
	sprintf(a, "%ld", (&comp_data)->humidity);	
  sprintf(q,"%ld",(&comp_data)->pressure/100);
	#endif
	strcat(w,",");
	strcat(w,s);
  strcat(w,",");
  strcat(w,q);
	
	return  (*env)->NewStringUTF(env, w); 
  }

JNIEXPORT void JNICALL Java_com_company_BME280Jni_GPIOSetPin
  (JNIEnv *env, jobject job, jint id, jint hl){
wiringPiSetup();
pinMode(id, OUTPUT);
if(hl==0){
digitalWrite(id, LOW);
}
if(hl==1){
digitalWrite(id,HIGH);
}
  }

JNIEXPORT void JNICALL Java_com_company_BME280Jni_BME280Destroy
  (JNIEnv *env, jobject job){
	  free(w);
	  free(s);
	  free(q);
	  w=NULL;
	  s=NULL;
	  q=NULL;
  }
