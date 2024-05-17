package com.inconus.mealmanagement.model

import androidx.lifecycle.LiveData

class RecordSummaryRepository(private val recordSummaryDao: RecordSummaryDao) {
    fun getSummaryByDate(date: Long): LiveData<RecordSummary> {
        return recordSummaryDao.getSummaryByDate(date)
    }

    fun getAllSummaries(): LiveData<List<RecordSummary>> {
        return recordSummaryDao.getAllSummaries()
    }

    suspend fun insertSummary(summary: RecordSummary) {
        recordSummaryDao.insertSummary(summary)
    }
}