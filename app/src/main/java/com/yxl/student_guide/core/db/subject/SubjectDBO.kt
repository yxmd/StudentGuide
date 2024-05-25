package com.yxl.student_guide.core.db.subject

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "subjects")
data class SubjectDBO(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
)
