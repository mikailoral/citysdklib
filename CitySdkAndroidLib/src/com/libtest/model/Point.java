package com.libtest.model;

/**
 * Bu sinif harita uzerinde konulacak markerlarin bilgilerini
 * modellemek icin olusturulmustur.
 * CitySDK rest web servislerinden cekilecek JSONdan alinan degerler, 
 * sinif araciligiyla saklanacaktir.
 * 
 * 
 * @author mikail.oral
 *
 */
public class Point {
	
	private double latitude ;
	private double longitude ;
	private String name ;
	
	public Point(double latitude, double longitude, String name) {
		super();
		this.latitude = latitude;
		this.longitude = longitude;
		this.name = name;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
	

}
