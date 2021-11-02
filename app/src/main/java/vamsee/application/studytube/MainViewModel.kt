package vamsee.application.studytube

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.common.api.Response
import kotlinx.coroutines.launch
import vamsee.application.studytube.Models.Search
import vamsee.application.studytube.Repository.Repository

class MainViewModel(private val repository: Repository):ViewModel() {
    val myResponse: MutableLiveData<retrofit2.Response<Search>> = MutableLiveData()

    fun search(){
        viewModelScope.launch {
            val searchRes = repository.search()
            myResponse.value = searchRes
        }
    }
}