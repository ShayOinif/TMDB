package edu.giniapps.tmdb.models.response

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

const val POSTER_BASE_URL = "https://image.tmdb.org/t/p/original/"
const val RATING_EVALUATION_DENOMINATOR = 2.0

interface TmdbItem : Parcelable {

    val id: Long
    val overview: String
    val releaseDate: String
    val posterPath: String?
    val backdropPath: String?
    val popularity: Double
    val name: String
    val voteAverage: Float
}

@Parcelize
@Entity(tableName = "MOVIES")
data class Movie(
    @PrimaryKey
    override val id: Long,

    override val overview: String,
    @SerializedName("release_date")
    @ColumnInfo(name = "release_date")
    override val releaseDate: String,
    @SerializedName("poster_path")
    @ColumnInfo(name = "poster_path")
    private val _posterPath: String?,
    @SerializedName("backdrop_path")
    @ColumnInfo(name = "backdrop_path")
    private val _backdropPath: String?,
    @SerializedName("title")
    override val name: String,
    @SerializedName("vote_average")
    @ColumnInfo(name = "vote_average")
    private val _voteAverage: Float,
    override val popularity: Double,
    var category: Int = NO_CATEGORY,
    var favorite: Boolean = false

) : TmdbItem {
    @IgnoredOnParcel
    @Ignore
    @SerializedName("genre_ids")
    val genreIds: List<Long> = emptyList()

    @IgnoredOnParcel
    override val posterPath: String
        get() = POSTER_BASE_URL + _posterPath

    @IgnoredOnParcel
    override val backdropPath: String
        get() = POSTER_BASE_URL + _backdropPath

    @IgnoredOnParcel
     override val voteAverage: Float
         get() = (_voteAverage / RATING_EVALUATION_DENOMINATOR).toFloat()

    companion object {
        const val NO_CATEGORY = 0
        const val POPULAR = 1
        const val NOW_PLAYING = 2
    }
}


@Parcelize
data class TVShow(
    override val id: Long,
    override val overview: String,
    @SerializedName("first_air_date")
    override val releaseDate: String,
    @SerializedName("poster_path")
    override val posterPath: String?,
    @SerializedName("backdrop_path")
    override val backdropPath: String?,
    override val name: String,
    @SerializedName("vote_average")
    override val voteAverage: Float,
    override val popularity: Double,
) : TmdbItem {
    @IgnoredOnParcel
    @SerializedName("genre_ids")
    val genreIds: List<Long> = emptyList()
}

