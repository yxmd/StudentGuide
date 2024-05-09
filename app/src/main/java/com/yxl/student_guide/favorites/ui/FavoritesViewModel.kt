package com.yxl.student_guide.favorites.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.yxl.student_guide.core.db.institute.InstituteDBO
import com.yxl.student_guide.favorites.data.FavoritesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val repository: FavoritesRepository
) : ViewModel() {

    val institutes: LiveData<List<InstituteDBO>> =
        repository.institutes.flowOn(Dispatchers.IO).asLiveData(viewModelScope.coroutineContext)

}