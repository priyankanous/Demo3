package com.screensaver.quotes;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelper extends SQLiteOpenHelper{
	 
    private static String DB_PATH = "/data/data/com.screensaver.quotes/databases/";
 
    private static String DB_NAME = "quotations";
 
    private SQLiteDatabase myDataBase; 
 
    private final Context myContext;
 
    public DataBaseHelper(Context context) {
    	super(context, DB_NAME, null, 1);
        this.myContext = context;
    }	
 
	public void createDataBase() throws IOException { 
    	boolean dbExist = checkDataBase();
 
    	if(dbExist){
    		System.out.println("DB exsist, nothing to do");
    	} else {

        	this.getReadableDatabase();
 
        	try {
 
    			copyDataBase();
 
    		} catch (IOException e) {
 
        		throw new Error("Error copying database");
 
        	}
    	}
 
    }
 
    private boolean checkDataBase(){
 
    	SQLiteDatabase checkDB = null;
 
    	try{
    		String myPath = DB_PATH + DB_NAME;
    		checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
 
    	}catch(SQLiteException e){
 
    		System.out.println("DB does not exsist");
 
    	}
 
    	if(checkDB != null){
 
    		checkDB.close();
 
    	}
 
    	return checkDB != null ? true : false;
    }
 
    private void copyDataBase() throws IOException{
 
    	InputStream myInput = myContext.getAssets().open(DB_NAME);
 
    	String outFileName = DB_PATH + DB_NAME;
 
    	OutputStream myOutput = new FileOutputStream(outFileName);
 
    	byte[] buffer = new byte[1024];
    	int length;
    	while ((length = myInput.read(buffer))>0){
    		myOutput.write(buffer, 0, length);
    	}
 
    	myOutput.flush();
    	myOutput.close();
    	myInput.close();
 
    }
 
    public void openDataBase() throws SQLException{
 
        String myPath = DB_PATH + DB_NAME;
    	myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
    }
 
    @Override
	public synchronized void close() {
 
    	    if(myDataBase != null)
    		    myDataBase.close();
 
    	    super.close();
 
	}
 
	@Override
	public void onCreate(SQLiteDatabase db) {
 
	}
 
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
 
	}
 
	public int getQuotesCount() {
		int count = 0;
        String countQuery = "SELECT  * FROM " + "Quotes";
        Cursor cursor = myDataBase.rawQuery(countQuery, null);
        count = cursor.getCount();
        cursor.close(); 
        return count;
    }
	
	 public List<Quotes> getAllQuotes() {
		    List<Quotes> quotesList = new ArrayList<Quotes>();
		    String selectQuery = "SELECT  * FROM " + "Quotes";
		 
		    Cursor cursor = myDataBase.rawQuery(selectQuery, null);
		 
		    if (cursor.moveToFirst()) {
		        do {
		        	Quotes quote = new Quotes();
		        	quote.setID(Integer.parseInt(cursor.getString(0)));
		        	quote.setName(cursor.getString(1));
		        	quotesList.add(quote);
		        } while (cursor.moveToNext());
		    }
		 
		    return quotesList;
		}
 
}