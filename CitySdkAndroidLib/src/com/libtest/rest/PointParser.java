package com.libtest.rest;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import com.libtest.model.Point;


/**
 * 
 * @author mikail.oral
 *
 */
public class PointParser {
	private static String getJsonFromUrl(String strUrl) {
		String strJson = null;
		InputStream is = null;
		try {
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpGet httpGet = new HttpGet(strUrl);
			httpGet.setHeader("Content-type", "application/json");

			HttpResponse httpResponse = httpClient.execute(httpGet);
			HttpEntity httpEntity = httpResponse.getEntity();
			is = httpEntity.getContent();

			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is, "utf-8"), 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			is.close();
			strJson = sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
			strJson = null;
		}
		return strJson;
	}

	public static ArrayList<Point> getVideosFromServer(String strUrl) {
		JSONArray resultList = null;
		try {
			String strJson = getJsonFromUrl(strUrl);
			JSONObject json = new JSONObject(strJson);
			resultList = json.getJSONArray("results");
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (resultList.equals(null)) {
			return null;
		}

		ArrayList<Point> items = null;
		Point item = null;

		try {
			/** item count */
			int length = resultList.length();

			if (length <= 0) {
				return null;
			}

			items = new ArrayList<Point>();

			for (int i = 0; i < length; i++) {

				double latitude = 0;
				double longitude = 0;
				String name = "";
				JSONObject result = (JSONObject) resultList.get(i);

				name = result.getString("name");
				JSONObject geom = result.getJSONObject("geom");
				JSONArray coordinates = geom.getJSONArray("coordinates");
				longitude = coordinates.getDouble(0);
				latitude = coordinates.getDouble(1);

				item = new Point(latitude, longitude, name);

				items.add(item);
			}
		} catch (Exception e) {
			e.printStackTrace();
			items = null;
		}

		return items;
	}
}