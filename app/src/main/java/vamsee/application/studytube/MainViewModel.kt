package vamsee.application.studytube

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.Response
import vamsee.application.studytube.Models.SearchItems
import vamsee.application.studytube.Models.Video.VideoListResponse
import vamsee.application.studytube.Repository.Repository

class MainViewModel(private val repository: Repository) : ViewModel() {
    val myResponse: MutableLiveData<retrofit2.Response<SearchItems>> = MutableLiveData()
    val videoResponse: MutableLiveData<retrofit2.Response<VideoListResponse>> = MutableLiveData()
    var id: List<Response<SearchItems>> = listOf()
    fun search() {
        viewModelScope.launch {
            val searchRes = repository.search()
            myResponse.value = searchRes
            id = listOf(searchRes)
        }
    }

    fun getVideoDetails(id: String) {
        viewModelScope.launch {
            val videoListResponse = repository.getVideoDetails(id)
            videoResponse.value = videoListResponse
        }
    }
}