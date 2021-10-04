package sanmi.labs.spotifyclone.common

import androidx.room.TypeConverter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.adapter
import sanmi.labs.spotifyclone.model.Album
import sanmi.labs.spotifyclone.model.Artist
import sanmi.labs.spotifyclone.model.Image

class Converters {

    val moshi = Moshi.Builder().build()

    val artistData = Types.newParameterizedType(List::class.java, Artist::class.java)
    val imageData = Types.newParameterizedType(List::class.java, Image::class.java)

    val albumAdapter = moshi.adapter(Album::class.java)
    val artistAdapter = moshi.adapter(Artist::class.java)
    val artistsAdapter = moshi.adapter<List<Artist>>(artistData::class.java)
    val imageAdapter = moshi.adapter(Image::class.java)
    val imagesAdapter = moshi.adapter<List<Image>>(imageData::class.java)

    @TypeConverter
    fun jsonToAlbum(json: String?): Album? {
        return albumAdapter.fromJson(json)
    }

    @TypeConverter
    fun albumToJson(album: Album): String? {
        return albumAdapter.toJson(album)
    }

    @TypeConverter
    fun jsonToArtistList(json: String?): List<Artist>? {
        return artistsAdapter.fromJson(json)
    }

    @TypeConverter
    fun artistListToJson(artist: List<Artist>): String? {
        return artistsAdapter.toJson(artist)
    }

    @TypeConverter
    fun jsonToArtist(json: String?): Artist? {
        return artistAdapter.fromJson(json)
    }

    @TypeConverter
    fun artistToJson(artist: Artist): String? {
        return artistAdapter.toJson(artist)
    }

    @TypeConverter
    fun jsonToImageList(json: String?): List<Image>? {
        return imagesAdapter.fromJson(json)
    }

    @TypeConverter
    fun imageListToJson(image: List<Image>): String? {
        return imagesAdapter.toJson(image)
    }

    @TypeConverter
    fun jsonToImage(json: String?): Image? {
        return imageAdapter.fromJson(json)
    }

    @TypeConverter
    fun imageToJson(image: Image): String? {
        return imageAdapter.toJson(image)
    }
}
