package com.yxl.student_guide.institute.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yxl.student_guide.core.data.Institute
import com.yxl.student_guide.institute.data.InstituteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InstituteViewModel @Inject constructor(
    private val repository: InstituteRepository
): ViewModel() {

    private val _institute = MutableLiveData<Institute>()
    val institute: LiveData<Institute> = _institute

    fun getUniversityInfo(id: Int){
        viewModelScope.launch {
            val university = repository.getUniversityInfo(id)
            _institute.postValue(university)
        }
    }

    fun getCollegeInfo(id: Int){
        viewModelScope.launch {
            val college = repository.getCollegeInfo(id)
            _institute.postValue(college)
        }
    }

}