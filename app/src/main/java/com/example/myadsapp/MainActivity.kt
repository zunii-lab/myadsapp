package com.example.myadsapp

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback

class MainActivity : AppCompatActivity() {

    private var interstitialAd: InterstitialAd? = null
    private var clickCount = 0

    private val quotes = listOf(
        "Mehnat ka phal meetha hota hai.",
        "Kamyabi unhi ko milti hai jo koshish karte hain.",
        "Har mushkil ke baad asani hai.",
        "Gir kar uthna hi asli jeet hai.",
        "Apne khwabon par yaqeen rakhein.",
        "Waqt se pehle aur naseeb se zyada kuch nahi milta.",
        "Sabar karein, behtari zaroor aayegi.",
        "Khud par bharosa rakhein.",
        "Chhoti koshishein bari tabdeeli laati hain.",
        "Aaj ka din aapka hai, ise zaya na karein."
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        MobileAds.initialize(this) {}

        val quoteText = findViewById<TextView>(R.id.quoteText)
        val newQuoteBtn = findViewById<Button>(R.id.newQuoteBtn)
        val adView = findViewById<AdView>(R.id.adView)

        adView.loadAd(AdRequest.Builder().build())
        quoteText.text = quotes.random()

        newQuoteBtn.setOnClickListener {
            quoteText.text = quotes.random()
            clickCount++
            if (clickCount % 3 == 0) showInterstitial()
        }

        loadInterstitial()
    }

    private fun loadInterstitial() {
        InterstitialAd.load(
            this,
            "ca-app-pub-7809984602999105/7200867892",
            AdRequest.Builder().build(),
            object : InterstitialAdLoadCallback() {
                override fun onAdLoaded(ad: InterstitialAd) { interstitialAd = ad }
                override fun onAdFailedToLoad(error: LoadAdError) { interstitialAd = null }
            }
        )
    }

    private fun showInterstitial() {
        interstitialAd?.let { ad ->
            ad.fullScreenContentCallback = object : FullScreenContentCallback() {
                override fun onAdDismissedFullScreenContent() {
                    interstitialAd = null
                    loadInterstitial()
                }
            }
            ad.show(this)
        } ?: loadInterstitial()
    }
}
