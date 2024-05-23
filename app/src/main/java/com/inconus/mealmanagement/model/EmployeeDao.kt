package com.inconus.mealmanagement.model

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface EmployeeDao {

    @Insert
    suspend fun insertRecord(record: EmployeeRecord)

    // 특정 기간 동안 저장한 기록 불러오기
    @Query("SELECT * FROM employee_records WHERE dateScanned BETWEEN :startDate AND :endDate")
    suspend fun getRecordsBetweenDates(startDate: Long, endDate: Long): List<EmployeeRecord>

    // 가장 마지막에 저장된 기록 불러오기
    @Query("SELECT * FROM employee_records ORDER BY id DESC LIMIT 1")
    suspend fun getLastRecord(): EmployeeRecord?

    // 특정 기간 이전의 데이터 삭제
    @Query("DELETE FROM employee_records WHERE dateScanned < :thresholdDate")
    suspend fun deleteOldRecords(thresholdDate: Long)
}
