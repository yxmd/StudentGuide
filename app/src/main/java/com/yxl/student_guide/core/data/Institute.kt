package com.yxl.student_guide.core.data

import com.google.gson.annotations.SerializedName

data class Institute(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name:String,
    @SerializedName("description")
    val description: String,
    @SerializedName("img")
    val img: String?,
    @SerializedName("logo")
    val logo: String?,
)