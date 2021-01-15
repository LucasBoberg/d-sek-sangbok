package se.dsek.sangbok.api

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import se.dsek.sangbok.db.Category
import se.dsek.sangbok.db.Song


object JSONConverter {
    fun getAllSongs(json: JsonObject): List<Song> {
        val type = object : TypeToken<Map<Int?, SongJson?>?>() {}.type
        val map: Map<Int, SongJson> = Gson().fromJson(json, type)
        val list: MutableList<Song> = ArrayList()
        for ((id, body) in map) {
            list.add(Song.fromSongJson(id, body))
        }

        return list.toList()
    }

    fun getAllCategories(json: JsonObject): List<Category> {
        val type = object : TypeToken<Map<Int?, CategoryJson?>?>() {}.type
        val map: Map<Int, CategoryJson> = Gson().fromJson(json, type)
        val list: MutableList<Category> = ArrayList()
        for ((id, body) in map) {
            list.add(Category.fromCategoryJson(id, body))
        }

        return list.toList()
    }
}
