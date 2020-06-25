package com.company;
public class BME280Jni
{
	public native String BME280Init();
	
	public native String BME280GetData();
	
	public native void BME280Destroy();
	
	public native void GPIOSetPin(int id,int hl);

}
