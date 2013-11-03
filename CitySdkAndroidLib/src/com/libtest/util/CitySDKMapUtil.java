package com.libtest.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;

public class CitySDKMapUtil {

	private Activity activity;
	
	public CitySDKMapUtil(Activity activity) {
		super();
		this.activity = activity;
	}

	public Activity getContext() {
		return activity;
	}

	public void setContext(Activity context) {
		this.activity = context;
	}

	public void showGPSDisabledAlertToUser() {
		activity.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				if (!activity.isFinishing()) {
					AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
							activity);
					alertDialogBuilder
							.setTitle("GPS Aktif Deðil")
							.setMessage("Açýlsýn mý ?")
							.setCancelable(true)
							.setPositiveButton("Tamam",
									new DialogInterface.OnClickListener() {
										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {
											Intent callGPSSettingIntent = new Intent(
													android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
											activity.startActivity(callGPSSettingIntent);
										}
									})
							.setNegativeButton("Ýptal",
									new DialogInterface.OnClickListener() {

										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {
											dialog.cancel();
										}
									}).show();

				}
			}
		});
	}

	
	

}
