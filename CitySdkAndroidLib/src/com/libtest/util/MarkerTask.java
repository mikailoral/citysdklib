package com.libtest.util;

import java.util.ArrayList;

import android.os.AsyncTask;

import com.libtest.model.Point;
import com.libtest.model.WebAddress;
import com.libtest.rest.PointParser;

public abstract class MarkerTask extends AsyncTask<Void, Void, ArrayList<Point>> {

	protected ArrayList<Point> itemList = null;
	WebAddress address;
//	private Activity activity;
//	ProgressDialog pd;

	public MarkerTask(WebAddress address) {
		super();
		this.address = address;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();

//		pd = ProgressDialog.show(activity,
//				activity.getString(R.string.synchronizing),
//				activity.getString(R.string.wait), true, true);
//		pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
	}

	@Override
	protected ArrayList<Point> doInBackground(Void... params) {
		itemList = PointParser
				.getVideosFromServer(address.getCitySDKURL());
		return itemList;
	}

	@Override
	protected void onPostExecute(ArrayList<Point> result) {
		super.onPostExecute(result);
//		if (pd != null) {
//			pd.dismiss();
//		}

		afterSync(itemList);
	}

	public abstract void afterSync(ArrayList<Point> itemList2);

}
