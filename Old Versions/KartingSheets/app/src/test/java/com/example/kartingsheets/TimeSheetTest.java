package com.example.kartingsheets;

import com.example.kartingsheets.Sheets.TimeSheet;

import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.*;

public class TimeSheetTest {

    @Test
    public void testTests(){
        assertFalse( 0 == 1);
    }

    @Test
    public void check_conversions(){
        Random random = new Random();
        String time = String.format(
                "%d.%02d:%03d",
                random.nextInt(5),
                random.nextInt(60),
                random.nextInt(1000));

        long conv = TimeSheet.toMilliseconds(time);
        String back = TimeSheet.parseLap(conv);
        assertEquals( back, time );
    }

    @Test
    public void dif_positive_test(){
        int dif = 5600;
        String dif_string = "+5:600";
        assertEquals( TimeSheet.parse_dif(dif), dif_string );
    }

}
