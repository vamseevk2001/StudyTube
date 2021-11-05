package vamsee.application.studytube.Models.Video

import vamsee.application.studytube.Models.Video.VideoDetails

data class VideoResponse(
    val kind: String,
    val id: String,
    val snippet: VideoDetails,
    val contentDetails: VideoDuration,
    val statistics: Count

)
