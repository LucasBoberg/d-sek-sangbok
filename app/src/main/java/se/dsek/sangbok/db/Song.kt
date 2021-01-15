package se.dsek.sangbok.db

import androidx.room.*
import se.dsek.sangbok.api.CategoryJson
import se.dsek.sangbok.api.SongJson
import se.dsek.sangbok.util.getCorrectSwedishLetters

@Entity(tableName = "song_table")
data class Song(
    @PrimaryKey var id: Int,
    var title: String,
    var lyrics: String,
    var created: Long,
    var modified: Long,
    var melody: String?,
    var melodyFile: String?,
    var favorite: Boolean = false,
    var lastViewed: Long = 0,
    val categoryId: Int
) {
    companion object {
        public fun fromSongJson(id: Int, songJson: SongJson): Song {
            return Song(id, getCorrectSwedishLetters(songJson.title), getCorrectSwedishLetters(songJson.lyrics), songJson.created, songJson.modified, getCorrectSwedishLetters(songJson.melodyTitle), getCorrectSwedishLetters(songJson.melodySoundclip), false, 0, songJson.categoryID.toInt())
        }
    }
}

@Entity(tableName = "song_table_fts")
@Fts4(contentEntity = Song::class)
data class SongFTS(
    val id: Int,
    val title: String,
    val lyrics: String,
    val melody: String?
    // val category: Category
)

data class SongWithMatchInfo(
    @Embedded
    val song: Song,
    val matchInfo: ByteArray
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as SongWithMatchInfo

        if (song != other.song) return false
        if (!matchInfo.contentEquals(other.matchInfo)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = song.hashCode()
        result = 31 * result + matchInfo.contentHashCode()
        return result
    }
}

@Entity(tableName = "category_table")
data class Category(
    @PrimaryKey var id: Int,
    var title: String,
    var description: String?
) {
    companion object {
        public fun fromCategoryJson(id: Int, categoryJson: CategoryJson): Category {
            return Category(id, getCorrectSwedishLetters(categoryJson.title), getCorrectSwedishLetters(categoryJson.description))
        }
    }
}

data class CategoryWithSongs(
    @Embedded val category: Category,
    @Relation(
        parentColumn = "id",
        entityColumn = "categoryId"
    )
    val songs: List<Song>
)