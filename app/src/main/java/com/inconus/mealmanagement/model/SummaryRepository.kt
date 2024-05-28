package com.inconus.mealmanagement.model

import androidx.lifecycle.LiveData

class SummaryRepository(private val summaryDao: SummaryDao) {

    suspend fun updateSummary(summaries: List<Summary>) {
        summaries.forEach { newSummary ->
            val oldSummary = summaryDao.getSummaryByDate(newSummary.date)
            if (oldSummary == null) {
                summaryDao.insertSummary(newSummary)
            } else if (oldSummary.count != newSummary.count) {
                summaryDao.updateSummaryByDate(newSummary.date, newSummary.count)
            }
        }
    }
    fun getAllSummaries(): LiveData<List<Summary>> {
        return summaryDao.getAllSummaries()
    }

    suspend fun getSummariesByMonth(date:Long):List<Summary>{
        return summaryDao.getSummariesByMonth(date)
    }
}