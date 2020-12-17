package si.fri.prpo.skupina57.storitve.dtos;

import java.util.Date;

public class PublicHolidayDto {

    private String date;

    private String localName;

    private String name;

    private String countryCode;

    private String type;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLocalName() {
        return localName;
    }

    public void setLocalName(String localName) {
        this.localName = localName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

/*
https://date.nager.at/Api/v2/PublicHolidays/2020/SI
base=date.nager.at/Api/v2/PublicHolidays/
year:
Date d=new Date();
        int year=d.getYear();
+ "/SI"

@ApplicationScoped
public class TodoApiOdjemalec {


}
* */
