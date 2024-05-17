package com.inconus.mealmanagement.model

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RecordSummaryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSummary(summary: RecordSummary)

    // 날짜로 데이터 가져오기
    @Query("SELECT * FROM record_summaries WHERE date = :date")
    fun getSummaryByDate(date: Long): LiveData<RecordSummary>

    // 모든 요약 가져오기
    @Query("SELECT * FROM record_summaries ORDER BY date DESC")
    fun getAllSummaries(): LiveData<List<RecordSummary>>
}