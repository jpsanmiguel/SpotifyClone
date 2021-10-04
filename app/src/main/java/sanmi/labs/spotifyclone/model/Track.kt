package sanmi.labs.spotifyclone.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "track")
data class Track(
    @PrimaryKey val id: String,
    val name: String,
    val album: Album,
    val inLibrary: Boolean = false,
    val artists: List<Artist>,
    val artistsNames: String = artists.joinToString { it.name }
)