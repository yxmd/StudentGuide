package com.yxl.student_guide.core.data

import retrofit2.Response
import retrofit2.http.GET

interface Api {

    @GET("/universities")
    suspend fun getUniversities(): Response<List<Institute>>

    @GET("/colleges")
    suspend fun getColleges(): Response<List<Institute>>

    companion object{
        const val BASE_URL = "my_api"
    }
}