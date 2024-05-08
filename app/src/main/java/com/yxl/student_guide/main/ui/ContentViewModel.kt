package com.yxl.student_guide.main.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yxl.student_guide.core.data.Institute
import com.yxl.student_guide.main.data.MainRepository
import com.yxl.student_guide.utils.toDBO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContentViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : ViewModel() {

    private val _data = MutableLiveData<List<Institute>>()
    val data: LiveData<List<Institute>> = _data

    //create data class to manage in which case was an error
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
            _data.postValue(mainRepository.getUniversities())
        } catch (e: Exception) {
            showErrorButton.postValue(true)
        }

    }

    private fun getColleges() = viewModelScope.launch {
        try {
            _data.postValue(mainRepository.getColleges())
        } catch (e: Exception) {
            showErrorButton.postValue(true)
        }
    }

    fun addInstituteToDb(institute: Institute){
        viewModelScope.launch(Dispatchers.IO) {
            mainRepository.insertInstitute(institute.toDBO())
        }
    }

}