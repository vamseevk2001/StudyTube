package vamsee.application.studytube.API

import retrofit2.http.GET
import retrofit2.http.Query
import vamsee.application.studytube.Models.SearchItems
import vamsee.application.studytube.Models.Video.VideoListResponse

interface SimpleApi {
    @GET("search?q='Android Development'&maxResults=25&key=AIzaSyCod8lPe1sTf5y90E3ScvkTbZ_Ybl4xfs0")
    suspend fun search(): retrofit2.Response<SearchItems>

    @GET("videos?part=snippet%2CcontentDetails%2Cstatistics&key=AIzaSyCod8lPe1sTf5y90E3ScvkTbZ_Ybl4xfs0")
    suspend fun getVideoDetails(@Query("id") id: String): retrofit2.Response<VideoListResponse>

}