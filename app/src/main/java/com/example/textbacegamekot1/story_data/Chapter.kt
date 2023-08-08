package com.example.textbacegamekot1.story_data

import android.content.Context
import org.json.JSONArray
import org.json.JSONObject

data class Chapter(
    val chapterNumber: Int,
    val chapterTitle: String,
    val chapterText: String
)

fun loadStoryFromJson(context: Context, fileName: String): List<Chapter> {
    val jsonString = context.assets.open(fileName).bufferedReader().use { it.readText() }
    val jsonObject = JSONObject(jsonString)
    val jsonArray = jsonObject.getJSONArray("chapters")
    val chapters = mutableListOf<Chapter>()

    for (i in 0 until jsonArray.length()) {
        val chapterObject = jsonArray.getJSONObject(i)
        val chapter = Chapter(
            chapterNumber = chapterObject.getInt("chapterNumber"),
            chapterTitle = chapterObject.getString("chapterTitle"),
            chapterText = chapterObject.getString("chapterText")
        )
        chapters.add(chapter)
    }

    return chapters
}

fun saveGameDataToJson(context: Context, playerName: String, playerHealth: Int, playerCoins: Int) {
    val jsonObject = JSONObject().apply {
        put("playerName", playerName)
        put("playerHealth", playerHealth)
        put("playerCoins", playerCoins)
    }

    context.openFileOutput("game_data.json", Context.MODE_PRIVATE).use {
        it.write(jsonObject.toString().toByteArray())
    }
}
