package vamsee.application.studytube.Repository

import retrofit2.Response
import vamsee.application.studytube.API.RetrofitInstance
import vamsee.application.studytube.Models.Channel.Channel
import vamsee.application.studytube.Models.SearchItems
import vamsee.application.studytube.Models.Video.VideoListResponse

class Repository {
    suspend fun search(name: String): Response<SearchItems>{
        return RetrofitInstance.api.search(name)
    }

    suspend fun getVideoDetails(id: String): Response<VideoListResponse>{
        return RetrofitInstance.api.getVideoDetails(id)
    }

    suspend fun getChannelDetails(id: String): Response<Channel>{
        return RetrofitInstance.api.getChannelDetails(id)
    }
}