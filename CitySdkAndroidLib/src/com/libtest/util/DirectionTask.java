package com.libtest.util;

import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.w3c.dom.Document;

import android.graphics.Color;
import android.location.Location;
import android.os.AsyncTask;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;
import com.libtest.GMapV2Direction;
import com.libtest.model.Point;

public abstract class DirectionTask extends AsyncTask<Void, Void, Document> {
	
//	private Activity activity;
	private GoogleMap map;
	private Location location;
	private Point point;
	
	public DirectionTask(GoogleMap map, Location location, Point point) {
		super();
		this.map = map;
		this.location = location;
		this.point = point;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public Point getPoint() {
		return point;
	}

	public void setPoint(Point point) {
		this.point = point;
	}

	protected Document doInBackground(Void... urls) {
		try {
			double latitude = location.getLatitude();
			double longitude = location.getLongitude();
			LatLng latLng = new LatLng(latitude, longitude);
			String url = "http://maps.googleapis.com/maps/api/directions/xml?"
					+ "origin="
					+ latLng.latitude
					+ ","
					+ latLng.longitude
					+ "&destination="
					+ point.getLatitude()
					+ ","
					+ point.getLongitude()
					+ "&sensor=false&units=metric&mode=driving";
			HttpParams httpParams = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpParams, 25000);
			HttpConnectionParams.setSoTimeout(httpParams, 25000);
			DefaultHttpClient httpClient = new DefaultHttpClient(httpParams);
			HttpPost httpPost = new HttpPost(url);
			HttpResponse response = httpClient.execute(httpPost);
			InputStream in = response.getEntity().getContent();
			DocumentBuilder builder = DocumentBuilderFactory.newInstance()
					.newDocumentBuilder();
			Document doc = builder.parse(in);
			return doc;
		} catch (Exception e) {
			return null;
		}
	}

	protected void onPostExecute(Document doc) {
		GMapV2Direction md = new GMapV2Direction();
		ArrayList<LatLng> directionPoint = md.getDirection(doc);
		PolylineOptions rectLine = new PolylineOptions().width(10).color(
				Color.RED);
		for (int i = 0; i < directionPoint.size(); i++) {
			rectLine.add(directionPoint.get(i));
		}
		map.clear();
		map.addPolyline(rectLine);
		drawMarker();
	}

	public GoogleMap getMap() {
		return map;
	}

	public void setMap(GoogleMap map) {
		this.map = map;
	}
	
	public abstract void drawMarker();
	
}