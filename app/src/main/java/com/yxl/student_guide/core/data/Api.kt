package com.yxl.student_guide.core.data

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface Api {

    @GET("/universities")
    suspend fun getUniversities(): Response<List<Institute>>

    @GET("/colleges")
    suspend fun getColleges(): Response<List<Institute>>

    @GET("/universities/{id}")
    suspend fun getUniversityInfo(@Path("id") id: Int): Response<Institute>

    @GET("/colleges/{id}")
    suspend fun getCollegeInfo(@Path("id") id: Int): Response<Institute>

    companion object {
        const val BASE_URL = "my_api"
    }
}