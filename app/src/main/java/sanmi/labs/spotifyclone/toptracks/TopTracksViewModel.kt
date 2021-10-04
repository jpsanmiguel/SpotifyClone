package sanmi.labs.spotifyclone.toptracks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.flow.Flow
import sanmi.labs.spotifyclone.model.Track
import sanmi.labs.spotifyclone.repository.SpotifyRepository

class TopTracksViewModel(spotifyRepository: SpotifyRepository) : ViewModel() {
//
//    private val _status = MutableLiveData<SpotifyApiStatus>()
//
//    val status: LiveData<SpotifyApiStatus>
//        get() = _status
//
//    private val _isRefreshing = MutableLiveData<Boolean>()
//    val isRefreshing: LiveData<Boolean>
//        get() = _isRefreshing

    val response: Flow<PagingData<Track>> =
        spotifyRepository.getTopTracks().cachedIn(viewModelScope)
}