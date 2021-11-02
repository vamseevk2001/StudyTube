package vamsee.application.studytube.Repository

import retrofit2.Response
import vamsee.application.studytube.API.RetrofitInstance
import vamsee.application.studytube.Models.Search

class Repository {
    suspend fun search(): Response<Search>{
        return RetrofitInstance.api.search()
    }
}