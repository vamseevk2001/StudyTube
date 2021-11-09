package vamsee.application.studytube.Models.Video

import android.os.Parcel
import android.os.Parcelable

data class VideoResponse(
    val kind: String?,
    val id: String?,
    val snippet: VideoDetails?,
    val contentDetails: VideoDuration?,
    val statistics: Count?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readParcelable(VideoDetails::class.java.classLoader),
        parcel.readParcelable(VideoDuration::class.java.classLoader),
        parcel.readParcelable(Count::class.java.classLoader)
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(kind)
        parcel.writeString(id)
        parcel.writeParcelable(snippet, flags)
        parcel.writeParcelable(contentDetails, flags)
        parcel.writeParcelable(statistics, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<VideoResponse> {
        override fun createFromParcel(parcel: Parcel): VideoResponse {
            return VideoResponse(parcel)
        }

        override fun newArray(size: Int): Array<VideoResponse?> {
            return arrayOfNulls(size)
        }
    }
}