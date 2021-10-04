package sanmi.labs.spotifyclone.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import sanmi.labs.spotifyclone.common.Converters
import sanmi.labs.spotifyclone.model.Track

@Database(
    entities = [Track::class],
    version = 1,
)
@TypeConverters(Converters::class)
abstract class SavedTracksDatabase : RoomDatabase() {
    abstract fun trackDao(): TrackDao

    companion object {

        @Volatile
        private var INSTANCE: SavedTracksDatabase? = null

        fun getInstance(context: Context): SavedTracksDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                SavedTracksDatabase::class.java, "SavedTracks.db"
            )
                .build()

    }
}