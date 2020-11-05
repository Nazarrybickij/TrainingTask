package com.nazarrybickij.trainingtask.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class UserDataEntity(
    val avatar: String,
    val email: String,
    @SerializedName("first_name")
    val firstName: String,
    @PrimaryKey
    val id: Int,
    @SerializedName("last_name")
    val lastName: String
)