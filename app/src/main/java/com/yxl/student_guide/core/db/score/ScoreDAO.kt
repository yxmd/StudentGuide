package com.yxl.student_guide.core.db.score

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface ScoreDAO {

    @Query("SELECT * FROM scores")
    fun getAll(): Flow<List<ScoreDBO>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(contacts: ScoreDBO)

}
