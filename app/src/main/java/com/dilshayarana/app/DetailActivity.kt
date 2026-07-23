package com.dilshayarana.app

import android.os.Build
import android.os.Bundle
import android.text.Html
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val imgDetail = findViewById<ImageView>(R.id.imgDetail)
        val tvTitle = findViewById<TextView>(R.id.tvDetailTitle)
        val tvContent = findViewById<TextView>(R.id.tvDetailContent)

        val title = intent.getStringExtra("EXTRA_TITLE") ?: ""
        val content = intent.getStringExtra("EXTRA_CONTENT") ?: ""
        val imageUrl = intent.getStringExtra("EXTRA_IMAGE")

        tvTitle.text = title

        // HTML Content ko Sahi Se Format Karke Dikhana
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            tvContent.text = Html.fromHtml(content, Html.FROM_HTML_MODE_COMPACT)
        } else {
            @Suppress("DEPRECATION")
            tvContent.text = Html.fromHtml(content)
        }

        // Image Load Karein
        if (!imageUrl.isNullOrEmpty()) {
            imgDetail.visibility = View.VISIBLE
            Glide.with(this).load(imageUrl).into(imgDetail)
        }

        // Detail Page Khulne Par Monetag Direct Link Ad Trigger Karein
        AdManager.showMonetagAd(this)
    }
}
