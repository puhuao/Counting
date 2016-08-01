package com.wksc.counting.exception;

/**
 * the root exception 
 * @author wanglin
 */
public class MyException extends Exception {
    private static final long serialVersionUID =  -2431196726844826744L;
    
    protected MyException(){
        super();
    }

    protected MyException(Throwable t){
        super(t);
    }
    
    public MyException(String msg){
        super(msg);
    }
    
    public MyException(String msg, Throwable t){
        super(msg,t);
    }
}
