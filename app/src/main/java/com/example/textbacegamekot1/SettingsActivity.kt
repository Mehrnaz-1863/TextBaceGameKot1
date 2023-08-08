package com.example.textbacegamekot1

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.RawResourceDataSource
import java.util.*


class SettingsActivity : AppCompatActivity() {
    private lateinit var stopImageView: ImageView
    private lateinit var playImageView: ImageView
    private lateinit var nextImageView: ImageView

    private lateinit var size1TextView: TextView
    private lateinit var size2TextView: TextView
    private lateinit var size3TextView: TextView

    private lateinit var font1TextView: TextView
    private lateinit var font2TextView: TextView
    private lateinit var font3TextView: TextView

    private lateinit var player: ExoPlayer
    private lateinit var playerView: PlayerView
    private lateinit var songArray: List<Uri>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        findViews()
        findMusic()
        onClicks()
    }

    private fun findViews() {
        // Initialize ImageViews
        stopImageView = findViewById(R.id.settings_img_stop)
        playImageView = findViewById(R.id.settings_img_play)
        nextImageView = findViewById(R.id.settings_img_next)
        // Initialize TextViews Size
        size1TextView = findViewById(R.id.settings_tv_size_1)
        size2TextView = findViewById(R.id.settings_tv_size_2)
        size3TextView = findViewById(R.id.settings_tv_size_3)
        // Initialize TextViews Font
        font1TextView = findViewById(R.id.settings_tv_font1)
        font2TextView = findViewById(R.id.settings_tv_font2)
        font3TextView = findViewById(R.id.settings_tv_font3)
        // Initialize ExoPlayer
        player = ExoPlayer.Builder(this).build()

        playerView = findViewById(R.id.player_view)
        playerView.player = player

    }

    private fun findMusic() {
        val rawResourceUri1 = RawResourceDataSource.buildRawResourceUri(R.raw.horror_music_1)
        val rawResourceUri2 = RawResourceDataSource.buildRawResourceUri(R.raw.horror_music_2)
        val rawResourceUri3 = RawResourceDataSource.buildRawResourceUri(R.raw.piano_horror_silent_hill)

        songArray = listOf(rawResourceUri1, rawResourceUri2, rawResourceUri3)
    }

    private fun onClicks() {

        // Set click listeners
        stopImageView.setOnClickListener { handleStopClick() }
        playImageView.setOnClickListener { handlePlayClick() }
        nextImageView.setOnClickListener { handleNextClick() }


        // Set click listeners
        size1TextView.setOnClickListener { handleSizeTextViewClick(size1TextView) }
        size2TextView.setOnClickListener { handleSizeTextViewClick(size2TextView) }
        size3TextView.setOnClickListener { handleSizeTextViewClick(size3TextView) }

        // Set click listeners
        font1TextView.setOnClickListener { handleFontTextViewTypeFace(font1TextView) }
        font2TextView.setOnClickListener { handleFontTextViewTypeFace(font2TextView) }
        font3TextView.setOnClickListener { handleFontTextViewTypeFace(font3TextView) }
    }

    private fun handleStopClick() {
        // Handle stop button click event
        Toast.makeText(this, "Stop clicked", Toast.LENGTH_SHORT).show()
        stopMusic()
    }

    private fun handlePlayClick() {
        // Handle play button click event
        Toast.makeText(this, "Play clicked", Toast.LENGTH_SHORT).show()
        playMusic(R.raw.horror_music_1)
    }

    private fun handleNextClick() {
        // Handle next button click event
        Toast.makeText(this, "Next clicked", Toast.LENGTH_SHORT).show()
        val random = Random().nextInt(3) + 1
        val randomSongUri = songArray[random - 1]
        playNextMusic(R.raw.horror_music_2)
        player.stop()

        val mediaItem = MediaItem.fromUri(randomSongUri)
        player.setMediaItem(mediaItem)
        player.prepare()
        player.play()
    }

    private fun handleSizeTextViewClick(textView: TextView) {
        // Update the text color of the clicked TextView
        textView.isSelected = true
        textView.setTextColor(ContextCompat.getColor(this, R.color.selected_font_color))

        // Reset the text color of the other TextViews
        when (textView.id) {
            R.id.settings_tv_size_1 -> {
                size2TextView.isSelected = false
                size2TextView.setTextColor(ContextCompat.getColor(this, R.color.default_font_color))
                size3TextView.isSelected = false
                size3TextView.setTextColor(ContextCompat.getColor(this, R.color.default_font_color))
                updateTextSize(findViewById(android.R.id.content), 16f) // Change text size to 16sp
            }
            R.id.settings_tv_size_2 -> {
                size1TextView.isSelected = false
                size1TextView.setTextColor(ContextCompat.getColor(this, R.color.default_font_color))
                size3TextView.isSelected = false
                size3TextView.setTextColor(ContextCompat.getColor(this, R.color.default_font_color))
                updateTextSize(findViewById(android.R.id.content), 20f) // Change text size to 16sp
            }
            R.id.settings_tv_size_3 -> {
                size2TextView.isSelected = false
                size2TextView.setTextColor(ContextCompat.getColor(this, R.color.default_font_color))
                size1TextView.isSelected = false
                size1TextView.setTextColor(ContextCompat.getColor(this, R.color.default_font_color))
                updateTextSize(findViewById(android.R.id.content), 24f) // Change text size to 16sp
            }
        }
        // Apply the font to all TextViews in the activity
        applyCustomFontToViews(findViewById(android.R.id.content))
    }

    private fun handleFontTextViewTypeFace(textView: TextView) {
        when (textView.id) {
            R.id.settings_tv_font1 -> {
                FontManager.setTypeface(this, "font1_aloevera.ttf")
                applyCustomFontToViews(findViewById(android.R.id.content))
            }
            R.id.settings_tv_font2 -> {
                FontManager.setTypeface(this, "font3_sunnyspells.otf")
                applyCustomFontToViews(findViewById(android.R.id.content))
            }
            R.id.settings_tv_font3 -> {
                FontManager.setTypeface(this, "font7_printbold.ttf")
                applyCustomFontToViews(findViewById(android.R.id.content))
            }
        }
    }

    private fun playMusic(rawResourceId: Int) {
        val rawResourceUri = RawResourceDataSource.buildRawResourceUri(rawResourceId)
        val mediaItem = MediaItem.fromUri(rawResourceUri)
        player.setMediaItem(mediaItem)
        player.prepare()
        player.play()
    }

    private fun stopMusic() {
        player.stop()
    }

    private fun playNextMusic(rawResourceId: Int) {
        player.stop()
        playMusic(rawResourceId)
    }

    override fun onDestroy() {
        super.onDestroy()
        player.release()
//       FontManager.setTypeface(this, null)
    }

    private fun updateTextSize(root: View, textSize: Float) {
        if (root is ViewGroup) {
            val count = root.childCount
            for (i in 0 until count) {
                val child = root.getChildAt(i)
                updateTextSize(child, textSize)
            }
        } else if (root is TextView) {
            root.textSize = textSize
        }
    }

    private fun applyCustomFontToViews(view: View) {
        if (view is ViewGroup) {
            val count = view.childCount
            for (i in 0 until count) {
                val child = view.getChildAt(i)
                applyCustomFontToViews(child)
            }
        } else if (view is TextView) {
            val typeface = FontManager.getTypeface()
            if (typeface != null) {
                view.typeface = typeface
            }
        }
    }
}