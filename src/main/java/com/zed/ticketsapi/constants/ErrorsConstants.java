package com.zed.ticketsapi.constants;

public class ErrorsConstants {

    public static final String UUID_PATTERN_ERROR = "Invalid pattern for uuid";

    public static final String DATE_PATTERN_ERROR = "Invalid pattern for date";

    public static final String NAME_EMPTY = "The name of the offer is empty";

    public static final String FIRSTNAME_EMPTY = "The firstname of the ticket is empty";

    public static final String LASTNAME_EMPTY = "The lastname of the ticket is empty";

    public static final String DESCRIPTION_EMPTY = "The description of the offer is empty";

    public static final String NUMBER_TICKETS_EMPTY = "The number of the tickets in offer is not set";

    public static final String PRICE_EMPTY = "The price of the offer is not set";

    public static final String JWT_UNAUTHORIZED= "UNAUTHORIZED";

    public static final String JWT_FORMAT_INCORRECT = "Incorrect format for the JWT ! ";

    public static final String JWT_RESOURCE_ID_UNKNOWN = "Resource Id is unknown ! ";


    private ErrorsConstants() {

    }
}
