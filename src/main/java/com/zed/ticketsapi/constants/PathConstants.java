package com.zed.ticketsapi.constants;

public class PathConstants {

    public static final String SLASH_PATH = "/";

    public static final String ALL_PATH = "**";

    public static final String API_PATH = "api/";

    public static final String VERSION_API_PATH = "v1/";

    public static final String VERSION_DOCS_PATH = "v3/";

    public static final String ME_PATH = SLASH_PATH + "me";

    public static final String CONTROLLER_V1_PATH = SLASH_PATH + API_PATH + VERSION_API_PATH;

    public static final String OFFERS_PATH = SLASH_PATH + "offers";

    public static final String OFFER_ID_PATH = OFFERS_PATH + SLASH_PATH + "{offerId}";

    public static final String TICKETS_PATH = SLASH_PATH + "tickets";

    public static final String TICKETS_ME_PATH = TICKETS_PATH + ME_PATH;

    public static final String TICKETS_PAYMENT_PATH = TICKETS_PATH + SLASH_PATH + "payment";

    public static final String ALL_SWAGGER_UI_PATH = SLASH_PATH + "swagger-ui" + SLASH_PATH + ALL_PATH;

    public static final String ALL_API_DOCS_PATH = SLASH_PATH + VERSION_DOCS_PATH + "api-docs" + SLASH_PATH + ALL_PATH;

    public static final String API_DOCS_PATH = SLASH_PATH + VERSION_DOCS_PATH + "api-docs";

    private PathConstants() {

    }
}
