package vamsee.application.studytube.Models.Channel

import vamsee.application.studytube.Models.Video.ThumbnailUrl
import vamsee.application.studytube.Models.Video.Thumbnails

data class ChannelDetails(
    val title: String,
    val thumbnails: HashMap<String, ThumbnailUrl>
)
