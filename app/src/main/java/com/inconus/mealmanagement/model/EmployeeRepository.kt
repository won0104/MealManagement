package com.inconus.mealmanagement.model

import android.util.Log
import androidx.lifecycle.LiveData

class EmployeeRepository(private val employeeDao: EmployeeDao) {
    fun getRecordsBetweenDates(startDate: Long, endDate: Long): LiveData<List<EmployeeRecord>> {
        return employeeDao.getRecordsBetweenDates(startDate, endDate)
    }

    /**
     * 레코드를 삽입하고 삽입 성공 여부를 반환합니다.
     * @param record 삽입할 레코드
     * @return true 레코드가 성공적으로 삽입된 경우, false 동일한 레코드가 이미 존재하는 경우
     */
    suspend fun insertRecord(record: EmployeeRecord) : Boolean{
        try {
            Log.d("오애애애애ㅐ", "EmployeeRepository - 입력 레코드 : $record")
            val lastRecord= employeeDao.getLastRecord()
            if(lastRecord != null && lastRecord == record) {
                Log.d("오애애애애ㅐ", "EmployeeRepository - 동일한 레코드가 이미 존재합니다. 삽입을 건너뜁니다.")
                return false
            } else{
            employeeDao.insertRecord(record)
            Log.d("오애애애애ㅐ", "EmployeeRepository - 레코드가 성공적으로 삽입되었습니다.")
            return true}
        } catch (e: Exception) {
            Log.e("오애애애애ㅐ", "EmployeeRepository - Error inserting record", e)
            throw e
        }
    }

    suspend fun deleteOldRecords(thresholdDate: Long) {
        employeeDao.deleteOldRecords(thresholdDate)
    }
}
