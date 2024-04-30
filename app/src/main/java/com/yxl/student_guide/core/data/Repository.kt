package com.yxl.student_guide.core.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Repository @Inject constructor(private val api: Api) {

    suspend fun getUniversities(): List<Institute> {
        return withContext(Dispatchers.IO){
            val resp = api.getUniversities()
            resp.body()!!
        }
    }

    suspend fun getColleges(): List<Institute> {
        return withContext(Dispatchers.IO){
            val resp = api.getColleges()
            resp.body()!!
        }
    }

}