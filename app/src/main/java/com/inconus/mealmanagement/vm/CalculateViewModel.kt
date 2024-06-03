package com.inconus.mealmanagement.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.map
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import com.inconus.mealmanagement.model.EmployeeRepository
import com.inconus.mealmanagement.model.Summary
import com.inconus.mealmanagement.model.SummaryRepository
import kotlinx.coroutines.launch
import java.util.Calendar

class CalculateViewModel(
    private var employeeRepository: EmployeeRepository,
    private var summaryRepository: SummaryRepository
) : ViewModel() {
    // 데이터 받아와야 하는 월
    private val _selectedCalendar = MutableLiveData(Calendar.getInstance())
    val selectedCalendar: LiveData<Calendar> = _selectedCalendar
    fun updateSelectedCalendar(calendar: Calendar) {
        _selectedCalendar.value = calendar
    }

    // 주어진 캘린더에서 년과 월을 추출하여 Long 형태로 반환
    private fun getMonthLongFromCalendar(calendar: Calendar): Long {
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH) + 1 // Calendar.MONTH는 0부터 시작
        return (year.toString() + month.toString().padStart(2, '0')).toLong()
    }


    // 선택된 캘린더의 월을 기준으로 요약 정보를 불러옴
    // 캘린더 값이 변경될 때 마다 요약 정보 업데이트
    val summaries: LiveData<List<Summary>> = _selectedCalendar.switchMap { calendar ->
        val monthLong = getMonthLongFromCalendar(calendar)
        summaryRepository.getSummariesByMonth(monthLong)
    }

    // 해당 월의 총 인원 계산
    val totalCount: LiveData<Int> = summaries.map{ summaries ->
        summaries.sumOf { it.count }
    }

    // 해당 월의 총 금액 계산
    val totalAmount: LiveData<Int> = summaries.map { summaries ->
        summaries.sumOf { it.count * it.price }
    }

    // 이전의 기록과 달라진 요약 정보가 있다면 DB에 업데이트
    fun updateSummary() = viewModelScope.launch {
        val summaries = employeeRepository.getRecordSummary()
        summaryRepository.updateSummary(summaries)
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