package sanmi.labs.spotifyclone.common

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.Image as ImageCoil
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import coil.compose.rememberImagePainter
import kotlinx.coroutines.flow.Flow
import sanmi.labs.spotifyclone.R
import sanmi.labs.spotifyclone.model.*
import sanmi.labs.spotifyclone.ui.theme.SpotifyCloneTheme

@Composable
fun PagedSavedTracksList(
    modifier: Modifier,
    tracks: Flow<PagingData<SavedTrack>>,
    listState: LazyListState = rememberLazyListState(),
    onItemClicked: (Track) -> Unit
) {
    val context = LocalContext.current
    val trackItems: LazyPagingItems<SavedTrack> = tracks.collectAsLazyPagingItems()

    LazyColumn(modifier = modifier, state = listState) {
        items(trackItems) { item ->
            item?.let {
                TrackItem(it.track, onItemClicked = {
                    onItemClicked
                })
            }
        }

        trackItems.apply {
            when {
                loadState.refresh is LoadState.Loading -> {
                    //You can add modifier to manage load state when first time response page is loading
                    Toast.makeText(context, "First load", Toast.LENGTH_SHORT).show()
                }
                loadState.append is LoadState.Loading -> {
                    //You can add modifier to manage load state when next response page is loading
                    Toast.makeText(context, "Loading more", Toast.LENGTH_SHORT).show()
                }
                loadState.append is LoadState.Error -> {
                    //You can use modifier to show error message
                    Toast.makeText(context, "Error loading", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}

@Composable
fun PagedTopTracksList(
    modifier: Modifier,
    tracks: Flow<PagingData<Track>>,
    listState: LazyListState = rememberLazyListState(),
    onItemClicked: (Track) -> Unit
) {
    val context = LocalContext.current
    val trackItems: LazyPagingItems<Track> = tracks.collectAsLazyPagingItems()

    LazyColumn(modifier = modifier, state = listState) {
        items(trackItems) { item ->
            item?.let {
                TrackItem(it, onItemClicked = {
                    onItemClicked
                })
            }
        }

        trackItems.apply {
            when {
                loadState.refresh is LoadState.Loading -> {
                    //You can add modifier to manage load state when first time response page is loading
                    Toast.makeText(context, "First load", Toast.LENGTH_SHORT).show()
                }
                loadState.append is LoadState.Loading -> {
                    //You can add modifier to manage load state when next response page is loading
                    Toast.makeText(context, "Loading more", Toast.LENGTH_SHORT).show()
                }
                loadState.append is LoadState.Error -> {
                    //You can use modifier to show error message
                    Toast.makeText(context, "Error loading", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}

@Composable
fun TrackItem(spotifyTrack: Track, onItemClicked: (Track) -> Unit) {

    Surface(
        contentColor = MaterialTheme.colors.surface
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = MaterialTheme.colors.surface)
                .clickable { onItemClicked(spotifyTrack) },
            verticalAlignment = Alignment.CenterVertically,
        ) {
            ImageCoil(
                painter = rememberImagePainter(
                    data = spotifyTrack.album.image.url,
                    builder = {
                        crossfade(true)
                        placeholder(R.drawable.ic_launcher_foreground)
                    },
                ),
                contentDescription = null,
                modifier = Modifier
                    .size(64.dp)
                    .padding(8.dp)
            )
            Column(
                modifier = Modifier
                    .padding(0.dp)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.SpaceAround
            ) {
                Text(spotifyTrack.name, color = MaterialTheme.colors.onSurface)
                Text(spotifyTrack.artistsNames, color = MaterialTheme.colors.onSurface)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TrackItemPreview() {
    SpotifyCloneTheme {
        TrackItem(
            spotifyTrack = Track(
                id = "1",
                album = Album("Más alcohol", listOf(Image(""))),
                artists = listOf(Artist("1", "Natos y Waor"), Artist("2", "Recycled J")),
                name = "Más alcohol"
            )
        ) {}
    }
}

@Preview(uiMode = UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun TrackItemDarkPreview() {
    SpotifyCloneTheme {
        TrackItem(
            spotifyTrack = Track(
                id = "1",
                album = Album("Más alcohol", listOf(Image(""))),
                artists = listOf(Artist("1", "Natos y Waor"), Artist("2", "Recycled J")),
                name = "Más alcohol"
            )
        ) { }
    }
}