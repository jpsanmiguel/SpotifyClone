package sanmi.labs.spotifyclone.common

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import sanmi.labs.spotifyclone.loginWithSpotify
import sanmi.labs.spotifyclone.model.Album
import sanmi.labs.spotifyclone.model.Artist
import sanmi.labs.spotifyclone.model.Image
import sanmi.labs.spotifyclone.model.Track

fun randomTrack(): Track {
    return Track(randomString(1), randomString(5), randomAlbum(), false, randomArtists(3))
}

fun randomTracks(n: Int): List<Track> {
    val tracks = mutableListOf<Track>()
    for (i in 0 until n) {
        tracks.add(randomTrack())
    }
    return tracks
}

private fun randomAlbum(): Album {
    return Album(randomString(1), randomImages(5))
}

private fun randomAlbums(n: Int): List<Album> {
    val albums = mutableListOf<Album>()
    for (i in 0 until n) {
        albums.add(randomAlbum())
    }
    return albums
}

private fun randomArtist(): Artist {
    return Artist(randomString(1), randomString(5))
}

private fun randomArtists(n: Int): List<Artist> {
    val artists = mutableListOf<Artist>()
    for (i in 0 until n) {
        artists.add(randomArtist())
    }
    return artists
}

private fun randomImage(): Image {
    return Image(randomString(10))
}

private fun randomImages(n: Int): List<Image> {
    val images = mutableListOf<Image>()
    for (i in 0 until n) {
        images.add(randomImage())
    }
    return images
}

private fun randomString(length: Int): String {
    val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
    return (1..length)
        .map { allowedChars.random() }
        .joinToString("")
}

fun checkForInternet(context: Context): Boolean {

    // register activity with the connectivity manager service
    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    // if the android version is equal to M
    // or greater we need to use the
    // NetworkCapabilities to check what type of
    // network has the internet connection
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

        // Returns a Network object corresponding to
        // the currently active default data network.
        val network = connectivityManager.activeNetwork ?: return false

        // Representation of the capabilities of an active network.
        val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false

        return when {
            // Indicates this network uses a Wi-Fi transport,
            // or WiFi has network connectivity
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true

            // Indicates this network uses a Cellular transport. or
            // Cellular has network connectivity
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true

            // else return false
            else -> false
        }
    } else {
        // if the android version is below M
        @Suppress("DEPRECATION") val networkInfo =
            connectivityManager.activeNetworkInfo ?: return false
        @Suppress("DEPRECATION")
        return networkInfo.isConnected
    }

    fun getToken(sharedPref: SharedPreferences, activity: Activity) {
        var token = sharedPref.getString(TOKEN_KEY, "")
        if (token.isNullOrEmpty()) {
            loginWithSpotify(activity)
        }
    }
}