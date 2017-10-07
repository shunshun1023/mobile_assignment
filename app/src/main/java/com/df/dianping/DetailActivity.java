package com.df.dianping;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.IntegerRes;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.*;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.SettableFuture;
import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.http.NextServiceFilterCallback;
import com.microsoft.windowsazure.mobileservices.http.OkHttpClientFactory;
import com.microsoft.windowsazure.mobileservices.http.ServiceFilter;
import com.microsoft.windowsazure.mobileservices.http.ServiceFilterRequest;
import com.microsoft.windowsazure.mobileservices.http.ServiceFilterResponse;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;
import com.microsoft.windowsazure.mobileservices.table.sync.MobileServiceSyncContext;
import com.microsoft.windowsazure.mobileservices.table.sync.localstore.ColumnDataType;
import com.microsoft.windowsazure.mobileservices.table.sync.localstore.MobileServiceLocalStoreException;
import com.microsoft.windowsazure.mobileservices.table.sync.localstore.SQLiteLocalStore;
import com.microsoft.windowsazure.mobileservices.table.sync.synchandler.SimpleSyncHandler;
import com.squareup.okhttp.OkHttpClient;

import org.w3c.dom.Text;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import static java.lang.Math.abs;


public class DetailActivity extends Activity implements OnClickListener
{

    private MobileServiceClient mClient;
    private MobileServiceTable<ToDoItem> mToDoTable;
    private ToDoItemAdapter mAdapter;
	private String restuarantName;
	private  LinearLayout restaurantDetail;
	private Map<String, Object> mData;
    // calculate the scores and show them
    Handler handler = new Handler()
    {
        public void handleMessage(Message paramMessage)
        {
            if(paramMessage.what == 1)
            {
                String[] scores =paramMessage.obj.toString().split(" ");
                if(scores.length == 3){
                    TextView textView =(TextView) findViewById(R.id.taste_score);
                    textView.setText(scores[0]);
                    textView = (TextView) findViewById(R.id.environment_score);
                    textView.setText(scores[1]);
                    textView = (TextView) findViewById(R.id.service_score);
                    textView.setText(scores[2]);
                    double score = Double.parseDouble(scores[0])+Double.parseDouble(scores[1])+Double.parseDouble(scores[2]);
                    int star_score = (int)score*10/3;
                    setRestaurantStars(star_score);
                }

            }
            if(paramMessage.what == 2)
            {
                restaurantDetail.findViewById(R.id.loadingbar).setVisibility(View.GONE);
                restaurantDetail.findViewById(R.id.serverdata).setVisibility(View.VISIBLE);
            }
        }
    };

    public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
        //get selected restaurant information from Main_Activity
		Restaurant restaurant = (Restaurant) getIntent().getSerializableExtra("restaurant");
        //set view
		setContentView(R.layout.poidetail);

		LayoutInflater inflater = LayoutInflater.from(this);
		restaurantDetail = (LinearLayout)inflater.inflate(R.layout.poiinfo, null);
		LinearLayout scroll = (LinearLayout)findViewById(R.id.lite_list);
		LayoutParams layoutParams = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		scroll.addView(restaurantDetail, layoutParams);
        //output restaurant info to desired positions
		InitialResultDetails(restaurant);

