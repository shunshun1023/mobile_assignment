package com.df.dianping;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ListView;


import com.df.widget.PoiListItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;


public class MainActivity extends Activity implements View.OnClickListener {
	private Button btnSearch;
	protected Location mLastLocation;
	private ArrayList<Map<String, Object>> mData;
	private ArrayList<Map<String, Object>> filterData;
	private View loadingView;
	private ListView listView;
	ListAdapter resultAdapter = null;
	private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 34;
	private LocationProvider _locationProvider;
	private LocationManager _locationManager;
	Location location;

	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		System.out.println("get location");
		//location = getCurrentLocation();


		setContentView(R.layout.searchresult);
		//System.out.println("getData:+AAAAA"+location.toString());
		// get the data of restaurants
		mData = PoiResultData.getRestaurantData();
		//mData = PoiResultData.calculateDistance(mData,location);
		filterData = mData;

		listView = (ListView) findViewById(R.id.resultlist);
		//list.setOnItemClickListener(mOnClickListener);
		resultAdapter = new MainActivity.PoiResultAdapter(this);

		View btnPrice = findViewById(R.id.id_sort_price);
		btnPrice.setOnClickListener(this);

		View btnBusyness = findViewById(R.id.id_sort_busyness);
		btnBusyness.setOnClickListener(this);

		View btnDistance = findViewById(R.id.id_sort_distance);
		btnDistance.setOnClickListener(this);

		loadingView = LayoutInflater.from(this).inflate(R.layout.listfooter, null);

		listView.addFooterView(loadingView);
		//loadingView.setVisibility(View.GONE);

		listView.setAdapter(resultAdapter);
		listView.setOnItemClickListener(mOnClickListener);

	}
	// Get current location
	private Location getCurrentLocation() {
		//get the currentLocation
		_locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
		_locationProvider = _locationManager.getProvider(LocationManager.NETWORK_PROVIDER);
		@SuppressWarnings("MissingPermission")
		Location location = _locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
		return location;
	}

	private AdapterView.OnItemClickListener mOnClickListener = new AdapterView.OnItemClickListener() {
		public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
			Intent intent = new Intent();
			intent.setClass(MainActivity.this, DetailActivity.class);
			Restaurant restaurant = new Restaurant(mData.get(position));
			//Bundle bundle = new Bundle();
			//bundle.putString("restaurant", restaurant);
			intent.putExtra("restaurant",restaurant);
			startActivity(intent);
		}
	};


	public class PoiResultAdapter extends BaseAdapter {

		private LayoutInflater mInflater;

		public PoiResultAdapter(Context context) {
			this.mInflater = LayoutInflater.from(context);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return filterData.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return filterData.get(position).get("name").toString();
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				Log.v("is NULL", "::" + position);
			}
			convertView = mInflater.inflate(R.layout.resultitem, null);

			PoiListItem item = (PoiListItem) convertView;

			Map map = filterData.get(position);

			item.setPoiData(
					map.get("name").toString(),
					((Integer) map.get("price")).intValue(),
					map.get("addr").toString(),
					((Integer) map.get("star")).intValue(),
					((Integer) map.get("busy")).intValue(),
					map.get("distance").toString()
			);
			if (position == filterData.size() - 1) {
				listView.removeFooterView(loadingView);
			}
			return convertView;
		}

	}
	// Comparator for price  Order
	public static Comparator<Map<String,Object>> PriceComparator = new Comparator <Map<String,Object>>( ) {

		@Override
		public int compare(Map<String, Object> t1, Map<String, Object> t2) {
			return compareInt(((Integer) t1.get("price")).intValue(), ((Integer) t2.get("price")).intValue());
		}
	};
	// Comparator for busyness  Order
	public static Comparator <Map<String,Object>> BusynessComparator = new Comparator <Map<String,Object>>( ) {

		@Override
		public int compare(Map<String, Object> t1, Map<String, Object> t2) {
			return compareInt(((Integer) t1.get("busy")).intValue(), ((Integer) t2.get("busy")).intValue());
		}
	};
	// Comparator for distance Order
	public static Comparator <Map<String,Object>> DistanceComparator = new Comparator <Map<String,Object>>( ) {

		@Override
		public int compare(Map<String, Object> t1, Map<String, Object> t2) {

			int intDistance1 = getMeters(t1.get("distance").toString());
			int intDistance2 = getMeters(t2.get("distance").toString());
			return compareInt(intDistance1, intDistance2);
		}
	};
	// get distance value.for example 680m returns 680, 1.5km returns 15000
	private static int getMeters(String strDistance) {
		if(strDistance.contains("Km")){
			String [] strListDistance1 = strDistance.split("Km");
			return (int)(Double.parseDouble(strListDistance1[0])*1000);
		}else{
			String [] strListDistance =strDistance.split("m");
			return (Integer.parseInt(strListDistance[0]));
		}
	}
	//compare two integers
	private static int compareInt(int value1, int value2) {
		if(value1 > value2){
			return 1;
		}else if(value1 < value2){
			return -1;
		}else{
			return 0;
		}
	}


	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.id_sort_price: {
				Collections.sort(filterData, PriceComparator);
				((PoiResultAdapter) resultAdapter).notifyDataSetChanged();
				break;
			}
			case R.id.id_sort_busyness: {
				Collections.sort(filterData, BusynessComparator);
				((PoiResultAdapter) resultAdapter).notifyDataSetChanged();
				break;
			}
			case R.id.id_sort_distance:{
				Collections.sort(filterData, DistanceComparator);
				((PoiResultAdapter) resultAdapter).notifyDataSetChanged();
				break;
			}
		}
	}
}