package com.inconus.mealmanagement.model

import android.util.Log
import androidx.lifecycle.LiveData

class SummaryRepository(private val summaryDao: SummaryDao) {

    /**
     * 입력된 리스트의 각 요약 정보(Summary)를 데이터베이스에 업데이트
     * 각 요약 정보는 날짜를 기준으로 기존 데이터와 비교되며, 필요에 따라 삽입 또는 업데이트 작업을 수행
     * @param summaries 업데이트하고자 하는 요약 정보의 리스트
     */
    suspend fun updateSummary(summaries: List<Summary>) {
        Log.d("확인용","updateSummary 실행")
        summaries.forEach { newSummary ->
            val oldSummary = summaryDao.getSummaryByDate(newSummary.date)
            if (oldSummary == null) {
                summaryDao.insertSummary(newSummary)
            } else if (oldSummary.count != newSummary.count) {
                summaryDao.updateSummaryByDate(newSummary.date, newSummary.count)
            }
        }
    }

    // 월 단위로 요약 정보 출력
    fun getSummariesByMonth(date:Long):LiveData<List<Summary>>{
        return summaryDao.getSummariesByMonth(date)
    }
}