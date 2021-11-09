package vamsee.application.studytube.Models.Video

import android.os.Parcel
import android.os.Parcelable

data class ThumbnailUrl(
    val url: String? = ""
): Parcelable {
    constructor(parcel: Parcel) : this(parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(url)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ThumbnailUrl> {
        override fun createFromParcel(parcel: Parcel): ThumbnailUrl {
            return ThumbnailUrl(parcel)
        }

        override fun newArray(size: Int): Array<ThumbnailUrl?> {
            return arrayOfNulls(size)
        }
    }
}