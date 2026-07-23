package com.dilshayarana.app

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

// Post ka Data Model (Agar alag file nahi hai to ye kaam karega)
data class PostItem(
    val title: String,
    val content: String
)

class PostAdapter(private val postList: List<PostItem>) : RecyclerView.Adapter<PostAdapter.PostViewHolder>() {

    class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgThumbnail: ImageView = itemView.findViewById(R.id.imgThumbnail)
        val tvTitle: TextView = itemView.findViewById(R.id.tvTitle)
        val tvSnippet: TextView = itemView.findViewById(R.id.tvSnippet)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_post, parent, false)
        return PostViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = postList[position]

        // 1. Title Set Karein
        holder.tvTitle.text = post.title

        // 2. Sirf 100 akshar ka chota snippet dikhayein
        holder.tvSnippet.text = BloggerUtils.extractSnippet(post.content)

        // 3. Image nikal kar load karein
        val imageUrl = BloggerUtils.extractImageUrl(post.content)
        if (!imageUrl.isNullOrEmpty()) {
            holder.imgThumbnail.visibility = View.VISIBLE
            Glide.with(holder.itemView.context)
                .load(imageUrl)
                .into(holder.imgThumbnail)
        } else {
            holder.imgThumbnail.visibility = View.GONE
        }

        // 4. Card Click -> Detail Page Kholein
        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, DetailActivity::class.java).apply {
                putExtra("EXTRA_TITLE", post.title)
                putExtra("EXTRA_CONTENT", post.content)
                putExtra("EXTRA_IMAGE", imageUrl)
            }
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = postList.size
}
