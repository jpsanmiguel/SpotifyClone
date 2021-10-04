package sanmi.labs.spotifyclone.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.paging.*
import kotlinx.coroutines.flow.Flow
import sanmi.labs.spotifyclone.api.SpotifyApiService
import sanmi.labs.spotifyclone.common.PAGE_SIZE
import sanmi.labs.spotifyclone.db.SavedTracksDatabase
import sanmi.labs.spotifyclone.model.SavedTrack
import sanmi.labs.spotifyclone.model.Track
import sanmi.labs.spotifyclone.paging.RemoteSavedTracksPagingSource
import sanmi.labs.spotifyclone.paging.RemoteTopTracksPagingSource
import sanmi.labs.spotifyclone.paging.SavedTracksRemoteMediator

class SpotifyRepository(
    private val service: SpotifyApiService,
    private val database: SavedTracksDatabase,
) {

    fun getSavedTracks(): Flow<PagingData<SavedTrack>> {
        return Pager(PagingConfig(pageSize = PAGE_SIZE)) {
            RemoteSavedTracksPagingSource()
        }.flow
    }

    fun getTopTracks(): Flow<PagingData<Track>> {
        return Pager(PagingConfig(pageSize = PAGE_SIZE)) {
            RemoteTopTracksPagingSource()
        }.flow
    }

    fun getSearchResultStream(offset: Int): Flow<PagingData<Track>> {
        val pagingSourceFactory = { database.trackDao().pagingSource()}

        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(pageSize = PAGE_SIZE, enablePlaceholders = false),
            remoteMediator = SavedTracksRemoteMediator(
                offset,
                database,
                service,
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }
}