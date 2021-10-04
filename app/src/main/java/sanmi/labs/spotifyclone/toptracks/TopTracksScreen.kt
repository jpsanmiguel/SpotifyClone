package sanmi.labs.spotifyclone.toptracks

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
import sanmi.labs.spotifyclone.repository.SpotifyRepository

@Composable
fun TopTracksScreenWithState(onItemClicked: (Track) -> Unit) {
    val viewModel: TopTracksViewModel = viewModel(
        factory = TopTracksViewModelFactory(
            SpotifyRepository(
                service = SpotifyApi.retrofitService,
                database = SavedTracksDatabase.getInstance(LocalContext.current),
            )
        )
    )

    val topTracks = viewModel.response

    SpotifyCloneTheme {
        TopTracksScreen(topTracks = topTracks, onItemClicked = onItemClicked)
    }
}

@Composable
fun TopTracksScreen(topTracks: Flow<PagingData<Track>>, onItemClicked: (Track) -> Unit) {
    PagedTopTracksList(tracks = topTracks, modifier = Modifier, onItemClicked = onItemClicked)
}

@Preview(
    showBackground = true,
)
@Composable
fun TopTracksFragmentScreenLightPreview() {
//    TopTracksScreen(randomTracks(20)) {}
}

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun TopTracksFragmentScreenDarkPreview() {
//    TopTracksScreen(randomTracks(20)) {}
}