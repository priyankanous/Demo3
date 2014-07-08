package com.screensaver.quotes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.content.SharedPreferences;
import android.database.SQLException;
import android.graphics.Typeface;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.service.dreams.DreamService;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

public class QuoteScreensaver extends DreamService {
	List<Quotes> quotesList = new ArrayList<Quotes>();
	Handler mHandler;
	TextView textViewToChange;
	static int counter = 0;
	int max_count = 0;
	String delay;
	
	@Override
	public void onDreamingStarted() {
		counter++;
		if (max_count <= counter)
			counter = 0;
     	textViewToChange.setText(quotesList.get(counter).getName());
     	
     	mHandler = new Handler();
        mHandler.post(mUpdate); 
	}
	
	private Runnable mUpdate = new Runnable() {
	   public void run() {
		   System.out.println(counter);
		   counter++;
			if (max_count <= counter)
				counter = 0;
		   textViewToChange.setText(quotesList.get(counter).getName());
		   //Configurable time limit
		   System.out.println(delay);
	       mHandler.postDelayed(this, Integer.parseInt(delay));
	    }
	};
	
	@Override
	public void onDreamingStopped(){
		mHandler.removeMessages(0);
	}
	
	@Override
	public void onAttachedToWindow() {
		super.onAttachedToWindow();
		setInteractive(false);
		setFullscreen(true);
		
		DataBaseHelper myDbHelper = new DataBaseHelper(this);
		 
        try {
         	myDbHelper.createDataBase(); 
	 	} catch (IOException ioe) {	 
	 		throw new Error("Unable to create database");	 
	 	}
 
        try {
         	myDbHelper.openDataBase();
         	max_count = myDbHelper.getQuotesCount();
         	
         	System.out.println(max_count);
         	
         	quotesList = myDbHelper.getAllQuotes();
         	
         	
	  	}catch(SQLException sqle){
	 	 		throw sqle;
	  	}
		setContentView(R.layout.screen_slide);
		myDbHelper.close();
		
		textViewToChange = (TextView) findViewById(R.id.quote);
		textViewToChange.setGravity(Gravity.CENTER | Gravity.CENTER_VERTICAL);
		textViewToChange.setTextAppearance(this, android.R.style.TextAppearance_Large);
		
		String fontPath = "fonts/a song for jennifer.ttf";
		Typeface tf = Typeface.createFromAsset(this.getAssets(), fontPath);
		textViewToChange.setTypeface(tf);
		
		SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
		delay = settings.getString("updates_interval", "30000");
		System.out.println(delay);
	}
	
	@Override
	public void onDetachedFromWindow() {
		super.onDetachedFromWindow();
	}
	
	public void onClick(View v){
	//handle clicks
	}
}