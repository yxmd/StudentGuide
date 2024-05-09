package com.yxl.student_guide.profile.data

import com.yxl.student_guide.core.db.score.ScoreDAO
import com.yxl.student_guide.core.db.score.ScoreDBO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProfileRepository @Inject constructor(
    private val scoreDAO: ScoreDAO
) {

    val scores: Flow<List<ScoreDBO>> = scoreDAO.getAll()

    suspend fun addScore(scoreName: String, scoreValue: Int){
        withContext(Dispatchers.IO){
            scoreDAO.insert(ScoreDBO(name = scoreName, value = scoreValue))
        }
    }

}
