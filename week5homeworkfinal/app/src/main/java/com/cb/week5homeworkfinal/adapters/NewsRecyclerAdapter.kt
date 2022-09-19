package com.cb.week5homeworkfinal.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cb.week5homeworkfinal.ModelData.Article

class NewsRecyclerAdapter (
    private val articlelist: List<Article>,
    private val OnNewsClick: (Article) -> Unit,
): RecyclerView.Adapter<NewsViewHolder>(){

//lateinit var clickListener: ArticleViewClickListener
// var ArticleList = articleList
//
//    interface ArticleViewClickListener {
//        fun articleClicked(position:Int)
//    }
//
//    fun setOnclickItemListener(listener: ArticleViewClickListener){
//        clickListener = listener
//    }





    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val articleView = ArticleView(parent.context)
        articleView.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT,
        )
        return NewsViewHolder(articleView)
    }

    override fun getItemCount(): Int {
        return articlelist.size
    }
// override fun onBindViewHolder(holder: NewsViewHolder, position: Int,) {
//    holder.articleAuthorView.text = ArticleList[position].author
//    holder.articleTitleView.text = ArticleList[position].title
//    holder.articleDescView.text = ArticleList[position].description
//
//
// }
override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
    holder.itemView.setOnClickListener {
        OnNewsClick(articlelist[position])
    }
    holder.bindData(articlelist[position])
}



}