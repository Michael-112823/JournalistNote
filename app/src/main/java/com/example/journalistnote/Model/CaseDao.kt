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
interface CaseDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(case: Case): Long

    @Query("SELECT * FROM cases ORDER BY date DESC")
    fun getAllCases(): Flow<List<Case>>

    @Query("SELECT * FROM cases WHERE id = :id")
    fun getCaseById(id: Long): Flow<Case?>

    @Update
    suspend fun update(case: Case)

    @Delete
    suspend fun delete(case: Case)

    @Query("SELECT * FROM cases WHERE title LIKE '%' || :query || '%' ORDER BY date DESC")
    fun searchCases(query: String): Flow<List<Case>>

    @Query("SELECT COUNT(*) FROM cases")
    fun getTotalCases(): Flow<Int>

    @Query("SELECT COUNT(*) FROM cases WHERE status = :status")
    fun getCasesCountByStatus(status: CaseStatus): Flow<Int>
}