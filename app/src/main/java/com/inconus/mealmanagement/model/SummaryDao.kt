package com.inconus.mealmanagement.model

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface SummaryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSummary(summary: Summary)

    @Update
    suspend fun updateSummary(summary: Summary)

    @Query("SELECT * FROM record_summaries WHERE date = :date")
    suspend fun getSummaryByDate(date: Long): Summary?

    @Query("SELECT * FROM record_summaries ORDER BY date DESC")
    fun getAllSummaries(): LiveData<List<Summary>>

    @Query("UPDATE record_summaries SET count = :count WHERE date = :date")
    suspend fun updateSummaryByDate(date: Long, count: Int)

    @Query("SELECT * FROM record_summaries WHERE SUBSTR(CAST(date AS TEXT), 1, 6) = SUBSTR(CAST(:date AS TEXT), 1, 6) ORDER BY date DESC")
    fun getSummariesByMonth(date:Long) : LiveData<List<Summary>>
}
