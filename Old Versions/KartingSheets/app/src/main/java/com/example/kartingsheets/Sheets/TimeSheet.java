package com.example.kartingsheets.Sheets;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v4.app.INotificationSideChannel;

import java.util.ArrayList;
import java.util.Date;

public class TimeSheet {

    /** Static Class **/
    //Static Values
    //Date
    public static final Date DATE_NOW = new Date();
    public static final Date DATE_UNDEFINED = null;

    //Weather
    public static final String WEATHER_UNDEFINED = "UNDEFINED";
    public static final String WEATHER_SUNNY = "DRY";
    public static final String WEATHER_RAIN = "RAIN";
    public static final String WEATHER_CLOUDY = "CLOUDY";

    //Track conditions
    public static final String TRACK_UNDEFINED = "UNDEFINED";
    public static final String TRACK_DRY = "DRY";
    public static final String TRACK_WET = "WET";
    public static final String TRACK_WET_OIL = "WET+OIL";

    //Other
    public static final String USER_UNDEFINED = "UNDEFINED";
    public static final String KART_UNDEFINED = "UNDEFINED";

    public static final int PARSING_MODE_SIMPLE = 0;
    public static final int PARSING_MODE_COMPLETE = 1;

    //Static Methods
    /*
     * Function: parseLap
     * Arguments: Long [, int]
     * Output: String
     * Description:
     *    Transforms a milliseconds time into a readable string
     */
    @SuppressLint("DefaultLocale")
    public static String parseLap(long lap_ms, int mode){

        int mins = 0;
        int secs = 0;
        long msec= 0;

        secs = (int) (lap_ms / 1000);
        mins = (int) (secs / 60);
        msec = (int) (lap_ms % 1000);
        secs = (int) (secs % 60);

        switch (mode){

            case PARSING_MODE_COMPLETE:
                return String.format("%d.%02d:%03d", mins, secs, msec);

            case PARSING_MODE_SIMPLE:
                if(mins != 0){
                    return String.format("%d.%02d:%03d", mins, secs, msec);
                }else{
                    if(secs !=0 ){
                        return String.format("%d:%03d", secs, msec);
                    }else {
                        return String.format("%03dms", msec);
                    }
                }

            default:
                return null;
        }
    }

    public static String parseLap(long lap_ms){
        return parseLap(lap_ms, PARSING_MODE_COMPLETE);
    }

    /*
    * Function: toMilliseconds
    * Arguments: String
    * Output: Long
    * Description:
    *   Turns a string with format "mm:ss:ms" into a milliseconds representation of it
    */
    public static long toMilliseconds(String lap){

        int mins;
        int secs;
        int msec;
        
        String[] split1 = lap.split(":");
        String[] split2 = split1[0].split("\\.");

        msec = Integer.parseInt(split1[1]);
        secs = Integer.parseInt(split2[1]);
        mins = Integer.parseInt(split2[0]);

        return msec + ( (secs + (mins * 60)) * 1000  );

    }

    public static String parse_dif(long dif){
        if( dif < 0)
            return "-"+TimeSheet.parseLap(-dif, PARSING_MODE_SIMPLE);
        else
            return "+"+TimeSheet.parseLap(dif, PARSING_MODE_SIMPLE);

    }


    /** Objective Class **/
    private Date sheetDate;
    private String userID;
    private String kartID;
    private String weather;
    private String trackCondition;

    private long purpleLap;
    private long redLap;

    private Long[] laps;
    private int[] difPerLap; // has negative values
    private int[] difFromPurpleLap; // always positive

    private TableSheetFragment tableSheetFragment;

    //Constructor
    public TimeSheet(String userID, String kartID, ArrayList<Long> laps, Date date, String weather,
                     String trackCondition){

        this.sheetDate = date;
        this.userID = userID;
        this.kartID = kartID;
        this.weather = weather;
        this.trackCondition = trackCondition;

        this.purpleLap = getPurpleLap(laps);
        this.redLap = getRedLap(laps);

        this.laps = new Long[laps.size()];
        this.laps = laps.toArray(this.laps);

        this.difPerLap = getDifFromLastLap(laps);
        this.difFromPurpleLap = getDifFromPurpleLap(laps);

    }

    public TimeSheet(String userID, String kartID, ArrayList<Long> laps){
        this(userID,kartID,laps, DATE_NOW, WEATHER_UNDEFINED, TRACK_UNDEFINED);
    }

    //** Public Methods **//

    public TableSheetFragment getTableSheetFragment() {
        if(this.tableSheetFragment != null )
            return this.tableSheetFragment;
        return this.tableSheetFragment = new TableSheetFragment( this );
    }

    // Get
    public long getPurpleLap() {
        return purpleLap;
    }

    public long getRedLap() {
        return redLap;
    }

    public Date getSheetDate() {
        return sheetDate;
    }

    public String getKartID() {
        return kartID;
    }

    public String getUserID() {
        return userID;
    }

    //** Private Methods **//

    // returns the fastest lap from laps
    // returns -1 if laps is empty or null
    private long getPurpleLap( ArrayList<Long> laps ){
        if( laps == null || laps.isEmpty() )
            return -1;
        long purpleLap = laps.get(0);

        for (long lap: laps){
            if( lap < purpleLap )
                purpleLap = lap;
        }

        return purpleLap;
    }

    // returns the slowest lap from laps
    // returns -1 if laps is empty or null
    private long getRedLap( ArrayList<Long> laps ){
        if( laps == null || laps.isEmpty() )
            return -1;
        long redLap = laps.get(0);

        for (long lap: laps){
            if( lap > redLap )
                redLap = lap;
        }

        return redLap;
    }

    private int[] getDifFromPurpleLap( ArrayList<Long> laps){
        if( laps == null || laps.isEmpty() )
            return null;

        if( purpleLap == 0 )
            purpleLap = getPurpleLap(laps);

        int[] difArr = new int[laps.size()];

        for (int i = 0; i<laps.size(); i++){
            difArr[i] = (int) (laps.get(i) - this.purpleLap);
        }

        return difArr;
    }

    private int[] getDifFromLastLap( ArrayList<Long> laps ){
        if( laps == null || laps.isEmpty() )
            return null;

        int[] difArr = new int[laps.size()];
        long lastLap = laps.get(0);
        difArr[0] = 0;

        for (int i = 1; i<laps.size(); i++){
            difArr[i] = (int) (laps.get(i) - lastLap);
            lastLap = laps.get(i);
        }

        return difArr;
    }

}
