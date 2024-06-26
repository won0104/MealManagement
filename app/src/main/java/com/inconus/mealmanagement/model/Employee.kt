package com.inconus.mealmanagement.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "employee_records")
data class Employee(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name : String,
    val number: Int, // 휴대폰 번호
    var dateScanned: Long // 인식 날짜 (예: 20230401HH)
) {
    // id제외, name, numberm dataScanned가 같으면 같다고 판단
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Employee

        if (name != other.name) return false
        if (number != other.number) return false
        if (dateScanned != other.dateScanned) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + number
        result = 31 * result + dateScanned.hashCode()
        return result
    }
}
