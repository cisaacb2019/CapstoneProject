package com.cb.week5homeworkfinal.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cb.week5homeworkfinal.ModelData.Article

class NewsRecyclerAdapter (
    private val articlelist: List<Article>,
    private val OnNewsClick: (Article) -> Unit,
): RecyclerView.Adapter<NewsViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val articleView = ArticleView(parent.context)
        articleView.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT,
        )
        return NewsViewHolder(articleView)
    }


override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
    holder.itemView.setOnClickListener {
        articlelist[position]?.let { it1 -> OnNewsClick(it1) }
    }
    articlelist[position]?.let { holder.bindData(it) }
}
    override fun getItemCount(): Int {
        return articlelist.size
    }


}