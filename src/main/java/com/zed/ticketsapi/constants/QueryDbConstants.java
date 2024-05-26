package com.zed.ticketsapi.constants;

public class QueryDbConstants {
    public static final String INSERT_INTO = "INSERT INTO ";

    public static final String INNER_JOIN = " INNER JOIN ";

    public static final String SELECT = "SELECT * ";

    public static final String DELETE = "DELETE ";

    public static final String UPDATE = "UPDATE ";

    public static final String FROM = "FROM ";

    public static final String WHERE = " WHERE ";

    public static final String SET = " SET ";

    public static final String ON = " ON ";

    public static final String VALUES = "VALUES ";

    public static final String QUESTION_MARK = "?";

    public static final String COMA = ",";

    public static final String DOT = ".";

    public static final String EQUAL = " = ";

    public static final String TABLE_OFFER = "t_offers";

    public static final String TABLE_TICKET = "t_tickets";

    public static final String TABLE_USER = "t_users";

    public static final String FORMAT_OFFER = " (idt_off, lib_off, lib_des, num_tick, num_price) ";

    public static final String FORMAT_TICKET = " (idt_tck, lib_firstname, lib_lastname, tmp, idt_usr) ";

    public static final String FORMAT_USER = " (idt_usr, mail) ";

    public static final String VALUES_5 = VALUES + "(?,?,?,?,?)";

    public static final String VALUES_2 = VALUES + "(?,?)";

    public static final String OFFER_ID = "idt_off";

    public static final String TICKET_ID = "idt_tck";

    public static final String USER_ID = "idt_usr";

    public static final String OFFER_NAME = "lib_off";

    public static final String OFFER_DESCRIPTION = "lib_des";

    public static final String OFFER_NUMBER_TICKETS = "num_tick";

    public static final String OFFER_PRICE = "num_price";

    public static final String TICKET_FIRSTNAME = "lib_firstname";

    public static final String TICKET_LASTNAME = "lib_lastname";

    public static final String TICKET_DATE = "tmp";

    public static final String TICKET_USER_ID = "idt_usr";

    public static final String USER_MAIL = "mail";

    public static final String OFFER_ID_EQUAL = OFFER_ID + EQUAL;

    public static final String TICKET_ID_EQUAL = TICKET_ID + EQUAL;

    public static final String USER_ID_EQUAL = USER_ID + EQUAL;

    public static final String OFFER_NAME_EQUAL = OFFER_NAME + EQUAL;

    public static final String OFFER_DESCRIPTION_EQUAL = OFFER_DESCRIPTION + EQUAL;

    public static final String OFFER_NUMBER_TICKETS_EQUAL = OFFER_NUMBER_TICKETS + EQUAL;

    public static final String OFFER_PRICE_EQUAL = OFFER_PRICE + EQUAL;

    public static final String INNER_JOIN_TICKET_USER_ID =  INNER_JOIN + TABLE_USER + ON + TABLE_TICKET + DOT + USER_ID
            + EQUAL + TABLE_USER + DOT + USER_ID;

    private QueryDbConstants() {

    }
}