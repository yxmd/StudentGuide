package com.yxl.student_guide.main.data

import com.yxl.student_guide.core.data.Api
import com.yxl.student_guide.core.data.Institute
import com.yxl.student_guide.core.db.institute.InstituteDBO
import com.yxl.student_guide.core.db.institute.InstituteDao
import com.yxl.student_guide.core.db.score.ScoreDAO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainRepository @Inject constructor(
    private val api: Api,
    private val instituteDao: InstituteDao,
    private val scoreDAO: ScoreDAO,
) {

    val totalScore: Flow<Int> = scoreDAO.getTotalScore()
    suspend fun getUniversities(): List<Institute> {
        return withContext(Dispatchers.IO) {
            val resp = api.getUniversities()
            resp.body() ?: emptyList()
        }
    }

    suspend fun getColleges(): List<Institute> {
        return withContext(Dispatchers.IO) {
            val resp = api.getColleges()
            resp.body() ?: emptyList()
        }
    }

    suspend fun insertInstitute(model: InstituteDBO) {
        withContext(Dispatchers.IO) {
            instituteDao.insert(model)
        }
    }

}