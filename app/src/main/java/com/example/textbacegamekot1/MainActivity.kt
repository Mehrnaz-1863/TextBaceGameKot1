package com.example.textbacegamekot1

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.animation.AlphaAnimation
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.textbacegamekot1.data_store.DataStoreViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.io.IOException
import java.nio.charset.Charset

class MainActivity : AppCompatActivity() {
    private lateinit var tvWelcome: TextView
    private lateinit var imgLogin: ImageView
    private lateinit var ll_menu: LinearLayout
    private lateinit var vibrator: Vibrator
    private var mediaPlayer: MediaPlayer? = null
    private lateinit var viewModel: DataStoreViewModel

    lateinit var btnNewGame: Button
    lateinit var btnContinueGame: Button
    lateinit var btnSetting: Button
    lateinit var btnAbout: Button
    lateinit var btnExitGame: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViews()
        // Start playing music
        mediaPlayer?.start()

        //  viewModel = ViewModelProvider(this).get(DataStoreViewModel::class.java)
        viewModel = ViewModelProvider(this)[DataStoreViewModel::class.java]
        loadJsonAndSaveItIntoWidgets()
    }

    private fun findViews() {
        tvWelcome = findViewById(R.id.tvWelcome)
        imgLogin = findViewById(R.id.imgLogin)
        ll_menu = findViewById<LinearLayout>(R.id.ll_menu_buttons)
        vibrator = ContextCompat.getSystemService(this, Vibrator::class.java) as Vibrator
        // Create and configure MediaPlayer
        mediaPlayer = MediaPlayer.create(this, R.raw.piano_horror_silent_hill)
        mediaPlayer?.isLooping = true

        btnNewGame = findViewById(R.id.btnNewGame)
        btnContinueGame = findViewById(R.id.btnContinue)
        btnSetting = findViewById(R.id.btnSettings)
        btnAbout = findViewById(R.id.btnAbout)
        btnExitGame = findViewById(R.id.btnExitGame)

    }


    override fun onDestroy() {
        super.onDestroy()
        // Release MediaPlayer resources
        mediaPlayer?.release()
        mediaPlayer = null
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("SetTextI18n")
    fun openLoginDialog(view: View) {
        val dialogBuilder = AlertDialog.Builder(this)
        val inflater: LayoutInflater = this.layoutInflater
        val dialogView: View = inflater.inflate(R.layout.dialog_login, null)
        dialogBuilder.setView(dialogView)

        val etName = dialogView.findViewById<EditText>(R.id.etName)

        dialogBuilder.setTitle("Login")
        dialogBuilder.setMessage("Enter your name:")
        dialogBuilder.setPositiveButton("Login") { dialogInterface: DialogInterface, _: Int ->

            //Set visibility of my login button(image_view) to gone
            imgLogin.visibility = View.GONE
            val name = etName.text.toString()
            tvWelcome.text = "Welcome, $name!"

            // Fade animation
            val fadeAnimation = AlphaAnimation(0f, 1f)
            fadeAnimation.duration = 3000
            ll_menu.startAnimation(fadeAnimation)

            //Set visibility of my menu layout to visible
            ll_menu.visibility = View.VISIBLE

            // Vibration effect
            val vibrationEffect =
                VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE)
            vibrator.vibrate(vibrationEffect)

            dialogInterface.dismiss()
        }
        dialogBuilder.setNegativeButton("Cancel") { dialogInterface: DialogInterface, _: Int ->
            dialogInterface.dismiss()
        }

        val alertDialog: AlertDialog = dialogBuilder.create()
        alertDialog.show()
    }

    fun startNewGame(view: View) {
        // Handle "New Game" button click
        val intent = Intent(this, CharacterSheet1Activity::class.java)
        startActivity(intent)
        // Apply transition animation
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }

    fun continueGame(view: View) {
        // Handle "Continue" button click
//        val intent = Intent(this, CharacterSelectActivity::class.java)
//        startActivity(intent)
        lifecycleScope.launch(Dispatchers.IO) {
            loadData()
        }
    }

    fun openAboutScreen(view: View) {
        // Handle "About" button click
        val intent = Intent(this, AboutActivity::class.java)
        startActivity(intent)
        // Apply transition animation
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }

    fun openSettingsScreen(view: View) {
        // Handle "About" button click
        val intent = Intent(this, SettingsActivity::class.java)
        startActivity(intent)
        // Apply transition animation
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }

    fun exitGame(view: View) {
        // Handle "Exit Game" button click
        finishAffinity() // Close the application
        // Apply transition animation
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }


    private suspend fun loadData() {
        val dataStore = viewModel.dataStore
        val characterNameKey = stringPreferencesKey("character_name")
        val characterNameValue = dataStore.data.map { preferences ->
            preferences[characterNameKey] ?: "No Name"
        }
        val characterClassKey = stringPreferencesKey("character_class")
        val characterClassValue = dataStore.data.map { Preferences ->
            Preferences[characterClassKey] ?: "No Class"
        }
        val characterBackgroundKey = stringPreferencesKey("character_background")
        val characterBackgroundValue = dataStore.data.map { Preferences ->
            Preferences[characterBackgroundKey] ?: "No Background"
        }
        val characterRaceKey = stringPreferencesKey("character_race")
        val characterRaceValue = dataStore.data.map { Preferences ->
            Preferences[characterRaceKey] ?: "No Race"
        }

        val characterAlignmentKey = stringPreferencesKey("character_alignment")
        val characterAlignmentValue = dataStore.data.map { Preferences ->
            Preferences[characterAlignmentKey] ?: "No Alignment"
        }
        val characterAgeKey = stringPreferencesKey("character_age")
        val characterAgeValue = dataStore.data.map { Preferences ->
            Preferences[characterAgeKey] ?: "No Age"
        }

        val characterHeightKey = stringPreferencesKey("character_height")
        val characterHeightValue = dataStore.data.map { Preferences ->
            Preferences[characterHeightKey] ?: "No Height"
        }
        val characterWeightKey = stringPreferencesKey("character_weight")
        val characterWeightValue = dataStore.data.map { Preferences ->
            Preferences[characterWeightKey] ?: "No Weight"
        }
        val characterHairKey = stringPreferencesKey("character_hair")
        val characterHairValue = dataStore.data.map { Preferences ->
            Preferences[characterHairKey] ?: "No Hair Color"
        }
        val characterEyesKey = stringPreferencesKey("character_eyes")
        val characterEyesValue = dataStore.data.map { Preferences ->
            Preferences[characterEyesKey] ?: "No Eye Color"
        }

        // Log the value emitted by the Flow
        characterNameValue.collect { value ->
            Log.d("Load Data", "character_name:$value")
        }
        characterClassValue.collect { value ->
            Log.d("Load Data", "character_class:$value")
        }
        characterBackgroundValue.collect { value ->
            Log.d("Load Data", "character_background:$value")
        }
        characterRaceValue.collect { value ->
            Log.d("Load Data", "character_race:$value")
        }
        characterAlignmentValue.collect { value ->
            Log.d("Load Data", "character_alignment:$value")
        }
        characterAgeValue.collect { value ->
            Log.d("Load Data", "character_age:$value")
        }
        characterHeightValue.collect { value ->
            Log.d("Load Data", "character_height")
        }
        characterWeightValue.collect { value ->
            Log.d("Load Data", "character_weight:$value")
        }
        characterHairValue.collect { value ->
            Log.d("Load Data", "character_hair:$value")
        }
        characterEyesValue.collect { value ->
            Log.d("Load Data", "character_eyes: $value")
        }


        // Update the UI in the main (UI) thread
//        lifecycleScope.launch(Dispatchers.Main) {
//            characterNameTextView.text = characterNameValue.toString()
//            classTextView.text = characterClassValue.toString()
//            backgroundTextView.text = characterBackgroundValue.toString()
//            raceTextView.text = characterRaceValue.toString()
//            alignmentTextView.text = characterAlignmentValue.toString()
//            ageTextView.text = characterAgeValue.toString()
//            heightTextView.text = characterHeightValue.toString()
//            weightTextView.text = weight.toString()
//            hairColorTextView.text = characterHairValue.toString()
//            eyesColorTextView.text = characterEyesValue.toString()
//        }
    }

    @SuppressLint("LongLogTag")
    private fun loadJsonAndSaveItIntoWidgets() {
        // Load the JSON file from the assets folder
        val jsonString: String = loadJSONFromAsset("application_primary_texts.json")

        // Parse the JSON string into a JSONObject
        val jsonObject = JSONObject(jsonString)

        // Access the values from the JSON object

        val mainMenuTitle = jsonObject.getJSONObject("mainMenu").getString("menuTitle_tv")
        Log.d("application primary texts", "loadJsonAndSaveItIntoWidgets:$mainMenuTitle")
        tvWelcome.text = mainMenuTitle

        val mainMenuNewGame = jsonObject.getJSONObject("mainMenu").getString("newGame_btn")
        Log.d("application primary texts", "loadJsonAndSaveItIntoWidgets:$mainMenuNewGame")
        btnNewGame.text = mainMenuNewGame

        val mainMenuContinue = jsonObject.getJSONObject("mainMenu").getString("continue_btn")
        Log.d("application primary texts", "loadJsonAndSaveItIntoWidgets:$mainMenuContinue")
        btnContinueGame.text = mainMenuContinue

        val mainMenuAbout = jsonObject.getJSONObject("mainMenu").getString("about_btn")
        Log.d("application primary texts", "loadJsonAndSaveItIntoWidgets:$mainMenuAbout")
        btnAbout.text = mainMenuAbout

        val mainMenuExitGame = jsonObject.getJSONObject("mainMenu").getString("exitGame_btn")
        Log.d("application primary texts", "loadJsonAndSaveItIntoWidgets:$mainMenuExitGame")
        btnExitGame.text = mainMenuExitGame

        val mainMenuSettings = jsonObject.getJSONObject("mainMenu").getString("settings_btn")
        Log.d("application primary texts", "loadJsonAndSaveItIntoWidgets:$mainMenuSettings")
        btnSetting.text = mainMenuSettings

    }

    private fun loadJSONFromAsset(filename: String): String {
        return try {
            val inputStream = assets.open(filename)
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            buffer.toString(Charset.defaultCharset())
        } catch (ex: IOException) {
            ex.printStackTrace()
            "{}" // Return an empty JSON object in case of an error
        }
    }



}

