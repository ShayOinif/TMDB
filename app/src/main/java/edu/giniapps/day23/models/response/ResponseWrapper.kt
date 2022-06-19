package edu.giniapps.day23.models.response

import com.google.gson.annotations.SerializedName


class ItemWrapper<T : TmdbItem>(
    @SerializedName("results")
    val items: List<T>,
    /*@SerializedName("page") val page: Int,
    @SerializedName("total_pages") val pages: Int*/
)

class VideoWrapper

class CreditWrapper