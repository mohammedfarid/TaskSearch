package com.swvl.tasksearch.model.movieresponse

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class MovieListResponse(

    @field:SerializedName("movies")
    val movies: List<MoviesItem?>? = null
) : Parcelable {
	constructor(parcel: Parcel) : this(parcel.createTypedArrayList(MoviesItem)) {
	}

	override fun writeToParcel(parcel: Parcel, flags: Int) {
		parcel.writeTypedList(movies)
	}

	override fun describeContents(): Int {
		return 0
	}

	companion object CREATOR : Parcelable.Creator<MovieListResponse> {
		override fun createFromParcel(parcel: Parcel): MovieListResponse {
			return MovieListResponse(parcel)
		}

		override fun newArray(size: Int): Array<MovieListResponse?> {
			return arrayOfNulls(size)
		}
	}
}

data class MoviesItem(

    @field:SerializedName("cast")
    val cast: List<String?>? = null,

    @field:SerializedName("year")
    val year: Int? = null,

    @field:SerializedName("genres")
    val genres: List<String?>? = null,

    @field:SerializedName("rating")
    val rating: Int? = null,

    @field:SerializedName("title")
    val title: String? = null
):Parcelable {
	constructor(parcel: Parcel) : this(
		parcel.createStringArrayList(),
		parcel.readValue(Int::class.java.classLoader) as? Int,
		parcel.createStringArrayList(),
		parcel.readValue(Int::class.java.classLoader) as? Int,
		parcel.readString()
	) {
	}

	override fun writeToParcel(parcel: Parcel, flags: Int) {
		parcel.writeStringList(cast)
		parcel.writeValue(year)
		parcel.writeStringList(genres)
		parcel.writeValue(rating)
		parcel.writeString(title)
	}

	override fun describeContents(): Int {
		return 0
	}

	companion object CREATOR : Parcelable.Creator<MoviesItem> {
		override fun createFromParcel(parcel: Parcel): MoviesItem {
			return MoviesItem(parcel)
		}

		override fun newArray(size: Int): Array<MoviesItem?> {
			return arrayOfNulls(size)
		}
	}
}
