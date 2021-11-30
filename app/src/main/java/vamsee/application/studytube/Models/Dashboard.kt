package vamsee.application.studytube.Models

import android.os.Parcel
import android.os.Parcelable
import vamsee.application.studytube.Models.Video.VideoResponse

data class Dashboard(
    val video: VideoResponse?,
    val channelLogo: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readParcelable(VideoResponse::class.java.classLoader),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeParcelable(video, flags)
        parcel.writeString(channelLogo)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Dashboard> {
        override fun createFromParcel(parcel: Parcel): Dashboard {
            return Dashboard(parcel)
        }

        override fun newArray(size: Int): Array<Dashboard?> {
            return arrayOfNulls(size)
        }
    }
}
