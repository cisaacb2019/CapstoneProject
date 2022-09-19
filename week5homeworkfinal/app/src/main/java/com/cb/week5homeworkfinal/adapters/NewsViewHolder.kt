package com.cb.week5homeworkfinal.adapters

import androidx.recyclerview.widget.RecyclerView
import com.cb.week5homeworkfinal.ModelData.Article


class NewsViewHolder(private val articleView: ArticleView) : RecyclerView.ViewHolder(articleView)  {
//        var articleAuthorView = itemView.findViewById<TextView>(R.id.articleAuthor)
//        var articleTitleView = itemView.findViewById<TextView>(R.id.articleName)
//        var articleDescView = itemView.findViewById<TextView>(R.id.articleDesc)
//init {
//    itemView.setOnClickListener {
//           listener.articleClicked(absoluteAdapterPosition)
//    }

    fun bindData(article: Article){
        articleView.setArticleData(article)
    }
}