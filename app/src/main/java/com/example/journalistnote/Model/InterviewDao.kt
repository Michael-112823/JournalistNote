package com.example.journalistnote.Model

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
// implementacion IA
@Dao
interface InterviewDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(interview: Interview): Long

    @Query("SELECT * FROM interviews WHERE caseId = :caseId ORDER BY date DESC")
    fun getInterviewsByCaseId(caseId: Long): Flow<List<Interview>>

    @Query("SELECT * FROM interviews WHERE id = :id")
    fun getInterviewById(id: Long): Flow<Interview?>

    @Update
    suspend fun update(interview: Interview)

    @Delete
    suspend fun delete(interview: Interview)
    // para borrar una entrevista sin borrar un caso
    @Query("DELETE FROM interviews WHERE caseId = :caseId")
    suspend fun deleteInterviewsByCaseId(caseId: Long)
}