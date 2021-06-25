package com.example.gokart.Converters

class LapConverter {
    companion object{
        fun toInt( text : String ) :Int{
            //Extract values
            val split1 = text.split(":") // mm : ss.mss
            val min = split1[0].toInt()

            val split2 = split1[1].split(".")
            val sec = split2[0].toInt()
            val mls = split2[1].toInt()

            return ((( min * 60 ) + sec ) * 1000 ) + mls
        }

        fun toText( number : Int ) : String{
            return "" //TODO
        }
    }
}