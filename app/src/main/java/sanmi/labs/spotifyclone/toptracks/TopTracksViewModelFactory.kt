package sanmi.labs.spotifyclone.toptracks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import sanmi.labs.spotifyclone.repository.SpotifyRepository

class TopTracksViewModelFactory(private val spotifyRepository: SpotifyRepository): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return TopTracksViewModel(spotifyRepository = spotifyRepository) as T
    }
}