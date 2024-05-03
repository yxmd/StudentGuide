package com.yxl.student_guide.core.db.score

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "scores")
data class ScoreDBO(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val value: Int,
)
