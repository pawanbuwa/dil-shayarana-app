package com.dilshayarana.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.json.JSONObject
import java.net.URL
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private val postList = mutableListOf<PostItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        fetchBloggerPosts()
    }

    private fun fetchBloggerPosts() {
        thread {
            try {
                // DilShayarana Blogger Feed URL
                val url = "https://www.dilshayarana.com/feeds/posts/default?alt=json"
                val jsonStr = URL(url).readText()
                val jsonObject = JSONObject(jsonStr)
                val feed = jsonObject.getJSONObject("feed")
                val entries = feed.optJSONArray("entry")

                postList.clear()
                if (entries != null) {
                    for (i in 0 until entries.length()) {
                        val item = entries.getJSONObject(i)
                        val title = item.getJSONObject("title").getString("\$t")
                        val content = item.getJSONObject("content").getString("\$t")
                        postList.add(PostItem(title, content))
                    }
                }

                runOnUiThread {
                    val adapter = PostAdapter(postList)
                    recyclerView.adapter = adapter
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
