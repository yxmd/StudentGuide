package com.yxl.student_guide.main.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.yxl.student_guide.core.data.Institute
import com.yxl.student_guide.main.data.MainRepository
import com.yxl.student_guide.utils.toDBO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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
    val rbScoreState = MutableLiveData<Boolean>(false)
    val score = mainRepository.totalScore.flowOn(Dispatchers.IO)
        .asLiveData(context = viewModelScope.coroutineContext)

    init {
        getData()
    }

    fun getData() = viewModelScope.launch {
        when (tab.value) {
            0 -> {
                getUniversities()
            }

            1 -> {
                getColleges()
            }
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

    fun addInstituteToDb(institute: Institute) {
        viewModelScope.launch(Dispatchers.IO) {
            mainRepository.insertInstitute(institute.toDBO())
        }
    }

    fun searchInstitutes(query: String) {
        isLoading.postValue(true)
        viewModelScope.launch {
            val result = _data.value?.filter { institute ->
                institute.name.contains(query, ignoreCase = true) || institute.shortName.contains(
                    query,
                    ignoreCase = true
                )
            }
            _data.postValue(result ?: emptyList())
        }
        isLoading.postValue(false)
    }

    fun filterByName(arg: String) {
        isLoading.postValue(true)
        viewModelScope.launch {
            val result = _data.value?.filter { institute ->
                institute.address == arg
            }
            _data.postValue(result ?: emptyList())
        }
        isLoading.postValue(false)
    }

    fun filterByScore(score: Int) {
        isLoading.postValue(true)
        viewModelScope.launch(Dispatchers.IO) {
            val currentData = _data.value.orEmpty()
            val filteredList = currentData.map { institute ->
                institute.copy(specialities = institute.specialities?.filter { speciality ->
                    val budget = speciality.budget?.toIntOrNull()
                    val paid = speciality.paid?.toIntOrNull()
                    (budget != null && budget <= score) || (paid != null && paid <= score)
                })
            }.filter { it.specialities?.isNotEmpty() == true }

            withContext(Dispatchers.Main) {
                _data.value = filteredList
                isLoading.value = false
            }
        }
    }



}