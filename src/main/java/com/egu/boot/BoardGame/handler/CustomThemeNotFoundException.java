package com.egu.boot.BoardGame.handler;

public class CustomThemeNotFoundException extends RuntimeException {
	
	
	    public CustomThemeNotFoundException(String msg, Throwable t) {
	        super(msg, t);
	    }
	    
	    public CustomThemeNotFoundException(String msg) {
	        super(msg);
	    }
	    
	    public CustomThemeNotFoundException() {
	        super();
	    }
	

}

