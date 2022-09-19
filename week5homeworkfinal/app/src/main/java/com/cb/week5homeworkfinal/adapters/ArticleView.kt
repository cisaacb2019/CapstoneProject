package com.cb.week5homeworkfinal.adapters

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.cb.week5homeworkfinal.ModelData.Article
import com.cb.week5homeworkfinal.databinding.ArticleViewBinding

class ArticleView @JvmOverloads constructor  (
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0,
) : ConstraintLayout(context, attrs, defStyleAttr, defStyleRes) {

    private val binding = ArticleViewBinding.inflate(LayoutInflater.from(context), this )

    fun setArticleData(article: Article){
        binding.tvSourceName.text = article.source?.name ?: ""
        binding.tvArticleTitle.text = article.title ?: ""
        binding.tvArticleAuthor.text = article.author ?: ""
        binding.tvArticlePublishedAt.text = article.publishedAt ?: ""
//        setOnDeleteTapped(onDeleteTapped)
    }

//    private fun setOnDeleteTapped(onDeleteTapped: () -> Unit){
//        binding.ibDelete.setOnClickListener {
//            onDeleteTapped()
//        }
//    }
}