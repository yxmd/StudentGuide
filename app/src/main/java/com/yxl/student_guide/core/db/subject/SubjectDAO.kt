package com.yxl.student_guide.core.db.subject

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface SubjectDAO {

    @Query("SELECT * FROM subjects")
    fun getAll(): Flow<List<SubjectDBO>>

}
