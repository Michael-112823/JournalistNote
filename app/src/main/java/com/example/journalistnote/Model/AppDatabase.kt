package com.example.journalistnote.Model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

// IA
@Database(
    entities = [Case::class, Interview::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun caseDao(): CaseDao
    abstract fun interviewDao(): InterviewDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "journalist_note_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}