        try {
            // Create the Mobile Service Client instance, using the provided

            // Mobile Service URL and key
            mClient = new MobileServiceClient(
                    "https://appal1.azurewebsites.net",
                    this).withFilter(new ProgressFilter());
            System.out.println("AAAA beigin1");
            // Extend timeout from default of 10s to 20s
            mClient.setAndroidHttpClientFactory(new OkHttpClientFactory() {
                @Override
                public OkHttpClient createOkHttpClient() {
                    OkHttpClient client = new OkHttpClient();
                    client.setReadTimeout(20, TimeUnit.SECONDS);
                    client.setWriteTimeout(20, TimeUnit.SECONDS);
                    return client;
                }
            });
            // Get the Mobile Service Table instance to use
            mToDoTable = mClient.getTable(ToDoItem.class);
            System.out.println("AAAA beigin2");
            //Init local storage
            initLocalStore().get();
            //final List<ToDoItem> list = refreshItemsFromMobileServiceTable();
            System.out.println("AAAA beigin3");
            refreshItemsFromTable();
            // Create an adapter to bind the items with the view
            mAdapter = new ToDoItemAdapter(this, R.layout.row_list_to_do);
            ListView listViewToDo = (ListView) findViewById(R.id.listViewToDo);
            listViewToDo.setAdapter(mAdapter);
            System.out.println("AAAA beigin4");

           // View wantedView =  listViewToDo.getChildAt(0);

            // Load the items from the Mobile Service

            System.out.println("AAAA beigin5");
        } catch (MalformedURLException e) {
            createAndShowDialog(new Exception("There was an error creating the Mobile Service. Verify the URL"), "Error");
        } catch (Exception e){
            createAndShowDialog(e, "Error");
        }
        View btnMap = findViewById(R.id.maps);
        btnMap.setOnClickListener(this);

