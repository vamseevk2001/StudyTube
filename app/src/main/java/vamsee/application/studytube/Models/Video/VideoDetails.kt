package vamsee.application.studytube.Models.Video

import android.os.Parcel
import android.os.Parcelable

data class VideoDetails(
    val channelId: String?,
    val publishedAt: String?,
    val title: String?,
    val description: String?,
    val channelTitle: String?,
    val thumbnails: HashMap<String, ThumbnailUrl> = HashMap()
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        hashMapOf<String, ThumbnailUrl>()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(channelId)
        parcel.writeString(publishedAt)
        parcel.writeString(title)
        parcel.writeString(description)
        parcel.writeString(channelTitle)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<VideoDetails> {
        override fun createFromParcel(parcel: Parcel): VideoDetails {
            return VideoDetails(parcel)
        }

        override fun newArray(size: Int): Array<VideoDetails?> {
            return arrayOfNulls(size)
        }
    }
}