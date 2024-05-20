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
    val isLoading = MutableLiveData(false)
    val tab = MutableLiveData(0)
    val spinnerCitiesState = MutableLiveData<Int?>(null)
    val spinnerProgramsState = MutableLiveData<Int?>(null)

    init {
        getData()
    }

    fun getData() = viewModelScope.launch {
        when (tab.value) {
            0 -> { getUniversities() }
            1 -> { getColleges() }
        }
    }

    private fun getUniversities() = viewModelScope.launch {
        isLoading.postValue(true)
        try {
            _data.postValue(mainRepository.getUniversities())
        } catch (e: Exception) {
            showErrorButton.postValue(true)
        }
        isLoading.postValue(false)
    }

    private fun getColleges() = viewModelScope.launch {
        isLoading.postValue(true)
        try {
            _data.postValue(mainRepository.getColleges())
        } catch (e: Exception) {
            showErrorButton.postValue(true)
        }
        isLoading.postValue(false)
    }

    fun addInstituteToDb(institute: Institute){
        viewModelScope.launch(Dispatchers.IO) {
            mainRepository.insertInstitute(institute.toDBO())
        }
    }

    fun searchInstitutes(query: String) {
        isLoading.postValue(true)
        viewModelScope.launch {
            val result = _data.value?.filter { institute ->
                institute.name.contains(query, ignoreCase = true)
            }
            _data.postValue(result ?: emptyList())
        }
        isLoading.postValue(false)
    }

    fun filterInstitutes(arg: String){
        isLoading.postValue(true)
        viewModelScope.launch {
            val result = _data.value?.filter { institute ->
                institute.city == arg
            }
            _data.postValue(result ?: emptyList())
        }
        isLoading.postValue(false)
    }

}