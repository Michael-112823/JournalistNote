package com.example.journalistnote.Model

import androidx.room.Entity
import androidx.room.PrimaryKey

// enum para que el compilador no asigne un estado que no exista
enum class CaseStatus {
    OPEN,
    IN_PROGRESS,
    CLOSED
}
// se crea la tabla de los casos para usar el Room
@Entity(tableName = "cases")
data class Case(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val description: String,
    val date: String,
    val status: CaseStatus,
    val conclusion: String = "")