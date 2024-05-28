package com.inconus.mealmanagement.model

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface RecordSummaryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSummary(summary: RecordSummary)

    @Update
    suspend fun updateSummary(summary: RecordSummary)

    @Query("SELECT * FROM record_summaries WHERE date = :date")
    suspend fun getSummaryByDate(date: Long): RecordSummary?

    @Query("SELECT * FROM record_summaries ORDER BY date DESC")
    fun getAllSummaries(): LiveData<List<RecordSummary>>

    @Query("UPDATE record_summaries SET count = :count WHERE date = :date")
    suspend fun updateSummaryByDate(date: Long, count: Int)

    @Query("SELECT * FROM record_summaries WHERE SUBSTR(CAST(date AS TEXT), 1, 6) = SUBSTR(CAST(:date AS TEXT), 1, 6) ORDER BY date DESC")
    suspend fun getSummariesByMonth(date:Long) : List<RecordSummary>
}
