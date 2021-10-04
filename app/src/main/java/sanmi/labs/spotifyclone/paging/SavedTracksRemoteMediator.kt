package sanmi.labs.spotifyclone.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import retrofit2.HttpException
import sanmi.labs.spotifyclone.api.SpotifyApiService
import sanmi.labs.spotifyclone.common.PAGE_SIZE
import sanmi.labs.spotifyclone.db.SavedTracksDatabase
import sanmi.labs.spotifyclone.model.Track
import java.io.IOException
import java.util.concurrent.TimeUnit

@OptIn(ExperimentalPagingApi::class)
class SavedTracksRemoteMediator(
    private val offset: Int,
    private val database: SavedTracksDatabase,
    private val spotifyApiService: SpotifyApiService,
) : RemoteMediator<Int, Track>() {
    val trackDao = database.trackDao()

    override suspend fun load(loadType: LoadType, state: PagingState<Int, Track>): MediatorResult {
        return try {
            val page = when (loadType) {
                LoadType.REFRESH -> 0
                LoadType.PREPEND ->
                    return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = true
                        )

                    // You must explicitly check if the last item is null when
                    // appending, since passing null to networkService is only
                    // valid for initial load. If lastItem is null it means no
                    // items were loaded after the initial REFRESH and there are
                    // no more items to load.

                    (state.pages.size - 1) * PAGE_SIZE
                }
            }

            // Suspending network load via Retrofit. This doesn't need to be
            // wrapped in a withContext(Dispatcher.IO) { ... } block since
            // Retrofit's Coroutine CallAdapter dispatches on a worker
            // thread.
            val response = spotifyApiService.getSavedTracks(
                offset = offset
            )

            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    trackDao.clearAll()
                }

                // Insert new users into database, which invalidates the
                // current PagingData, allowing Paging to present the updates
                // in the DB.
                trackDao.insertAll(response.tracks.map { it.track })
            }

            MediatorResult.Success(
                endOfPaginationReached = response.offset == null
            )
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }

    override suspend fun initialize(): InitializeAction {
//        val cacheTimeout = TimeUnit.HOURS.convert(1, TimeUnit.MILLISECONDS)
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

}