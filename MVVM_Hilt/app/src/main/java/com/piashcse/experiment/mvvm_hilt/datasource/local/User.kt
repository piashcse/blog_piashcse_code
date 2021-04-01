package com.piashcse.experiment.mvvm_hilt.datasource.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.piashcse.experiment.mvvm_hilt.constants.AppConstants
import java.util.*


@Entity
data class User(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "name") val name: String? = AppConstants.DefaultData.STRING,
    @ColumnInfo(name = "company") val company: String? = AppConstants.DefaultData.STRING
)
