package edu.giniapps.tmdb.models.response

import androidx.room.*

@Entity(tableName = "MOVIE_GENRE", primaryKeys = ["id", "genre_id"],
    foreignKeys = [
        ForeignKey(onDelete = ForeignKey.CASCADE, entity = Movie::class, parentColumns = ["id"], childColumns = ["id"]),
        ForeignKey(
            onDelete = ForeignKey.CASCADE,
            entity = Genre::class,
            parentColumns = ["genre_id"],
            childColumns = ["genre_id"]
        )
    ])
data class MovieGenre(
    @ColumnInfo(name = "id")
    val movieId: Long,
    @ColumnInfo(name = "genre_id")
    val genreId: Long
)

data class MovieWithGenres(
    @Embedded
    val movie: Movie,

    @Relation(
        parentColumn = "id",
        entityColumn = "genre_id",
        associateBy = Junction(MovieGenre::class)
    )
    val genres: List<Genre>
)