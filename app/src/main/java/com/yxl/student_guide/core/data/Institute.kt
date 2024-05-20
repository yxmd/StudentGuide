package com.yxl.student_guide.core.data

import com.google.gson.annotations.SerializedName

data class Institute(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name:String,
    @SerializedName("description")
    val description: String,
    @SerializedName("city")
    val city: String,
    @SerializedName("img")
    val img: String?,
    @SerializedName("logo")
    val logo: String?,
    @SerializedName("faculties")
    val faculties: List<Faculty>? = null,
    @SerializedName("specialties")
    val specialties: List<Specialty>? = null,
)

data class Faculty(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name:String,
    @SerializedName("universityId")
    val universityId: Int,
)

data class Specialty(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name:String,
    @SerializedName("facultyId")
    val facultyId: Int,
    @SerializedName("collegeId")
    val collegeId: String,
)