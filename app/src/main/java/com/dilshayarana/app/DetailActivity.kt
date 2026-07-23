package com.dilshayarana.app

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Html
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide

class DetailActivity : AppCompatActivity() {

    private var rawText: String = ""
    private var imageUrl: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val imgDetail = findViewById<ImageView>(R.id.imgDetail)
        val tvTitle = findViewById<TextView>(R.id.tvDetailTitle)
        val tvContent = findViewById<TextView>(R.id.tvDetailContent)
        val btnCopy = findViewById<Button>(R.id.btnCopy)
        val btnShare = findViewById<Button>(R.id.btnShare)
        val btnDownload = findViewById<Button>(R.id.btnDownload)

        val title = intent.getStringExtra("title") ?: ""
        val contentHtml = intent.getStringExtra("content") ?: ""
        imageUrl = intent.getStringExtra("imageUrl")

        // HTML Tags & &quot; clean karna
        val cleanTitle = Html.fromHtml(title, Html.FROM_HTML_MODE_LEGACY).toString()
        val cleanContent = Html.fromHtml(contentHtml, Html.FROM_HTML_MODE_LEGACY).toString().trim()
        rawText = "$cleanTitle\n\n$cleanContent"

        tvTitle.text = cleanTitle
        tvContent.text = cleanContent

        if (!imageUrl.isNull_or_empty()) {
            imgDetail.visibility = View.VISIBLE
            Glide.with(this).load(imageUrl).into(imgDetail)
            btnDownload.visibility = View.VISIBLE
        } else {
            btnDownload.visibility = View.GONE
        }

        // Copy Button Logic
        btnCopy.setOnClickListener {
            val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("Shayari", rawText)
            clipboard.setPrimaryClip(clip)
            Toast.makeText(this, "शायरी कॉपी हो गई! 📋", Toast.LENGTH_SHORT).show()
        }

        // Share Button Logic
        btnShare.setOnClickListener {
            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, "$rawText\n\nRead more on DilShayarana App!")
            }
            startActivity(Intent.createChooser(shareIntent, "Shayari Share karein:"))
        }

        // Image Browser Open/Save Logic
        btnDownload.setOnClickListener {
            imageUrl?.let { url ->
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                startActivity(intent)
            }
        }

        // Monetag Ad Call
        AdManager.showMonetagAd(this)
    }

    private fun String?.isNull_or_empty(): Boolean = this == null || this.isEmpty()
}
