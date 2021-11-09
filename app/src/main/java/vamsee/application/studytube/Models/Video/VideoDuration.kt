package vamsee.application.studytube.Models.Video

import android.os.Parcel
import android.os.Parcelable

data class VideoDuration(
    val duration: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(duration)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<VideoDuration> {
        override fun createFromParcel(parcel: Parcel): VideoDuration {
            return VideoDuration(parcel)
        }

        override fun newArray(size: Int): Array<VideoDuration?> {
            return arrayOfNulls(size)
        }
    }
}
