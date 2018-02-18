package utilities;

import com.senla.hotel.api.internal.IFieldParser;

import java.util.Date;

public class FieldParser implements IFieldParser {
    public java.sql.Date parseDate(Date date){
        return new java.sql.Date(date.getTime());
    }

}
