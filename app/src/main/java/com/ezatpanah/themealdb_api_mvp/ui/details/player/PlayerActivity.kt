package com.ezatpanah.themealdb_api_mvp.ui.details.player

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import com.ezatpanah.themealdb_api_mvp.R
import com.ezatpanah.themealdb_api_mvp.databinding.ActivityPlayerBinding
import com.ezatpanah.themealdb_api_mvp.utils.Constant.VIDEO_ID
import com.ezatpanah.themealdb_api_mvp.utils.Constant.YOUTUBE_API_KEY
import com.ezatpanah.themealdb_api_mvp.utils.showSnackBar
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer

@Suppress("DEPRECATION")
class PlayerActivity : YouTubeBaseActivity() {

    private lateinit var binding: ActivityPlayerBinding

    //Other
    private var videoId = ""
    private lateinit var player: YouTubePlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlayerBinding.inflate(layoutInflater)
        //Full screen
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(binding.root)
        //Get data
        videoId = intent.getStringExtra(VIDEO_ID).toString()
        //InitViews
        binding.apply {
            val listener = object : YouTubePlayer.OnInitializedListener {
                override fun onInitializationSuccess(p0: YouTubePlayer.Provider?, p1: YouTubePlayer, p2: Boolean) {
                    player = p1
                    player.loadVideo(videoId)
                    player.play()
                }

                override fun onInitializationFailure(p0: YouTubePlayer.Provider?, p1: YouTubeInitializationResult?) {
                    root.showSnackBar("Error")
                }
            }
            videoPlayer.initialize(YOUTUBE_API_KEY, listener)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        player.release()
    }
}