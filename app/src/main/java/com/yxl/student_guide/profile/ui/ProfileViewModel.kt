package com.yxl.student_guide.profile.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yxl.student_guide.profile.data.Score
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(

): ViewModel() {

    val scores = MutableLiveData<List<Score>>()
    private val scoresList = mutableListOf<Score>()
    private var scoreId = 1
    fun addScoreToDb(scoreName: String, scoreValue: Int) = viewModelScope.launch {
        scoresList.add(Score(scoreId, scoreName, scoreValue))
        scores.postValue(scoresList)
        scoreId++
    }

}