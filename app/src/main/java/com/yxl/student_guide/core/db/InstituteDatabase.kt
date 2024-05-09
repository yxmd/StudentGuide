package com.yxl.student_guide.core.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.yxl.student_guide.core.db.institute.InstituteDBO
import com.yxl.student_guide.core.db.institute.InstituteDao
import com.yxl.student_guide.core.db.score.ScoreDBO
import com.yxl.student_guide.core.db.score.ScoreDAO

@Database(entities = [InstituteDBO::class, ScoreDBO::class], version = 1, exportSchema = true)
abstract class InstituteDatabase : RoomDatabase() {

    abstract fun InstituteDao(): InstituteDao
    abstract fun ScoreDao(): ScoreDAO

    companion object{
        const val DATABASE_NAME = "institute_db"
    }
}