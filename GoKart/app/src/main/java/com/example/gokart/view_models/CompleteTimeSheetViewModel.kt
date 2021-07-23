package com.example.gokart.view_models

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.gokart.database.AppDatabase
import com.example.gokart.database.entity.TimeSheetWithLaps

class CompleteTimeSheetViewModel(application: Application) : AndroidViewModel(application) {

    private val timeSheetDao = AppDatabase.getMemoryInstance(application).timeSheetDao()
    private var completeTimeSheets = timeSheetDao.getAllComplex()

    fun get() : LiveData<List<TimeSheetWithLaps>> {
        return completeTimeSheets
    }

}