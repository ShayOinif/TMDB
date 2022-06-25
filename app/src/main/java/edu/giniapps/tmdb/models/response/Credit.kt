package edu.giniapps.tmdb.models.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
class Credit(
    @SerializedName("character")
    val credit: String,
    val name: String,
    @SerializedName("profile_path")
    val profilePath: String?,
    val id: Int
) : Parcelable