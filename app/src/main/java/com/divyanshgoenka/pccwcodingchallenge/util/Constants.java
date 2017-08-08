package com.divyanshgoenka.pccwcodingchallenge.util;

/**
 * Created by divyanshgoenka on 03/08/17.
 */

public class Constants {

    public static class Keys{
        public static final String USER_ID = "USER_ID";
        public static final String CURRENT_PAGE = "CURRENT_PAGE";
        public static final String NO_OF_PAGES = "NO_OF_PAGES";
        public static final String ITEM_LIST = "ITEM_LIST";
        public static final String USER = "USER";
    }

    public static class Api{
        public static final String API_URL = "https://api.github.com/";
        public static final String DEFAULT_USER_ID = "divyanshgoenka-tinklabs";
    }

    public static class ScrollLogic{

        public static final int LOAD_MORE_OFFSET = 5;
        public static final int NO_ITEMS_PER_PAGE = 30;
        public static final int PAGE_START_ZERO = 0;
    }
}
