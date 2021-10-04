package sanmi.labs.spotifyclone.api.response

import com.squareup.moshi.Json
import sanmi.labs.spotifyclone.model.Track

data class TopTracksResponse(
    @Json(name = "items") val tracks: List<Track>,
    val total: Int,
    val offset: Int,
)
