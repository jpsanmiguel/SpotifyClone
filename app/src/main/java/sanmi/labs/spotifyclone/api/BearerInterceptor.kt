package sanmi.labs.spotifyclone.api

import android.content.Context
import okhttp3.Interceptor
import okhttp3.Response

class BearerInterceptor : Interceptor {
    lateinit var token: String

    init {
        getPreferences(Context.MODE_PRIVATE)
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()

        if(request.header("No-Authentication")==null){
            //val token = getTokenFromSharedPreference();
            //or use Token Function
            if(!token.isNullOrEmpty())
            {
                val finalToken =  "Bearer "+token
                request = request.newBuilder()
                    .addHeader("Authorization",finalToken)
                    .build()
            }

        }

        return chain.proceed(request)

    }
}