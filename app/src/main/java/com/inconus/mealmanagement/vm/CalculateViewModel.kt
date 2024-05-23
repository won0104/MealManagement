package com.inconus.mealmanagement.vm

import androidx.lifecycle.ViewModel
import com.inconus.mealmanagement.model.EmployeeRepository
import com.inconus.mealmanagement.model.RecordSummaryRepository
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class CalculateViewModel(
    private var employeeRepository: EmployeeRepository,
    private var recordSummaryRepository: RecordSummaryRepository
) : ViewModel() {

    private val currentDate: LocalDateTime = LocalDateTime.now()
    private val formatter = DateTimeFormatter.ofPattern("yyyyMMdd")
    val formattedDate = currentDate.format(formatter).toLong()


}

// Select COUNT(*) From employee_records Where dateScanned BETWEEN 2024052100 and 2024052124
