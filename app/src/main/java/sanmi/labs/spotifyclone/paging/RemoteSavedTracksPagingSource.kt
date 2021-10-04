package sanmi.labs.spotifyclone.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import retrofit2.HttpException
import sanmi.labs.spotifyclone.api.SpotifyApi
import sanmi.labs.spotifyclone.api.TOKEN
import sanmi.labs.spotifyclone.common.PAGE_SIZE
import sanmi.labs.spotifyclone.model.SavedTrack
import java.io.IOException

class RemoteSavedTracksPagingSource : PagingSource<Int, SavedTrack>() {
    override fun getRefreshKey(state: PagingState<Int, SavedTrack>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, SavedTrack> {
        return try {
            val nextPage = params.key ?: 0
            val response = SpotifyApi.retrofitService.getSavedTracks(offset = nextPage)
            LoadResult.Page(
                data = response.tracks,
                prevKey = if (nextPage == 0) null else nextPage + response.offset,
                nextKey = if (response.tracks.isEmpty()) null else response.offset + PAGE_SIZE
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }
}