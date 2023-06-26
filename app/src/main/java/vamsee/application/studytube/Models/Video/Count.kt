package vamsee.application.studytube.Models.Video

import android.os.Parcel
import android.os.Parcelable

data class Count(
    val viewCount: String? = "",
    val likeCount: String? = "",
    //val dislikeCount: String? = ""
): Parcelable{
    constructor(parcel: Parcel):this(
        parcel.readString(),
        parcel.readString(),
    ){

    }

    override fun writeToParcel(p0: Parcel?, p1: Int) {
        p0?.writeString(viewCount)
        p0?.writeString(likeCount)

    }

//
//    override fun writeToParcel(parcel: Parcel, flags: Int) {
//        parcel.writeString(viewCount)
//        parcel.writeString(likeCount)
//       // parcel.writeString(dislikeCount)
//    }
//
    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Count> {
        override fun createFromParcel(parcel: Parcel): Count {
            return Count(parcel)
        }

        override fun newArray(size: Int): Array<Count?> {
            return arrayOfNulls(size)
        }
    }
}
