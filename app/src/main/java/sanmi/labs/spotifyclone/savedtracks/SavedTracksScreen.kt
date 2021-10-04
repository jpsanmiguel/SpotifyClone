package sanmi.labs.spotifyclone.savedtracks

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import sanmi.labs.spotifyclone.ui.theme.SpotifyCloneTheme
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import sanmi.labs.spotifyclone.api.SpotifyApi
import sanmi.labs.spotifyclone.model.Track
import sanmi.labs.spotifyclone.common.*
import sanmi.labs.spotifyclone.db.SavedTracksDatabase
import sanmi.labs.spotifyclone.model.SavedTrack
import sanmi.labs.spotifyclone.repository.SpotifyRepository


@Composable
fun SavedTracksScreenWithState(onItemClicked: (Track) -> Unit) {
    val viewModel: SavedTracksViewModel = viewModel(factory = SavedTracksViewModelFactory(
        SpotifyRepository(
            service = SpotifyApi.retrofitService,
            database = SavedTracksDatabase.getInstance(LocalContext.current),
        )
    ))
//    val isRefreshing by viewModel.isRefreshing.observeAsState()

    val savedTracks = viewModel.response

    SpotifyCloneTheme {
//        SwipeRefresh(
//            state = rememberSwipeRefreshState(isRefreshing ?: false),
//            onRefresh = { viewModel.refresh() }
//        ) {
            SavedTracksScreen(savedTracks = savedTracks, onItemClicked = onItemClicked)
//        }
    }
}

@Composable
fun SavedTracksScreen(savedTracks: Flow<PagingData<SavedTrack>>, onItemClicked: (Track) -> Unit) {
    PagedSavedTracksList(tracks = savedTracks, modifier = Modifier, onItemClicked = onItemClicked)
}

@Preview(
    showBackground = true,
)
@Composable
fun SavedTracksFragmentScreenLightPreview() {
//    SavedTracksScreen(randomTracks(20)) {}
}

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun SavedTracksFragmentScreenDarkPreview() {
//    SavedTracksScreen(randomTracks(20)) {}
}