package se.dsek.sangbok.api

data class SongJson(
    val title: String,
    val lyrics: String,
    val approved: String,
    val created: Long,
    val modified: Long,
    val melodyTitle: String,
    val melodySoundclip: String,
    val categoryID: String,
    val categoryTitle: String
)

data class CategoryJson(
    val title: String,
    val description: String
)