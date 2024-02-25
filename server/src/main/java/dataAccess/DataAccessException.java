package dataAccess;

/**
 * Indicates there was an error connecting to the database
 */
public class DataAccessException extends Exception{
    final private int statusCode;

    public DataAccessException(int statusCode, String message) {
        super(message);
        this.statusCode = statusCode;
    }
//    public DataAccessException(String message){
//        super(message);
//    }
    public int getStatusCode(){
        return this.statusCode;
    }
}