package com.inconus.mealmanagement.model

import androidx.lifecycle.LiveData

class RecordSummaryRepository(private val recordSummaryDao: RecordSummaryDao) {

    suspend fun updateSummary(summaries: List<RecordSummary>) {
        summaries.forEach { newSummary ->
            val oldSummary = recordSummaryDao.getSummaryByDate(newSummary.date)
            if (oldSummary == null) {
                recordSummaryDao.insertSummary(newSummary)
            } else if (oldSummary.count != newSummary.count) {
                recordSummaryDao.updateSummaryByDate(newSummary.date, newSummary.count)
            }
        }
    }
    fun getAllSummaries(): LiveData<List<RecordSummary>> {
        return recordSummaryDao.getAllSummaries()
    }

    suspend fun getSummariesByMonth(date:Long):List<RecordSummary>{
        return recordSummaryDao.getSummariesByMonth(date)
    }
}