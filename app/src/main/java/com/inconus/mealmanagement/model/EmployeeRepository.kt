package com.inconus.mealmanagement.model

import androidx.lifecycle.LiveData

class EmployeeRepository(private val employeeDao: EmployeeDao) {
    fun getRecordsBetweenDates(startDate: Long, endDate: Long): LiveData<List<EmployeeRecord>> {
        return employeeDao.getRecordsBetweenDates(startDate, endDate)
    }

    // 중복 저장 되지 않게
    suspend fun insertRecord(record: EmployeeRecord) {
        val lastRecord = employeeDao.getLastRecord()
        if (lastRecord==null || lastRecord != record){
            employeeDao.insertRecord(record)
        }
    }

    suspend fun deleteOldRecords(thresholdDate: Long) {
        employeeDao.deleteOldRecords(thresholdDate)
    }
}
