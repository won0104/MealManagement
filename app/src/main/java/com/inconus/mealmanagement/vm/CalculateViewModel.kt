package com.inconus.mealmanagement.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.map
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import com.inconus.mealmanagement.model.Employee
import com.inconus.mealmanagement.model.EmployeeRepository
import com.inconus.mealmanagement.model.Summary
import com.inconus.mealmanagement.model.SummaryRepository
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Calendar

class CalculateViewModel(
    private var employeeRepository: EmployeeRepository,
    private var summaryRepository: SummaryRepository
) : ViewModel() {

    private val currentDate: LocalDateTime = LocalDateTime.now()
    private val formatter = DateTimeFormatter.ofPattern("yyyyMMdd")
    private val formattedDate = currentDate.format(formatter).toLong()

    private val _records = MutableLiveData<List<Employee>>()
    val records: LiveData<List<Employee>> = _records

    private val _selectedCalendar = MutableLiveData(Calendar.getInstance())
    val selectedCalendar: LiveData<Calendar> = _selectedCalendar


    val summaries: LiveData<List<Summary>> = _selectedCalendar.switchMap { calendar ->
        val monthLong = getMonthLongFromCalendar(calendar)
        summaryRepository.getSummariesByMonth(monthLong)
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
        summaryRepository.updateSummary(summaries)
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
    private val summaryRepository: SummaryRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CalculateViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CalculateViewModel(employeeRepository, summaryRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}



// Select COUNT(*) From employee_records Where dateScanned BETWEEN 2024052100 and 2024052124
