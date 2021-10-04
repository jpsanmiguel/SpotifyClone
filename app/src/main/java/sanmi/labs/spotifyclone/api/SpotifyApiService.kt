package sanmi.labs.spotifyclone.api

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query
import sanmi.labs.spotifyclone.api.response.SavedTracksResponse
import sanmi.labs.spotifyclone.api.response.TopTracksResponse

private const val BASE_URL = "https://api.spotify.com"
val TOKEN = "Bearer BQCXZ6U69Yq4zrlLN-MS7ZCOtgyC2x3gIPl-w7JlE21zJBqa7tFiQByM7-4uzEEqpqVxErg9dYVh8h-avTBdM9PW7AYFC29vWvirzEuTfXKPkvaJ2fmaJzyuhmQYPZu3UfLevB1IXhwXW3fUQgpcqwHnrv61mHzjJSAze3xDqg"

enum class SpotifyApiStatus { REFRESHING, LOADING, ERROR, DONE }

private fun initializeHttpLoggingInterceptor(): HttpLoggingInterceptor {
    val interceptor = HttpLoggingInterceptor()
    interceptor.level = HttpLoggingInterceptor.Level.BODY
    return interceptor
}

private val client = OkHttpClient
    .Builder()
    .addInterceptor(initializeHttpLoggingInterceptor())
    .addInterceptor(BearerInterceptor())
    .build()


private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .client(client)
    .build()

interface SpotifyApiService {

    @GET("/v1/me/top/tracks")
    suspend fun getTopTracks( @Query("offset") offset: Int, @Header("Authorization") authHeader: String = TOKEN): TopTracksResponse


    @GET("/v1/me/tracks")
    suspend fun getSavedTracks(@Query("offset") offset: Int, @Header("Authorization") authHeader: String = TOKEN): SavedTracksResponse
}

object SpotifyApi {
    val retrofitService : SpotifyApiService by lazy { retrofit.create(SpotifyApiService::class.java) }
}