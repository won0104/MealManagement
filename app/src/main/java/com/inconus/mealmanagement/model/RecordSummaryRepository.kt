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

    suspend fun getSummaryByDate(date: Long): RecordSummary? {
        return recordSummaryDao.getSummaryByDate(date)
    }

    fun getAllSummaries(): LiveData<List<RecordSummary>> {
        return recordSummaryDao.getAllSummaries()
    }

    suspend fun insertSummary(summary: RecordSummary) {
        recordSummaryDao.insertSummary(summary)
    }

    suspend fun updateSummaryByDate(date: Long, count: Int) {
        recordSummaryDao.updateSummaryByDate(date, count)
    }
}