package vamsee.application.studytube.Models.Video

import android.os.Parcel
import android.os.Parcelable

data class Thumbnails(
    val standard: ThumbnailUrl? = null
) : Parcelable {
    constructor(parcel: Parcel) : this(parcel.readParcelable(ThumbnailUrl::class.java.classLoader)) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeParcelable(standard, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Thumbnails> {
        override fun createFromParcel(parcel: Parcel): Thumbnails {
            return Thumbnails(parcel)
        }

        override fun newArray(size: Int): Array<Thumbnails?> {
            return arrayOfNulls(size)
        }
    }
}