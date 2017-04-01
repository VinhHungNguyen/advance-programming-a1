package com.hung;

/**
 * Created by hungnguyen on 4/1/17.
 */
public class Sport {

    public static final int MIN_TIME_SWIMMING = 100;
    public static final int RANGE_TIME_SWIMMING = 100;
    public static final int MIN_TIME_CYCLING = 500;
    public static final int RANGE_TIME_CYCLING = 300;
    public static final int MIN_TIME_RUNNING = 10;
    public static final int RANGE_TIME_RUNNING = 10;

    public static final String ID_PREFIX_SWIMMING = "S";
    public static final String ID_PREFIX_CYCLING = "C";
    public static final String ID_PREFIX_RUNNING = "R";

    private String id;

    public Sport(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
