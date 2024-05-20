package com.inconus.mealmanagement.model

import android.util.Log
import androidx.lifecycle.LiveData

class EmployeeRepository(private val employeeDao: EmployeeDao) {
    fun getRecordsBetweenDates(startDate: Long, endDate: Long): LiveData<List<EmployeeRecord>> {
        return employeeDao.getRecordsBetweenDates(startDate, endDate)
    }

    suspend fun insertRecord(record: EmployeeRecord) {
        try {
            Log.d("오애애애애ㅐ", "EmployeeRepository - 입력 레코드 : $record")
            val lastRecord= employeeDao.getLastRecord()
            if(lastRecord != null && lastRecord == record) {
                Log.d("오애애애애ㅐ", "EmployeeRepository - 동일한 레코드가 이미 존재합니다. 삽입을 건너뜁니다.")
                return
            }
            employeeDao.insertRecord(record)
            Log.d("오애애애애ㅐ", "EmployeeRepository - 레코드가 성공적으로 삽입되었습니다.")
        } catch (e: Exception) {
            Log.e("오애애애애ㅐ", "EmployeeRepository - Error inserting record", e)
            throw e
        }
    }

    suspend fun deleteOldRecords(thresholdDate: Long) {
        employeeDao.deleteOldRecords(thresholdDate)
    }
}
