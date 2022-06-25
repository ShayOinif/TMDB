package edu.giniapps.tmdb.models

import androidx.annotation.StringRes
import edu.giniapps.tmdb.ui.home.adapters.TMDBItemAdapter

data class MoviesCategoryItem(
    @StringRes
    val categoryNameStringRes: Int,
    val adapter: TMDBItemAdapter)