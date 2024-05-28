package com.inconus.mealmanagement.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [Employee::class, Summary::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun employeeDao(): EmployeeDao
    abstract fun summaryDao(): SummaryDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                )
                    .addMigrations(MIGRATION_1_2)  // 필요한 마이그레이션을 추가합니다.
                    .build()
                INSTANCE = instance
                instance
            }
        }

        // 버전 1에서 2로의 마이그레이션
        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                // 1. 임시 테이블 생성
                db.execSQL("""
                    CREATE TABLE IF NOT EXISTS employee_records_temp (
                        id INTEGER PRIMARY KEY NOT NULL,
                        name TEXT NOT NULL,
                        number INTEGER NOT NULL,
                        dateScanned TEXT NOT NULL
                    )
                """.trimIndent())

                // 2. 기존 테이블의 데이터 복사
                db.execSQL("""
                    INSERT INTO employee_records_temp (id, name, number, dateScanned)
                    SELECT id, name, employeeCode, dateScanned
                    FROM employee_records
                """.trimIndent())

                // 3. 기존 테이블 삭제
                db.execSQL("DROP TABLE IF EXISTS employee_records")

                // 4. 임시 테이블을 기존 테이블로 변경
                db.execSQL("ALTER TABLE employee_records_temp RENAME TO employee_records")
            }
        }
    }
}

