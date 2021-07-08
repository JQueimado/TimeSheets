package com.example.gokart

import com.example.gokart.data_converters.toIntTimeStamp
import com.example.gokart.data_converters.toTextTimeStamp
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test

class LapConverterTest {

    private val text = arrayListOf(
        "1:18.348",
        "1:18.355",
        "1:18.746",
        "1:18.890",
        "1:19.039",
        "1:19.042",
        "1:19.116",
        "1:19.306",
        "1:19.479",
        "1:19.659"
    )

    private val number = arrayListOf(
        78348,
        78355,
        78746,
        78890,
        79039,
        79042,
        79116,
        79306,
        79479,
        79659
    )

    @Test
    fun toNumberTest(){
        for ( i in 0 until text.size ){
            assertThat( text[i].toIntTimeStamp(), equalTo( number[i] ) )
        }
    }

    @Test
    fun toTextTest(){
        for ( i in 0 until number.size ){
            assertThat( number[i].toTextTimeStamp(), equalTo( text[i] ) )
        }
    }

    @Test
    fun backAndForwardTest(){
        var temp: Int
        for ( i in 0 until text.size ){
            temp = text[i].toIntTimeStamp()
            assertThat(temp.toTextTimeStamp(), equalTo( text[i] ) )
        }
    }

    @Test
    fun reverseBackAndForwardTest(){
        var temp: String
        for ( i in 0 until number.size ){
            temp = number[i].toTextTimeStamp()
            assertThat( temp.toIntTimeStamp(), equalTo( number[i] ) )
        }
    }
}