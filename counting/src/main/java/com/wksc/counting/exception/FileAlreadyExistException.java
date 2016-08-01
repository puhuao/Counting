package com.wksc.counting.exception;

/**
 * 文件已经存在异常
 * 
 * @author wanglin@gohighedu.com
 * @date 2013-5-23 下午4:46:34
 */
public class FileAlreadyExistException extends MyException {
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    public FileAlreadyExistException(String message) {
        
        super(message);
    }
    
}
