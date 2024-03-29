package com.piashcse.experiment.mvvm_hilt.data.datasource.local.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.piashcse.experiment.mvvm_hilt.utils.AppConstants


@Entity
data class User(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "name") val name: String? = AppConstants.DefaultData.STRING,
    @ColumnInfo(name = "company") val company: String? = AppConstants.DefaultData.STRING
)
