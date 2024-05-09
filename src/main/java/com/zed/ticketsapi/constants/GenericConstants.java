package com.zed.ticketsapi.constants;

public class GenericConstants {

    public static final String BEARER = "Bearer Authentication";

    public static final String OFFER_TAG = "Offer";

    public static final String TICKET_TAG = "Ticket";

    public static final String OFFER_DESCRIPTION = "Everything about offer of tickets";

    public static final String TICKET_DESCRIPTION = "Everything about ticket";

    public static final String PATTERN_UUID = "[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{12}";

    public static final String RESOURCE_ID = "tickets-api";

    public static final String RESOURCE_ACCESS = "resource_access";

    public static final String LOG_QUERY_DAO = "An error occurred when the query is execute. error : {}";

    public static final String MSG_ERROR_DAO = "An error occurred when executing query";


    private GenericConstants() {

    }
}
