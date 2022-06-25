package edu.giniapps.tmdb.models.response

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "GENRES")
data class Genre(
    @PrimaryKey
    @SerializedName("id")
    @ColumnInfo(name = "genre_id")
    val genreId: Long,
    val name: String,
)