package com.inconus.mealmanagement.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "employee_records")
data class EmployeeRecord(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name : String,
    val employeeCode: Int, // 휴대폰 번호
    var dateScanned: Long // 인식 날짜 (예: 20230401HH)
)
