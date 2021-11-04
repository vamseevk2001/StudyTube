package vamsee.application.studytube.Models.Video

data class VideoDetails(
    val channelId: String,
    val title: String,
   // val description: String,
    val channelTitle: String,
    val thumbnails: HashMap<String, ThumbnailUrl>
)
