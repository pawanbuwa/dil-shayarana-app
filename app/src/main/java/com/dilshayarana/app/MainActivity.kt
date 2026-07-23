package com.dilshayarana.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import org.json.JSONObject
import java.net.URL
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private val postList = mutableListOf<PostItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        swipeRefresh = findViewById(R.id.swipeRefresh)
        
        recyclerView.layoutManager = LinearLayoutManager(this)

        swipeRefresh.setOnRefreshListener {
            fetchBloggerPosts()
        }

        fetchBloggerPosts()
    }

    private fun fetchBloggerPosts() {
        swipeRefresh.isRefreshing = true
        thread {
            try {
                // max-results=50 se sari posts full aayengi
                val url = "https://www.dilshayarana.com/feeds/posts/default?alt=json&max-results=50"
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

                        // First Image extract from content
                        var imageUrl: String? = null
                        if (content.contains("<img") && content.contains("src=\"")) {
                            val srcStart = content.indexOf("src=\"") + 5
                            val srcEnd = content.indexOf("\"", srcStart)
                            if (srcStart > 4 && srcEnd > srcStart) {
                                imageUrl = content.substring(srcStart, srcEnd)
                            }
                        }

                        postList.add(PostItem(title, content, imageUrl))
                    }
                }

                runOnUiThread {
                    val adapter = PostAdapter(postList)
                    recyclerView.adapter = adapter
                    swipeRefresh.isRefreshing = false
                }
            } catch (e: Exception) {
                e.printStackTrace()
                runOnUiThread {
                    swipeRefresh.isRefreshing = false
                }
            }
        }
    }
}
