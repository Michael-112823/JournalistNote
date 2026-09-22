package com.example.journalistnote.Model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
// este sirve para establecer la relacion entre las tablas case e interview
@Entity(
    tableName = "interviews",
    foreignKeys = [
        ForeignKey(
            entity = Case::class,
            parentColumns = ["id"],
            childColumns = ["caseId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["caseId"])]
)
// se crea la tabla de interview para usar el Room
data class Interview(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val caseId: Long,
    val interviewee: String,
    val date: String,
    val notes: String,
    val findings: String
)