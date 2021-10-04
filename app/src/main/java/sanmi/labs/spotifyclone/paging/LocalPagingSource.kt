package sanmi.labs.spotifyclone.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import retrofit2.HttpException
import sanmi.labs.spotifyclone.api.SpotifyApi
import sanmi.labs.spotifyclone.api.TOKEN
import sanmi.labs.spotifyclone.db.TrackDao
import sanmi.labs.spotifyclone.model.SavedTrack
import sanmi.labs.spotifyclone.model.Track
import java.io.IOException

class LocalPagingSource(private val trackDao: TrackDao) : PagingSource<Int, Track>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Track> {
        return try {
            val nextPage = params.key ?: 0
            val response = trackDao.getTracks(params.loadSize)
            LoadResult.Page(
                data = response,
                prevKey = if (nextPage == 0) null else nextPage - 1,
                nextKey = if (response.isNullOrEmpty()) null else nextPage + 1
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Track>): Int? {
        return state.anchorPosition
    }
}