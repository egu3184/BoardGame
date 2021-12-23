package com.egu.boot.BoardGame.handler;

public class CustomReservationNotFoundException extends RuntimeException {
	
	
	    public CustomReservationNotFoundException(String msg, Throwable t) {
	        super(msg, t);
	    }
	    
	    public CustomReservationNotFoundException(String msg) {
	        super(msg);
	    }
	    
	    public CustomReservationNotFoundException() {
	        super();
	    }
	

}

