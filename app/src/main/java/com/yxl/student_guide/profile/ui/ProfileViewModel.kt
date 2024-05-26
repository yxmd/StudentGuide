package com.yxl.student_guide.profile.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.yxl.student_guide.core.db.score.ScoreDBO
import com.yxl.student_guide.profile.data.ProfileRepository
import com.yxl.student_guide.profile.data.Score
import com.yxl.student_guide.profile.data.Subject
import com.yxl.student_guide.utils.toScoreDBO
import com.yxl.student_guide.utils.toSubject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val profileRepository: ProfileRepository
) : ViewModel() {

    val scores: LiveData<List<ScoreDBO>> = profileRepository.scores.flowOn(Dispatchers.IO)
        .asLiveData(context = viewModelScope.coroutineContext)
    val subjects: LiveData<List<Subject>> = profileRepository.subjects.flowOn(Dispatchers.IO).map {
        it.map { sub -> sub.toSubject() } }.asLiveData(context = viewModelScope.coroutineContext)
    val totalScore: LiveData<Int> = profileRepository.totalScore.flowOn(Dispatchers.IO)
        .asLiveData(context = viewModelScope.coroutineContext)

    fun addScoreToDb(scoreName: String, scoreValue: Int) = viewModelScope.launch(Dispatchers.IO) {
        profileRepository.addScore(scoreName, scoreValue)
    }

    fun deleteScore(score: Score)= viewModelScope.launch(Dispatchers.IO){
        profileRepository.deleteScore(score.toScoreDBO())
    }

}
