package com.example.textbacegamekot1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import com.example.textbacegamekot1.story_data.loadStoryFromJson

class NewGameActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_game)

        val chapters = loadStoryFromJson(applicationContext, "story.json")
        val chapterToLoad = chapters[0]

        val chapterTextView = findViewById<TextView>(R.id.chapterTextView)


        // Make the chapterTextView visible before starting the animation
        chapterTextView.visibility = View.VISIBLE

        // Animate the chapterTextView from the top and apply fade-in animation
        val slideAndFadeAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_down)
        chapterTextView.startAnimation(slideAndFadeAnimation)

        // Make the chapterTextView visible before starting the animation
        chapterTextView.visibility = View.VISIBLE

        // Set the chapter information in the TextView
        chapterTextView.text =
            "Chapter Number: ${chapterToLoad.chapterNumber}\n" + "Chapter Title: ${chapterToLoad.chapterTitle}\n" + "Chapter Text: ${chapterToLoad.chapterText}"

        // Show the buttons layout with a slide-up animation
        val buttonLayout = findViewById<LinearLayout>(R.id.buttonLayout)
        val slideUpAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_up)
        buttonLayout.startAnimation(slideUpAnimation)

        // Set click listeners for the buttons
        val btn1 = findViewById<Button>(R.id.btn1)
        val btn2 = findViewById<Button>(R.id.btn2)
        val btn3 = findViewById<Button>(R.id.btn3)

        btn1.setOnClickListener {
            // Handle button 1 click
            // Perform the action associated with button 1
        }

        btn2.setOnClickListener {
            // Handle button 2 click
            // Perform the action associated with button 2
        }

        btn3.setOnClickListener {
            // Handle button 3 click
            // Perform the action associated with button 3
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        finish()
        // Apply transition animation
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }
}