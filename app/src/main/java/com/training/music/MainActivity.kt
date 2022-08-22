package com.training.music

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.ktx.logEvent
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.ktx.get
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import com.training.music.databinding.ActivityMainBinding
import com.training.music.response.Playlist

class MainActivity : AppCompatActivity() {
    private val TAG = "EVENTO FIREBASE"

    private lateinit var binding : ActivityMainBinding
    private lateinit var firebaseAnalytics: FirebaseAnalytics

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        firebaseAnalytics = Firebase.analytics
        binding.btnEvent.setOnClickListener {
            firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW) {
                param(FirebaseAnalytics.Param.SCREEN_NAME, "Main")
                param(FirebaseAnalytics.Param.SCREEN_CLASS, "MainActivity")
            }
        }

        binding.btnCrash.setOnClickListener {
            throw RuntimeException("Test Crash")
        }

        val remoteConfig: FirebaseRemoteConfig = Firebase.remoteConfig
        val configSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = 60
        }

        remoteConfig.setDefaultsAsync(R.xml.remote_config_defaults)
        remoteConfig.setConfigSettingsAsync(configSettings)
        remoteConfig.fetchAndActivate()
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val updated = task.result
                    binding.titleApp.text = remoteConfig["appTitle"].asString()
                }
            }
        setContentView(binding.root)
    }
    fun actionCancion(view: View) {
        val intent = Intent(this, SongActivity::class.java).apply {}
        startActivity(intent)
    }
    fun actionArtista(view: View) {
        val intent = Intent(this, ArtistActivity::class.java).apply {}
        startActivity(intent)
    }

    fun actionAlbum(view: View) {
        val intent = Intent(this, AlbumActivity::class.java).apply {}
        startActivity(intent)
    }

    fun actionPlaylist(view: View) {
        val intent = Intent(this, PlaylistActivity::class.java).apply {}
        startActivity(intent)
    }
}