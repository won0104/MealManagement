package com.inconus.mealmanagement.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.map
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import com.inconus.mealmanagement.model.EmployeeRecord
import com.inconus.mealmanagement.model.EmployeeRepository
import com.inconus.mealmanagement.model.RecordSummary
import com.inconus.mealmanagement.model.RecordSummaryRepository
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Calendar

class CalculateViewModel(
    private var employeeRepository: EmployeeRepository,
    private var recordSummaryRepository: RecordSummaryRepository
) : ViewModel() {

    private val currentDate: LocalDateTime = LocalDateTime.now()
    private val formatter = DateTimeFormatter.ofPattern("yyyyMMdd")
    private val formattedDate = currentDate.format(formatter).toLong()

    private val _records = MutableLiveData<List<EmployeeRecord>>()
    val records: LiveData<List<EmployeeRecord>> = _records

    private val _selectedCalendar = MutableLiveData(Calendar.getInstance())
    val selectedCalendar: LiveData<Calendar> = _selectedCalendar


    val summaries: LiveData<List<RecordSummary>> = _selectedCalendar.switchMap{ calendar: Calendar ->
        val monthLong = getMonthLongFromCalendar(calendar)
        val liveData = MutableLiveData<List<RecordSummary>>()
        viewModelScope.launch {
            liveData.value = recordSummaryRepository.getSummariesByMonth(monthLong)
        }
        liveData
    }

    val totalSummaryCount: LiveData<Int> = summaries.map{ summaries ->
        summaries.sumOf { it.count }
    }

    init {
        loadRecords()
        updateSummary()
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

    fun updateSelectedCalendar(calendar: Calendar) {
        _selectedCalendar.value = calendar
    }

    private fun getMonthLongFromCalendar(calendar: Calendar): Long {
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH) + 1 // Calendar.MONTH는 0부터 시작
        return (year.toString() + month.toString().padStart(2, '0')).toLong()
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
