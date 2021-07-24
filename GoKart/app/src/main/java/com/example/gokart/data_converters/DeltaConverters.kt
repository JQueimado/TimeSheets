package com.example.gokart.data_converters

/// delta format +/-M?:SS.sss

fun String.toIntDelta() : Int {
    val min: Int
    val sec: Int
    val mls: Int
    val signal = this[0]
    val text = this.substring(1,this.length)

    val split1 = text.split(":")
    val split2 : List<String>?
    if ( split1.size != 1 ) {
        min = split1[0].toInt()
        split2 = split1[1].split(".")
    }else{
        min = 0
        split2 = split1[0].split(".")
    }

    sec = split2[0].toInt()
    mls = split2[1].toInt()

    return (if(signal == '-' ) -1 else 1 ) * (((( min * 60 ) + sec ) * 1000 ) + mls)
}

fun Int.toStringDelta() : String{
    val value = if( this < 0  ) -1*this else this

    val temp = value / 1000
    val mls = value % 1000
    val sec = temp % 60
    val min = temp / 60

    val format = if( min == 0 )
        "%d.%03d".format( sec,mls )
    else
        "%d:%02d.%03d".format( min,sec,mls )

    return if( this < 0 ) "-$format" else "+$format"
}