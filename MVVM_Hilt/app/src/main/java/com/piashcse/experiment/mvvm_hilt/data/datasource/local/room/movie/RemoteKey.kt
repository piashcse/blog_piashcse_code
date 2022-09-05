package com.piashcse.experiment.mvvm_hilt.data.datasource.local.room.movie

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remote_keys")
data class RemoteKey(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val next_page: Int?,
    val last_updated: Long
)