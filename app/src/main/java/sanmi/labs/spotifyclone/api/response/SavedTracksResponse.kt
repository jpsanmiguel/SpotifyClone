package sanmi.labs.spotifyclone.api.response

import com.squareup.moshi.Json
import sanmi.labs.spotifyclone.model.SavedTrack

data class SavedTracksResponse(
    @Json(name = "items") val tracks: List<SavedTrack>,
    val total: Int,
    val offset: Int,
)
