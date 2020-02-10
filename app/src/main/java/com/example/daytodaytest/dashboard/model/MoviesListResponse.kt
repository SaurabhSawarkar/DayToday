package com.example.daytodaytest.dashboard.model

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class MoviesResponse(
    /*var object_ids: String? = null,

    var comments: String? = null,*/

    var iso_3166_1: String? = null,

    var description: String? = null,

    var runtime: String? = null,

    var average_rating: String? = null,

    var total_pages: String? = null,

    var sort_by: String? = null,

    var iso_639_1: String? = null,

    var created_by: Created_by? = null,

    var poster_path: String? = null,

    var total_results: String? = null,

    var backdrop_path: String? = null,

    var revenue: String? = null,

    var public: String? = null,

    var name: String? = null,

    var id: String? = null,

    var page: String? = null,

    var results: List<Results>? = null
)

open class Results(
    @PrimaryKey
    var id: String? = null,

    var overview: String? = null,

    var original_language: String? = null,

    var original_title: String? = null,

    var video: String? = null,

    var title: String? = null,

    var genre_ids: RealmList<String>? = null,

    var poster_path: String? = null,

    var backdrop_path: String? = null,

    var media_type: String? = null,

    var release_date: String? = null,

    var popularity: String? = null,

    var vote_average: String? = null,

    var adult: String? = null,

    var vote_count: String? = null
) : RealmObject()

open class Created_by(
    var gravatar_hash: String? = null,

    var name: String? = null,

    var id: String? = null,

    var username: String? = null
)