        View btnComment = findViewById(R.id.btnAddComments);
        btnComment.setOnClickListener(this);
	}
    // output the information of restaurant to the right position
	private void InitialResultDetails(Restaurant restaurant) {
		System.out.println("begin inserting details: ");
        TextView nameView = (TextView)findViewById(R.id.restaurantName);
        nameView.setText(restaurant.getName().toString());

        TextView namePhone = (TextView)findViewById(R.id.restaurant_phone);
        namePhone.setText(restaurant.getPhone().toString());

        TextView nameAddress = (TextView)findViewById(R.id.restaurant_address);
        nameAddress.setText(restaurant.getAddress().toString());

        TextView selfDesView = (TextView)findViewById(R.id.restaurant_selfDescription);
        selfDesView.setText(restaurant.getSelf_description());

        TextView recommendedView = (TextView)findViewById(R.id.restaurant_popularDishes);
        recommendedView.setText(restaurant.getRecommended());

        TextView average_costView = (TextView)findViewById(R.id.average_cost);
        average_costView.setText(restaurant.getPrice().toString());

        TextView tasteView = (TextView)findViewById(R.id.taste_score);
        tasteView.setText(""+restaurant.getTaste_score());

        TextView environmentView = (TextView)findViewById(R.id.environment_score);
        environmentView.setText(""+restaurant.getEnvironment_score());

        TextView serviceView = (TextView)findViewById(R.id.service_score);
        serviceView.setText(""+restaurant.getService_score());

        setRestaurantStars(restaurant.getStars());
        setRestaurantImage(restaurant.getName());
	}
    // set the image of restaurants according to their name
    private void setRestaurantImage(String name) {
        ImageView imageview = (ImageView) findViewById(R.id.restaurant_Image);
        Drawable myDrawable;
        switch (name.toLowerCase()){
            case "maha":
                myDrawable = getResources().getDrawable(R.drawable.ic_restaurant_maha);
                imageview.setImageDrawable(myDrawable);
                break;
            case "coda":
                myDrawable = getResources().getDrawable(R.drawable.ic_restaurant_coda);
                imageview.setImageDrawable(myDrawable);
                break;
            case "laksa king":
                myDrawable = getResources().getDrawable(R.drawable.ic_restaurant_laskaking);
                imageview.setImageDrawable(myDrawable);
                break;
            case "metro burger":
                myDrawable = getResources().getDrawable(R.drawable.ic_restaurant_metroburger);
                imageview.setImageDrawable(myDrawable);
                break;
            case "scopri":
                myDrawable = getResources().getDrawable(R.drawable.ic_restaurant_scopri);
                imageview.setImageDrawable(myDrawable);
                break;
            case "the french brasserie":
                myDrawable = getResources().getDrawable(R.drawable.ic_restaurant_brasserie);
                imageview.setImageDrawable(myDrawable);
                break;
            case "gru thai restaurant":
                myDrawable = getResources().getDrawable(R.drawable.ic_restaurant_gruthai);
                imageview.setImageDrawable(myDrawable);
                break;
            case "nando's":
                myDrawable = getResources().getDrawable(R.drawable.ic_restaurant_nando);
                imageview.setImageDrawable(myDrawable);
                break;
            case "udon yasan":
                myDrawable = getResources().getDrawable(R.drawable.ic_restaurant_udonyasan);
                imageview.setImageDrawable(myDrawable);
                break;
            default:
                myDrawable = getResources().getDrawable(R.drawable.ic_restaurant_bettyburgers);
                imageview.setImageDrawable(myDrawable);
                break;
        }
    }
    // set the stars 0f the restaurants
    private void setRestaurantStars(int restaurant_stars) {
		ImageView imageview = (ImageView) findViewById(R.id.restaurant_stars);
        int stars =closetInteger(restaurant_stars);
		Drawable myDrawable;
		switch(stars){
			case 0:
				myDrawable = getResources().getDrawable(R.drawable.star00);
				imageview.setImageDrawable(myDrawable);
				break;
			case 5:
				myDrawable = getResources().getDrawable(R.drawable.star05);
				imageview.setImageDrawable(myDrawable);
				break;
			case 10:
				myDrawable = getResources().getDrawable(R.drawable.star10);
				imageview.setImageDrawable(myDrawable);
				break;
			case 15:
				myDrawable = getResources().getDrawable(R.drawable.star15);
				imageview.setImageDrawable(myDrawable);
				break;
			case 20:
				myDrawable = getResources().getDrawable(R.drawable.star20);
				imageview.setImageDrawable(myDrawable);
				break;
			case 25:
				myDrawable = getResources().getDrawable(R.drawable.star25);
				imageview.setImageDrawable(myDrawable);
				break;
			case 30:
				myDrawable = getResources().getDrawable(R.drawable.star30);
				imageview.setImageDrawable(myDrawable);
				break;
			case 35:
				myDrawable = getResources().getDrawable(R.drawable.star35);
				imageview.setImageDrawable(myDrawable);
				break;
			case 40:
				myDrawable = getResources().getDrawable(R.drawable.star40);
				imageview.setImageDrawable(myDrawable);
				break;
			case 45:
				myDrawable = getResources().getDrawable(R.drawable.star45);
				imageview.setImageDrawable(myDrawable);
				break;
			case 50:
				myDrawable = getResources().getDrawable(R.drawable.star50);
				imageview.setImageDrawable(myDrawable);
				break;
		}
	}
   // find the closet int value
    private int closetInteger(int restaurant_stars) {
        int a[] ={0,5,10,15,20,35,30,35,40,45,50};
        int distance =100;
        int stars = 51;
        for(int i=0;i<a.length;i++){
            if(abs(a[i]-restaurant_stars)< distance){
                distance = abs(a[i]-restaurant_stars);
                stars= a[i];
            }
        }
        if(stars == 51){
            return 50;
        }else{
            return stars;
        }
    }


    public void onClick(View v)
	{
		switch(v.getId())
		{
			case R.id.maps:
            {
                TextView textview = (TextView) findViewById(R.id.restaurant_address);
                String direction = "google.navigation:q=" + textview.getText().toString();
                Uri gmmIntentUri = Uri.parse(direction);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
                break;
            }

            case R.id.btnAddComments:
            {
                Intent intent = new Intent();
                intent.setClass(DetailActivity.this, CommentActivity.class);
                startActivity(intent);
            }
		}

	}


    /**
     * Refresh the list with the items in the Table
     */
    private void refreshItemsFromTable() {

        // Get the items that weren't marked as completed and add them in the
        // adapter

        AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>(){
            @Override
            protected Void doInBackground(Void... params) {

                try {
                    final List<ToDoItem> results = refreshItemsFromMobileServiceTable();
                    float taste_score=0;
                    float environment_score =0;
                    float service_score =0;
                    for (ToDoItem item : results) {
                        taste_score += item.gettasteRank();
                        environment_score += item.getEnvironmentRank();
                        service_score += item.getServiceRank();
                    }
                    taste_score = (float) ((int)(taste_score/results.size()*10)/10.0);
                    environment_score = (float) ((int)(environment_score/results.size()*10)/10.0);
                    service_score = (float) ((int)(service_score/results.size()*10)/10.0);
                    Message msg = new Message();
                    msg.what = 1;
                    msg.obj =(taste_score+" "+environment_score+" "+service_score);
                    handler.sendMessage(msg);
                    //Offline Sync
                    //final List<ToDoItem> results = refreshItemsFromMobileServiceTableSyncTable();

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mAdapter.clear();
                            for (ToDoItem item : results) {
                                mAdapter.add(item);
                            }
                        }
                    });
                    // after loading adpater let loading bar gone
                    new Thread()
                    {
                        public void run()
                        {
                            Message msg = new Message();
                            msg.what = 2;
                            handler.sendMessage(msg);
                        }
                    }.start();
                } catch (final Exception e){
                    createAndShowDialogFromTask(e, "Error");
                }
                return null;
            }
        };
        runAsyncTask(task);
    }

    /**
     * Refresh the list with the items in the Mobile Service Table
     */

    private List<ToDoItem> refreshItemsFromMobileServiceTable() throws ExecutionException, InterruptedException {
        System.out.println("AAAA+ lchangdu"+ mToDoTable.where().field("restaurant").
                eq("a1").execute().get().size());
        return mToDoTable.where().field("restaurant").
                eq("a1").execute().get();
    }

    private AsyncTask<Void, Void, Void> initLocalStore() throws MobileServiceLocalStoreException, ExecutionException, InterruptedException {

        AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {

                    MobileServiceSyncContext syncContext = mClient.getSyncContext();

                    if (syncContext.isInitialized())
                        return null;

                    SQLiteLocalStore localStore = new SQLiteLocalStore(mClient.getContext(), "OfflineStore", null, 1);

                    Map<String, ColumnDataType> tableDefinition = new HashMap<String, ColumnDataType>();
                    tableDefinition.put("id", ColumnDataType.String);
                    tableDefinition.put("text", ColumnDataType.String);
                    tableDefinition.put("complete", ColumnDataType.Boolean);

                    localStore.defineTable("ToDoItem", tableDefinition);

                    SimpleSyncHandler handler = new SimpleSyncHandler();

                    syncContext.initialize(localStore, handler).get();

                } catch (final Exception e) {
                    createAndShowDialogFromTask(e, "Error");
                }

                return null;
            }
        };

        return runAsyncTask(task);
    }
    private AsyncTask<Void, Void, Void> runAsyncTask(AsyncTask<Void, Void, Void> task) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            return task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        } else {
            return task.execute();
        }
    }
    private void createAndShowDialogFromTask(final Exception exception, String title) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                createAndShowDialog(exception, "Error");
            }
        });
    }


    private void createAndShowDialog(Exception exception, String title) {
        Throwable ex = exception;
        if(exception.getCause() != null){
            ex = exception.getCause();
        }
        createAndShowDialog(ex.getMessage(), title);
    }


    private void createAndShowDialog(final String message, final String title) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage(message);
        builder.setTitle(title);
        builder.create().show();
    }
    private class ProgressFilter implements ServiceFilter {

        @Override
        public ListenableFuture<ServiceFilterResponse> handleRequest(ServiceFilterRequest request, NextServiceFilterCallback nextServiceFilterCallback) {

            final SettableFuture<ServiceFilterResponse> resultFuture = SettableFuture.create();


            runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    //if (mProgressBar != null) mProgressBar.setVisibility(ProgressBar.VISIBLE);
                }
            });

            ListenableFuture<ServiceFilterResponse> future = nextServiceFilterCallback.onNext(request);

            Futures.addCallback(future, new FutureCallback<ServiceFilterResponse>() {
                @Override
                public void onFailure(Throwable e) {
                    resultFuture.setException(e);
                }

                @Override
                public void onSuccess(ServiceFilterResponse response) {
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            //if (mProgressBar != null) mProgressBar.setVisibility(ProgressBar.GONE);
                        }
                    });

                    resultFuture.set(response);
                }
            });

            return resultFuture;
        }
    }

}