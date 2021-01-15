package se.dsek.sangbok.util

fun getCorrectSwedishLetters(text: String?): String {
    var text = text
    if(!text.isNullOrEmpty()) {
        text = text.replace("&#039;".toRegex(), "'")
        text = text.replace("&aring;".toRegex(), "å")
        text = text.replace("&Aring;".toRegex(), "Å")
        text = text.replace("&auml;".toRegex(), "ä")
        text = text.replace("&Auml;".toRegex(), "Ä")
        text = text.replace("&ouml;".toRegex(), "ö")
        text = text.replace("&Ouml;".toRegex(), "Ö")
        text = text.replace("&amp;".toRegex(), "&")
        text = text.replace("&quot;".toRegex(), "\"")
        text = text.replace("&#8221;".toRegex(), "\"")
        text = text.replace("&#180;".toRegex(), "'")
        text = text.replace("&#8217;".toRegex(), "’")
        text = text.replace("&#8230;".toRegex(), "...")
        text = text.replace("&#8220;".toRegex(), "\"")
        text = text.replace("&#8221;".toRegex(), "\"")
        text = text.replace("&#65533;".toRegex(), "e")
    } else {
        text = ""
    }

    return text
}