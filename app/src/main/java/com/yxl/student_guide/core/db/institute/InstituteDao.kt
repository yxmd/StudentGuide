package com.yxl.student_guide.core.db.institute

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface InstituteDao {

    @Query("SELECT * FROM institutes")
    fun getInstitutes(): Flow<List<InstituteDBO>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addAll(contacts: List<InstituteDBO>)

}
