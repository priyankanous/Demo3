package com.screensaver.quotes;

public class Quotes {
	int _id;
    String _quote;
    
    public Quotes() {
        
    }

    public Quotes(int id, String text) {
        this._id = id;
        this._quote = text;
    }
    
    public void setID(int id) {
        this._id = id;
    }
    
    public void setName(String quote) {
    	this._quote = quote;
    }
    
    public String getName() {
    	return this._quote;
    }
}
