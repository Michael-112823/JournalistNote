package com.example.journalistnote.Controller

import android.content.Context
import com.example.journalistnote.Model.AppDatabase
import com.example.journalistnote.Model.Case
import com.example.journalistnote.Model.CaseStatus
import kotlinx.coroutines.flow.Flow

class CaseController(context: Context) {
    private val caseDao = AppDatabase.getDatabase(context).caseDao()

    suspend fun createCase(
        title: String,
        description: String,
        date: String,
        status: CaseStatus,
        conclusion: String = ""
    ): Long {
        val case = Case(
            title = title,
            description = description,
            date = date,
            status = status,
            conclusion = conclusion
        )
        return caseDao.insert(case)
    }

    fun getCases(): Flow<List<Case>> = caseDao.getAllCases()

    fun getCaseById(id: Long): Flow<Case?> = caseDao.getCaseById(id)

    suspend fun updateCase(case: Case) = caseDao.update(case)

    suspend fun deleteCase(case: Case) = caseDao.delete(case)

    fun searchCases(query: String): Flow<List<Case>> = caseDao.searchCases(query)

    fun getTotalCases(): Flow<Int> = caseDao.getTotalCases()

    fun getCasesCountByStatus(status: CaseStatus): Flow<Int> =
        caseDao.getCasesCountByStatus(status)
}