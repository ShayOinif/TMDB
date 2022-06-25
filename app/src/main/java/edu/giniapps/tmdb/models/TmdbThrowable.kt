package edu.giniapps.tmdb.models

sealed class TmdbThrowable : Throwable()

object TmdbNetworkThrowable : TmdbThrowable()

object TmdbServerThrowable : TmdbThrowable()

object TmdbInternalThrowable : TmdbThrowable()

object TmdbNotInitialized : TmdbThrowable()

object TmdbBrokenThrowable : TmdbThrowable()