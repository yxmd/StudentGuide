package com.yxl.student_guide.core.db.institute

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "institutes")
data class InstituteDBO(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name:String,
    val description: String?,
    val city: String,
    val img: String?,
    val logo: String?,
    val shortName: String,
    val type: String,
)
