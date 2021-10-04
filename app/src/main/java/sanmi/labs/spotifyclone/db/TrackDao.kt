package sanmi.labs.spotifyclone.db

import androidx.paging.DataSource
import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import sanmi.labs.spotifyclone.model.Track

@Dao
interface TrackDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(tracks: List<Track>)

    @Query("SELECT * FROM track")
    fun pagingSource(): PagingSource<Int, Track>

    @Query("SELECT * FROM track")
    fun getAllPaged(): DataSource.Factory<Int, Track>

    @Query("SELECT * FROM track LIMIT :size")
    suspend fun getTracks(size: Int): List<Track>

    @Query("DELETE FROM track")
    suspend fun clearAll()
}