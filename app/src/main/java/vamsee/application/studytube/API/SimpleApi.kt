package vamsee.application.studytube.API

import retrofit2.http.GET
import retrofit2.http.Query
import vamsee.application.studytube.Models.Channel.Channel
import vamsee.application.studytube.Models.SearchItems
import vamsee.application.studytube.Models.Video.VideoListResponse

interface SimpleApi {
    @GET("search?&maxResults=25&key=AIzaSyCod8lPe1sTf5y90E3ScvkTbZ_Ybl4xfs0")
    //@GET("&maxResults=25&key=AIzaSyD36dzhnPmdlveean9Z33K9_9us5pUIwHI")
    suspend fun search(@Query("q") name: String): retrofit2.Response<SearchItems>

    @GET("videos?part=snippet%2CcontentDetails%2Cstatistics&key=AIzaSyCod8lPe1sTf5y90E3ScvkTbZ_Ybl4xfs0")
    suspend fun getVideoDetails(@Query("id") id: String): retrofit2.Response<VideoListResponse>

    @GET("channels?part=snippet%2Cstatistics&key=AIzaSyCod8lPe1sTf5y90E3ScvkTbZ_Ybl4xfs0")
    suspend fun getChannelDetails(@Query("id") id: String): retrofit2.Response<Channel>

}