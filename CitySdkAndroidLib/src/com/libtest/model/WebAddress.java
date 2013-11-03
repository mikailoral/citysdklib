package com.libtest.model;


/**
 * Bu sinif CitySDKdan cekilebilecek webservis adreslerini modellemek 
 * icin gelistirilmistir. Tanimlanan degiskenler doldurularak
 * getCitySDKURL() metodu cagirilirsa CitySDK nin tanimladigi 
 * bir web servis adresi olusacaktir.
 * 
 *  
 * @author mikail.oral
 *
 */
public class WebAddress {

	private double latitude;
	private double longitude;
	private String type;
	private String name;
	private int radius;
	private int perPage;

	public WebAddress(String name, String type) {
		this.name = name;
		this.type = type;
	}

	public int getPerPage() {
		return perPage;
	}

	public void setPerPage(int perPage) {
		this.perPage = perPage;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getRadius() {
		return radius;
	}

	public void setRadius(int radius) {
		this.radius = radius;
	}

	public String getCitySDKURL() {
		return  "http://apicitysdk.ibb.gov.tr/nodes?layer=" + type + "&lat=" + 41.104501 + "&lon=" + 29.022002 + "&radius=" + radius + "&geom&per_page="+ perPage;
//		return "http://apicitysdk.ibb.gov.tr/nodes?layer=parkingareas&lat=40.993171&lon=29.026592&radius=300&geom&per_page=100";
	}

}
