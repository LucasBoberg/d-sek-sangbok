package se.dsek.sangbok.api

import com.google.gson.JsonObject
import retrofit2.http.GET

interface SongApiInterface {

    @GET("arkiv/sanger/api.php?showAll")
    suspend fun getSongs(): JsonObject

    @GET("arkiv/sanger/api.php?categories")
    suspend fun getCategories(): JsonObject
}