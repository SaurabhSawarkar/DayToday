package com.example.daytodaytest.dashboard.model

import android.os.Parcelable
import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import android.os.Parcel
import io.realm.RealmObject.asChangesetObservable
import io.realm.RealmObject.asFlowable
import io.realm.RealmObject.asChangesetObservable
import io.realm.RealmObject.asFlowable


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
) : RealmObject(), Parcelable {

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeString(id)
        dest?.writeString(overview)
        dest?.writeString(original_language)
        dest?.writeString(original_title)
        dest?.writeString(video)
        dest?.writeString(title)
        dest?.writeString(poster_path)
        dest?.writeString(backdrop_path)
        dest?.writeString(media_type)
        dest?.writeString(release_date)
        dest?.writeString(popularity)
        dest?.writeString(vote_average)
        dest?.writeString(adult)
        dest?.writeString(vote_count)
    }


    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        RealmList(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    companion object CREATOR : Parcelable.Creator<Results> {
        override fun createFromParcel(parcel: Parcel): Results {
            return Results(parcel)
        }

        override fun newArray(size: Int): Array<Results?> {
            return arrayOfNulls(size)
        }
    }

    override fun describeContents(): Int {
        return 0
    }
}

open class Created_by(
    var gravatar_hash: String? = null,

    var name: String? = null,

    var id: String? = null,

    var username: String? = null
)