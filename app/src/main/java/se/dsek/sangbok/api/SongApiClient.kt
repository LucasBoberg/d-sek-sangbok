package se.dsek.sangbok.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object SongApiClient {
    const val URL = "https://www.dsek.se/"

    val songApiClient: Retrofit.Builder by lazy {

        val okhttpClient = OkHttpClient.Builder()

        Retrofit.Builder()
            .baseUrl(URL)
            .client(okhttpClient.build())
            .addConverterFactory(GsonConverterFactory.create())
    }

    val apiInterface: SongApiInterface by lazy {
        songApiClient
            .build()
            .create(SongApiInterface::class.java)
    }
}