package com.example.gokart.data_converters

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