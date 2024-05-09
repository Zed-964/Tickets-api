package com.zed.ticketsapi.constants;

public class QueryDbConstants {
    public static final String INSERT_INTO = "INSERT INTO ";

    public static final String SELECT = "SELECT * ";

    public static final String DELETE = "DELETE ";

    public static final String UPDATE = "UPDATE ";

    public static final String FROM = "FROM ";

    public static final String WHERE = " WHERE ";

    public static final String SET = " SET ";

    public static final String VALUES = "VALUES ";

    public static final String VARIABLE = "?";

    public static final String NEXT_VARIABLE = ",";

    public static final String TABLE_OFFER = "t_offers";

    public static final String FORMAT_OFFER = " (idt_off, lib_off, lib_des, num_tick, num_price) ";

    public static final String VALUES_OFFER = VALUES + "(?,?,?,?,?)";

    public static final String OFFER_ID = "idt_off";

    public static final String OFFER_NAME = "lib_off";

    public static final String OFFER_DESCRIPTION = "lib_des";

    public static final String OFFER_NUMBER_TICKETS = "num_tick";

    public static final String OFFER_PRICE = "num_price";

    public static final String OFFER_ID_EQUAL = OFFER_ID + " = ";

    public static final String OFFER_NAME_EQUAL = OFFER_NAME + " = ";
    public static final String OFFER_DESCRIPTION_EQUAL = OFFER_DESCRIPTION + " = ";
    public static final String OFFER_NUMBER_TICKETS_EQUAL = OFFER_NUMBER_TICKETS + " = ";
    public static final String OFFER_PRICE_EQUAL = OFFER_PRICE + " = ";



    private QueryDbConstants() {

    }
}
