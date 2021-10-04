package sanmi.labs.spotifyclone.savedtracks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import sanmi.labs.spotifyclone.repository.SpotifyRepository

class SavedTracksViewModelFactory(private val spotifyRepository: SpotifyRepository): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SavedTracksViewModel(spotifyRepository = spotifyRepository) as T
    }
}