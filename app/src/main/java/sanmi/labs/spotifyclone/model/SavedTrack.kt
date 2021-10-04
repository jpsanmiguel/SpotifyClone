package sanmi.labs.spotifyclone.model

import com.squareup.moshi.Json

data class SavedTrack(
    @Json(name = "added_at") val addedAt: String,
    val track: Track,
)
