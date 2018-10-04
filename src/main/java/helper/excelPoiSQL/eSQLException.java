package helper.excelPoiSQL;

/**
 * Created by c0240259 on 22/06/2017.
 */
public class eSQLException extends Exception {
    private String eSQLExceptionMessage;

    eSQLException(String eSQLExceptionMessage){
        this.eSQLExceptionMessage = eSQLExceptionMessage;
    }

    @Override
    public String toString(){
        return (this.eSQLExceptionMessage);
    }

}
