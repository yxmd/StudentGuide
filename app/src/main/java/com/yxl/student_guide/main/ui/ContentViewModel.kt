package com.yxl.student_guide.main.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yxl.student_guide.core.data.Institute
import com.yxl.student_guide.core.data.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContentViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _data = MutableLiveData<List<Institute>>()
    val data: LiveData<List<Institute>> = _data

    //create data class to manage in which requst was an error
    val showErrorButton = MutableLiveData(false)

    init {
        getData(0)
    }

    fun getData(index: Int) = viewModelScope.launch {
        when (index) {
            0 -> { getUniversities() }
            1 -> { getColleges() }
        }
    }

    private fun getUniversities() = viewModelScope.launch {
        try {
            _data.postValue(repository.getUniversities())
        } catch (e: Exception) {
            showErrorButton.postValue(true)
        }

    }

    private fun getColleges() = viewModelScope.launch {
        try {
            _data.postValue(repository.getColleges())
        } catch (e: Exception) {
            showErrorButton.postValue(true)
        }
    }

    private fun addInstituteToDb(){

    }

}