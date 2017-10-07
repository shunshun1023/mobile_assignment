package com.df.widget;

import com.df.dianping.R;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

public class PoiListItem extends LinearLayout
{

	private static int CARD_PADDING = 0;
	private static int CHECKIN_PADDING = 0;
	public static float DISTANCE_FACTOR = 1.0F;
	private static int PROMO_PADDING = 0;
	private static int BUSY_PADDING = 0;

	View card;
	View checkin;
	PoiStar star;
	View promo;
	String name;
	TextView price;

	TextView distance;
	ImageView busy = (ImageView) findViewById(R.id.busy);

	public PoiListItem(Context context) {
		super(context);
	}

	public PoiListItem(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	public String getName(){
		return this.name;
	}
	public void setPoiData(String restaurantName, int price, String addr, int stars, int isBusy,String dis) {
        TextView addressView = (TextView) findViewById(R.id.addr);
        addressView.setText(addr);

        TextView priceView = (TextView) findViewById(R.id.price);
        priceView.setText("Cost: $"+price);
        TextView nameView = (TextView) findViewById(R.id.name);
        nameView.setText(restaurantName);
        this.name = restaurantName;
        TextView distanceView = (TextView) findViewById(R.id.distance);
        distanceView.setText(dis);
        PoiStar starView =(PoiStar) findViewById(R.id.poistar);
        starView.setStar(stars);
		switch(isBusy){
			case 0:
				ImageView imageviewGreen = (ImageView) findViewById(R.id.busy);
				Drawable myDrawableGreen = getResources().getDrawable(R.drawable.ic_green);
				imageviewGreen.setImageDrawable(myDrawableGreen);
				System.out.println("BBBB:green");
				break;
			case 1:
				ImageView imageviewYellow = (ImageView) findViewById(R.id.busy);
				Drawable myDrawableYellow = getResources().getDrawable(R.drawable.ic_yellow);
				imageviewYellow.setImageDrawable(myDrawableYellow);
				System.out.println("BBBB:yellow");
				break;
			case 2:
				ImageView imageviewRed = (ImageView) findViewById(R.id.busy);
				Drawable myDrawableRed = getResources().getDrawable(R.drawable.ic_red);
				imageviewRed.setImageDrawable(myDrawableRed);
				System.out.println("BBBB:red");
				break;
		}

	}

	public void setDistanceText(String dis)
	{
		this.distance.setText(dis);
	}

}