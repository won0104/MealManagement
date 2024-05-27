package com.inconus.mealmanagement.model

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface EmployeeDao {

    @Insert
    suspend fun insertRecord(record: EmployeeRecord)

    // 해당 날짜에 스캔된 직원 리스트 모두 불러오기
    @Query("SELECT * FROM employee_records WHERE SUBSTR(CAST(dateScanned AS TEXT), 1, 8) = SUBSTR(CAST(:inputDate AS TEXT), 1, 8)")
    suspend fun getRecordsByDate(inputDate : Long) : List<EmployeeRecord>

    //  Summery애 저장할 데이터 받아오기
    @Query("SELECT substr(cast(dateScanned as text), 1, 8) as date, COUNT(*) as count FROM employee_records GROUP BY substr(cast(dateScanned as text), 1, 8)")
    suspend fun getRecordSummary(): List<RecordSummary>

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
