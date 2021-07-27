package com.example.gokart.data_converters

import android.util.Log

/// laps format M:SS.sss

fun String.toIntTimeSheet() : Int{
    //Extract values
    val split1 = this.split(":")
    val min = split1[0].toInt()

    val split2 = split1[1].split(".")
    val sec = split2[0].toInt()
    val mls = split2[1].toInt()

    return ((( min * 60 ) + sec ) * 1000 ) + mls
}

fun Int.toTextTimeStamp() : String{
    val temp = this / 1000
    val mls = this % 1000
    val sec = temp % 60
    val min = temp / 60
    return "%d:%02d.%03d".format( min,sec,mls )
}

fun String.toTextTimeStamp(): String{
    var text = this

    for (i in text.length until 6)
        text = "0$text"


    return text.substring(0, text.length - 5) +
            ":" + text.substring(text.length - 5, text.length - 3) +
            "." + text.substring(text.length - 3, text.length)
}