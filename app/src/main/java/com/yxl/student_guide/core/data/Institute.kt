package com.yxl.student_guide.core.data

import com.google.gson.annotations.SerializedName

data class Institute(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name:String,
    @SerializedName("description")
    val description: String?,
    @SerializedName("address")
    val address: String?,
    @SerializedName("img")
    val img: String?,
    @SerializedName("logo")
    val logo: String?,
    @SerializedName("specialities")
    val specialities: List<Specialty>? = null,
    @SerializedName("shortName")
    val shortName: String,
    @SerializedName("type")
    val type: String,
    @SerializedName("website")
    val website: String?,
)

data class Specialty(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name:String,
    @SerializedName("budget")
    val budget:String,
    @SerializedName("paid")
    val paid:String,
    @SerializedName("universityId")
    val universityId: Int,
    @SerializedName("collegeId")
    val collegeId: String,
)