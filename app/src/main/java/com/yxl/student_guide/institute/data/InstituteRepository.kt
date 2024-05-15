package com.yxl.student_guide.institute.data

import com.yxl.student_guide.core.data.Api
import com.yxl.student_guide.core.data.Institute
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class InstituteRepository @Inject constructor(
    private val api: Api
) {

    suspend fun getUniversityInfo(id: Int): Institute {
        return withContext(Dispatchers.IO) {
            api.getUniversityInfo(id).body()!!
        }
    }

    suspend fun getCollegeInfo(id: Int): Institute {
        return withContext(Dispatchers.IO) {
            api.getCollegeInfo(id).body()!!
        }
    }

}