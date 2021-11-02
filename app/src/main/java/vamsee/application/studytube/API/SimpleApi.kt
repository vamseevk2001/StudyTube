package vamsee.application.studytube.API

import com.google.android.gms.common.api.Response
import retrofit2.http.GET
import vamsee.application.studytube.Models.Search

interface SimpleApi {
    @GET("search?q='Android Development'&maxResults=25&key=AIzaSyCod8lPe1sTf5y90E3ScvkTbZ_Ybl4xfs0")
    suspend fun search(): retrofit2.Response<Search>


}