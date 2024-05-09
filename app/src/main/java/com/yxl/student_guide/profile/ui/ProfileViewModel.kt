package com.yxl.student_guide.profile.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.yxl.student_guide.core.db.score.ScoreDBO
import com.yxl.student_guide.profile.data.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val profileRepository: ProfileRepository
) : ViewModel() {

    val scores: LiveData<List<ScoreDBO>> = profileRepository.scores.flowOn(Dispatchers.IO)
        .asLiveData(context = viewModelScope.coroutineContext)

    fun addScoreToDb(scoreName: String, scoreValue: Int) = viewModelScope.launch(Dispatchers.IO) {
        profileRepository.addScore(scoreName, scoreValue)
    }

}
