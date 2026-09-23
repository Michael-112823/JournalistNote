package com.example.journalistnote.Controller

import android.content.Context
import com.example.journalistnote.Model.AppDatabase
import com.example.journalistnote.Model.Interview
import kotlinx.coroutines.flow.Flow

class InterviewController(context: Context) {

    private val interviewDao = AppDatabase.getDatabase(context).interviewDao()

    suspend fun createInterview(
        caseId: Long,
        interviewee: String,
        date: String,
        notes: String,
        findings: String
    ): Long {
        val interview = Interview(
            caseId = caseId,
            interviewee = interviewee,
            date = date,
            notes = notes,
            findings = findings
        )
        return interviewDao.insert(interview)
    }

    fun getInterviewsByCaseId(caseId: Long): Flow<List<Interview>> =
        interviewDao.getInterviewsByCaseId(caseId)

}