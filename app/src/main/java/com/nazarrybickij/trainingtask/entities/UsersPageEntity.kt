package com.nazarrybickij.trainingtask.entities

import com.google.gson.annotations.SerializedName

data class UsersPageEntity(
    val ad: AdEntity,
    var data: List<UserDataEntity>,
    val page: Int,
    @SerializedName("per_page")
    val perPage: Int,
    val total: Int,
    @SerializedName("total_pages")
    val totalPages: Int
)