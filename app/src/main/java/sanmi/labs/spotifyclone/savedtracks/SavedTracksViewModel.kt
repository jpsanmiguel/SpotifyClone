package sanmi.labs.spotifyclone.savedtracks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.flow.Flow
import sanmi.labs.spotifyclone.model.SavedTrack
import sanmi.labs.spotifyclone.repository.SpotifyRepository

class SavedTracksViewModel(spotifyRepository: SpotifyRepository) : ViewModel() {

//    private val _status = MutableLiveData<SpotifyApiStatus>()
//
//    val status: LiveData<SpotifyApiStatus>
//        get() = _status
//
//    private val _isRefreshing = MutableLiveData<Boolean>()
//    val isRefreshing: LiveData<Boolean>
//        get() = _isRefreshing


    val response: Flow<PagingData<SavedTrack>> =
        spotifyRepository.getSavedTracks().cachedIn(viewModelScope)

//    val responseRemote: Flow<PagingData<SavedTrack>> = Pager(PagingConfig(pageSize = 50)) {
//        RemotePagingSource()
//    }.flow.cachedIn(viewModelScope)

//    val responseLocal = Pager(PagingConfig(50)) {
//        LocalPagingSource(trackDao = trackDao)
//    }.flow.cachedIn(viewModelScope)

//    private val _savedTracks = MutableLiveData<List<SavedTrack>>()
//
//    val savedTracks: LiveData<List<SavedTrack>>
//        get() = _savedTracks

//    init {
//        getSpotifySavedTracks(50)
//    }
//
//    fun refresh() {
//        _isRefreshing.value = true
//        viewModelScope.launch {
//            _status.value = SpotifyApiStatus.LOADING
//            try {
//                _savedTracks.value = SpotifyApi.retrofitService.getSavedTracks(50, 0, TOKEN).tracks
//                _status.value = SpotifyApiStatus.DONE
//                Log.d("SavedTracksViewModel", "Success!")
//            } catch (e: Exception) {
//                _status.value = SpotifyApiStatus.ERROR
//                Log.d("SavedTracksViewModel", "error: ${e.message}")
//            }
//        }
//        _isRefreshing.value = false
//    }
//
//    private fun getSpotifySavedTracks(limit: Int) {
//        viewModelScope.launch {
//            _status.value = SpotifyApiStatus.LOADING
//            try {
//                _savedTracks.value = SpotifyApi.retrofitService.getSavedTracks(limit, 0, TOKEN).tracks
//                _status.value = SpotifyApiStatus.DONE
//                Log.d("SavedTracksViewModel", "Success!")
//            } catch (e: Exception) {
//                _status.value = SpotifyApiStatus.ERROR
//                Log.d("SavedTracksViewModel", "error: ${e.message}")
//            }
//        }
//    }
}