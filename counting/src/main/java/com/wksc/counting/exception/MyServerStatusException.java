package com.wksc.counting.exception;




/**
 * server error such as error(500) or exception(401 403) & etc
 * @author wanglin
 */
public class MyServerStatusException extends MyException{
    private static final long serialVersionUID = 8831634121316777078L;

    /**
     * exception code
     */
    public int code;
    

    @SuppressWarnings("unused")
    private MyServerStatusException(){
        super();
    }
    @SuppressWarnings("unused")
    private MyServerStatusException(Throwable t){
        super(t);
    }
    
    public MyServerStatusException(int code, String msg){
        super(msg);
        
        this.code = code;
    }
    
    public MyServerStatusException(int code, String msg, Throwable t){
        super(msg,t);
        
        this.code = code;
    }
    


}
