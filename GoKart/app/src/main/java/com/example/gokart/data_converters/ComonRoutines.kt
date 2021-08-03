package com.example.gokart.data_converters

import com.example.gokart.database.AppDatabase

//Calculates the kart with most laps from a given database
suspend fun getFavouriteKart( databaseInstance: AppDatabase ): Long{

    val kartDao = databaseInstance.kartDao()
    val timeSheetDao = databaseInstance.timeSheetDao()
    val lapDAO = databaseInstance.lapDao()

    var kartId = -1L
    var kartLaps = -1L

    //Fetch all time sheets from a kart
    val kartIds = kartDao.getIds()

    if (kartIds.isEmpty())
        return -1

    for ( currentKartId in kartIds ){
        var laps = 0L

        //Fetch Lap Counts
        val kartSheetsIds = timeSheetDao.getAllIdsFromKart(currentKartId)
        for ( currentSheetId in kartSheetsIds )
            laps += lapDAO.getCountForTimeSheet(currentSheetId)

        //Compare Karts
        if ( laps > kartLaps ){
            kartLaps = laps
            kartId = currentKartId
        }
    }

    return if(kartLaps == 0L) -1L else kartId
}

//Calculates the kartingCenter with most laps from a given database
suspend fun getFavouriteKartingCenter( databaseInstance: AppDatabase ): Long{

    val kartingCenterDAO = databaseInstance.kartingCenterDao()
    val kartDao = databaseInstance.kartDao()
    val timeSheetDao = databaseInstance.timeSheetDao()
    val lapDAO = databaseInstance.lapDao()

    var kartingCenterId = -1L
    var kartingCenterLap = -1L

    //Fetch KartingCenters
    val kartingCentersIds = kartingCenterDAO.getAllIdsBlocking()

    if( kartingCentersIds.isEmpty() )
        return -1

    for( currentKartingCenterId in kartingCentersIds ){
        var laps = 0L

        //Fetch Karts
        val kartIds = kartDao.getIdsFromKartingCenter(currentKartingCenterId)
        for ( currentKartId in kartIds ) {

            //Fetch Time Lap counts
            val kartSheetsIds = timeSheetDao.getAllIdsFromKart(currentKartId)
            for (currentSheetId in kartSheetsIds)
                laps += lapDAO.getCountForTimeSheet(currentSheetId)
        }

        //Compare Karting Centers
        if( laps > kartingCenterLap ){
            kartingCenterLap = laps
            kartingCenterId = currentKartingCenterId
        }
    }

    return if( kartingCenterLap == 0L ) -1L else kartingCenterId
}

//Calculate Fastest Lap
suspend fun getFastestLap( databaseInstance: AppDatabase ): Int{

    val timeSheetDao = databaseInstance.timeSheetDao()

    val timeSheets = timeSheetDao.getAllSimpleBlocking()
    if (timeSheets.isEmpty())
        return -1

    var lap = -1

    for ( currentTimeSheet in timeSheets){
        if( lap == -1 || lap > currentTimeSheet.bestLap )
            lap = currentTimeSheet.bestLap
    }

    return lap
}