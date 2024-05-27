package com.inconus.mealmanagement.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.inconus.mealmanagement.model.EmployeeRecord
import com.inconus.mealmanagement.model.EmployeeRepository
import com.inconus.mealmanagement.model.RecordSummary
import com.inconus.mealmanagement.model.RecordSummaryRepository
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class CalculateViewModel(
    private var employeeRepository: EmployeeRepository,
    private var recordSummaryRepository: RecordSummaryRepository
) : ViewModel() {

    // 날짜를 Long 형태로 변환
    private val currentDate: LocalDateTime = LocalDateTime.now()
    private val formatter = DateTimeFormatter.ofPattern("yyyyMMdd")
    private val formattedDate = currentDate.format(formatter).toLong()

    // 해당 날짜에 스캔된 직원 리스트
    private val _records = MutableLiveData<List<EmployeeRecord>>()
    val records: LiveData<List<EmployeeRecord>> = _records



    init {
        loadRecords()
    }

    private fun loadRecords() {
        viewModelScope.launch {
            val recordList = employeeRepository.getRecordsByDate(formattedDate)
            _records.postValue(recordList)
        }
    }

    fun updateSummary() = viewModelScope.launch {
        val summaries = employeeRepository.getRecordSummary()
        recordSummaryRepository.updateSummary(summaries)
    }

    val summaries: LiveData<List<RecordSummary>> by lazy {
        recordSummaryRepository.getAllSummaries()
    }
}

class CalculateViewModelFactory(
    private val employeeRepository: EmployeeRepository,
    private val recordSummaryRepository: RecordSummaryRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CalculateViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CalculateViewModel(employeeRepository, recordSummaryRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}



// Select COUNT(*) From employee_records Where dateScanned BETWEEN 2024052100 and 2024052124
