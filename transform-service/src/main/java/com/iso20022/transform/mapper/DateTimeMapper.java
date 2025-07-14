package com.iso20022.transform.mapper;

import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.GregorianCalendar;

/**
 * Mapper for date/time conversions between SWIFT and ISO 20022 formats
 */
@Component
@Named("DateTimeMapper")
public class DateTimeMapper {

    /**
     * Converts SWIFT date format (YYMMDD) to XMLGregorianCalendar
     */
    @Named("swiftDateToXmlDate")
    public XMLGregorianCalendar swiftDateToXmlDate(String swiftDate) throws DatatypeConfigurationException {
        if (swiftDate == null || swiftDate.length() != 6) {
            return null;
        }
        
        int year = 2000 + Integer.parseInt(swiftDate.substring(0, 2));
        int month = Integer.parseInt(swiftDate.substring(2, 4));
        int day = Integer.parseInt(swiftDate.substring(4, 6));
        
        return DatatypeFactory.newInstance()
                .newXMLGregorianCalendarDate(year, month, day, 0);
    }
    
    /**
     * Converts SWIFT datetime format to XMLGregorianCalendar
     */
    @Named("swiftDateTimeToXmlDateTime")
    public XMLGregorianCalendar swiftDateTimeToXmlDateTime(String swiftDateTime) throws DatatypeConfigurationException {
        if (swiftDateTime == null) {
            return null;
        }
        
        // Example: 2101011200 for 2021-01-01T12:00:00
        int year = 2000 + Integer.parseInt(swiftDateTime.substring(0, 2));
        int month = Integer.parseInt(swiftDateTime.substring(2, 4));
        int day = Integer.parseInt(swiftDateTime.substring(4, 6));
        int hour = swiftDateTime.length() > 6 ? Integer.parseInt(swiftDateTime.substring(6, 8)) : 0;
        int minute = swiftDateTime.length() > 8 ? Integer.parseInt(swiftDateTime.substring(8, 10)) : 0;
        int second = swiftDateTime.length() > 10 ? Integer.parseInt(swiftDateTime.substring(10, 12)) : 0;
        
        return DatatypeFactory.newInstance()
                .newXMLGregorianCalendar(
                        year, month, day,
                        hour, minute, second, 0, 0
                );
    }
    
    /**
     * Converts LocalDateTime to XMLGregorianCalendar
     */
    @Named("localDateTimeToXmlDateTime")
    public XMLGregorianCalendar localDateTimeToXmlDateTime(LocalDateTime dateTime) throws DatatypeConfigurationException {
        if (dateTime == null) {
            return null;
        }
        
        GregorianCalendar gcal = GregorianCalendar.from(dateTime.atZone(ZoneId.systemDefault()));
        return DatatypeFactory.newInstance().newXMLGregorianCalendar(gcal);
    }
}
