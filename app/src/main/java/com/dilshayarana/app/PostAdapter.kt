package com.dilshayarana.app

package com.dilshayarana.app

import android.content.Intent
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class PostAdapter(private val postList: List<PostItem>) :
    RecyclerView.Adapter<PostAdapter.PostViewHolder>() {

    class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitle: TextView = itemView.findViewById(R.id.tvTitle)
        val tvContent: TextView = itemView.findViewById(R.id.tvContent)
        val imgPost: ImageView = itemView.findViewById(R.id.imgPost)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_post, parent, false)
        return PostViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = postList[position]

        val cleanTitle = Html.fromHtml(post.title, Html.FROM_HTML_MODE_LEGACY).toString()
        val cleanContent = Html.fromHtml(post.content, Html.FROM_HTML_MODE_LEGACY).toString().trim()

        holder.tvTitle.text = cleanTitle
        holder.tvContent.text = cleanContent

        if (post.imageUrl.isNotEmpty()) {
            holder.imgPost.visibility = View.VISIBLE
            Glide.with(holder.itemView.context)
                .load(post.imageUrl)
                .into(holder.imgPost)
        } else {
            holder.imgPost.visibility = View.GONE
        }

        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, DetailActivity::class.java).apply {
                putExtra("title", post.title)
                putExtra("content", post.content)
                putExtra("imageUrl", post.imageUrl)
            }
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = postList.size
}
