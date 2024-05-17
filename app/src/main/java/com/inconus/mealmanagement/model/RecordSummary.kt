package com.inconus.mealmanagement.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "record_summaries")
data class RecordSummary(
    @PrimaryKey val date: Long, // 해당 날짜 (예: 20230401)
    val count: Int // 그 날 인식된 총 인원 수
)

