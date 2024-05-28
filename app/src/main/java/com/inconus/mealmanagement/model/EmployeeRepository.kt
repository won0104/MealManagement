package com.inconus.mealmanagement.model

import android.util.Log
import androidx.lifecycle.LiveData

class EmployeeRepository(private val employeeDao: EmployeeDao) {

    suspend fun getRecordsByDate(inputDate: Long):List<EmployeeRecord>{
        return employeeDao.getRecordsByDate(inputDate)
    }

    /**
     * 레코드를 삽입하고 삽입 성공 여부를 반환
     * @param record 삽입할 레코드
     * @return true 레코드가 성공적으로 삽입된 경우, false 동일한 레코드가 이미 존재하는 경우
     */
    suspend fun insertRecord(record: EmployeeRecord): Boolean {
        try {
            Log.d("확인", "EmployeeRepository - 입력 레코드 : $record")
            val findDate = record.dateScanned

            // 직접 비교를 위해 suspend 함수의 결과를 사용
            // 같은 시간에 저장된 레코드를 가져와 삽입될 레코드와 같은지 비교
            val records = employeeDao.getRecordsBetweenDates(findDate, findDate)
            if (records.any { existingRecord -> existingRecord == record }) {
                Log.d("확인", "EmployeeRepository - 동일한 레코드가 이미 존재합니다. 삽입을 건너뜁니다.")
                return false
            } else {
                employeeDao.insertRecord(record)
                Log.d("확인", "EmployeeRepository - 레코드가 성공적으로 삽입되었습니다.")
                return true
            }
        } catch (e: Exception) {
            Log.e("확인", "EmployeeRepository - Error inserting record", e)
            throw e
        }
    }

    suspend fun getRecordSummary() : List<RecordSummary>{
        return employeeDao.getRecordSummary()
    }
}
