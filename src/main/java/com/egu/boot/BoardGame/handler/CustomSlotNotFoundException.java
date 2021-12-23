package com.egu.boot.BoardGame.handler;

public class CustomSlotNotFoundException extends RuntimeException {
	
	
	    public CustomSlotNotFoundException(String msg, Throwable t) {
	        super(msg, t);
	    }
	    
	    public CustomSlotNotFoundException(String msg) {
	        super(msg);
	    }
	    
	    public CustomSlotNotFoundException() {
	        super();
	    }
	

}

