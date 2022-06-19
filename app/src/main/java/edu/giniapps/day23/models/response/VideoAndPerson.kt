package edu.giniapps.day23.models.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Person(
    @SerializedName("birthday")
    val birthday: String?,

    @SerializedName("deathday")
    val deathday: String?,

    val id: Int,

    @SerializedName("also_known_as")
    val alsoKnownAs: List<String>?,

    val biography: String,

    @SerializedName("place_of_birth")
    val placeOfBirth: String,

    ) : Parcelable

@Parcelize
data class Video(
    val id: String,
    val name: String,
    val site: String,

    @SerializedName("key")
    val videoId: String,

    val type: String

) : Parcelable /*{
    companion object {
        private const val SITE_YOUTUBE = "Youtube"
        private const val YOUTUBE_VIDEO_URL = "https://www.youtube.com/watch?v="
        private const val YOUTUBE_THUMBNAIL_URL = "https://www.img.youtube.com/vi"

        fun getVideoUrl(video: Video) =
            if (video.site.equals(SITE_YOUTUBE, ignoreCase = true))
                YOUTUBE_VIDEO_URL + video.videoId
            else ""

        fun getVideoThumbnail(video: Video) =
            if (video.site.equals(SITE_YOUTUBE, ignoreCase = true))
                YOUTUBE_THUMBNAIL_URL + video.videoId + ".jpg"
            else ""
    }
}*/
