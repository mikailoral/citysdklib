package com.libtest;

import java.util.ArrayList;

import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.libtest.model.Point;
import com.libtest.model.WebAddress;
import com.libtest.util.CitySDKMapUtil;
import com.libtest.util.DirectionTask;
import com.libtest.util.MarkerTask;

public class LibTest extends FragmentActivity implements LocationListener,
		OnMarkerClickListener {

	private GoogleMap map;
	private Handler handler;
	LocationManager locationManager;
	Location location;
	String provider;
	LatLng toPosition = new LatLng(41.108906, 29.009936);
	CitySDKMapUtil citySDKMapUtil;
	ArrayList<Point> itemList = new ArrayList<Point>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lib_test);
		citySDKMapUtil = new CitySDKMapUtil(this);

		Button btn = (Button) findViewById(R.id.button1);
		btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				WebAddress address = new WebAddress("", "pharmacy");
//				String s = "http://apicitysdk.ibb.gov.tr/nodes?layer=parkingareas&lat=40.993171&lon=29.026592&radius=300&geom&per_page=100";
				address.setLatitude(location.getLatitude());
				address.setLongitude(location.getLongitude());
				address.setPerPage(100);
				address.setRadius(2000);
				new MarkerTask(address) {

					@Override
					public void afterSync(ArrayList<Point> itemList_) {
						this.itemList = itemList_;
						for (Point point : itemList) {
							map.addMarker(new MarkerOptions().position(
									new LatLng(point.getLatitude(), point
											.getLongitude())).title(
									point.getName()));

						}
					}
				}.execute();
			}
		});

		map = ((SupportMapFragment) getSupportFragmentManager()
				.findFragmentById(R.id.map)).getMap();
		locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
		map.setMyLocationEnabled(true);
		map.getUiSettings().isMyLocationButtonEnabled();
		map.setOnMarkerClickListener(this);
		map.addMarker(new MarkerOptions().position(
				new LatLng(41.107969, 29.021695)).title("Cikis"));

		map.addMarker(new MarkerOptions().position(
				new LatLng(41.111461, 28.99719)).title("Kavsak"));

		if (locationManager != null) {
			boolean gpsIsEnabled = locationManager
					.isProviderEnabled(LocationManager.GPS_PROVIDER);
			if (!gpsIsEnabled) {
				handler = new Handler();
				handler.postDelayed(new Runnable() {
					@Override
					public void run() {
						citySDKMapUtil.showGPSDisabledAlertToUser();
					}
				}, 3000);
			}
			Criteria criteria = new Criteria();
			provider = locationManager.getBestProvider(criteria, true);
			locationManager.requestLocationUpdates(
					LocationManager.GPS_PROVIDER, 5000L, 10F, this);
			location = locationManager.getLastKnownLocation(provider);
		}
		if (location != null) {
			onLocationChanged(location);
		}
	}
	

	@Override
	public void onLocationChanged(Location location) {
		double latitude = location.getLatitude();
		double longitude = location.getLongitude();
		LatLng latLng = new LatLng(latitude, longitude);
		map.moveCamera(CameraUpdateFactory.newLatLng(latLng));
		map.animateCamera(CameraUpdateFactory.zoomTo(15.0f));
	}

	@Override
	public void onProviderDisabled(String provider) {
	}

	@Override
	public void onProviderEnabled(String provider) {
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
	}

	@Override
	public boolean onMarkerClick(Marker marker) {
		toPosition = new LatLng(marker.getPosition().latitude,
				marker.getPosition().longitude);
		Point point = new Point(marker.getPosition().latitude,
				marker.getPosition().longitude, "");
		new DirectionTask(map, location, point){

			@Override
			public void drawMarker() {
				for (Point point : itemList) {
					map.addMarker(new MarkerOptions().position(
							new LatLng(point.getLatitude(), point
									.getLongitude())).title(
							point.getName()));

				}	
			}
			
		}.execute();
		
		return false;
	}

}
