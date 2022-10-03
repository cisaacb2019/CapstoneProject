package com.cb.week5homeworkfinal.Fragments

import android.net.ConnectivityManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.navigation.fragment.findNavController
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.cb.week5homeworkfinal.ModelData.Article
import com.cb.week5homeworkfinal.ModelData.NewsResponse
import com.cb.week5homeworkfinal.ModelData.Result
import com.cb.week5homeworkfinal.R
import com.cb.week5homeworkfinal.Remote.NetworkStatusChecker
import com.cb.week5homeworkfinal.Remote.buildAPIService
import com.cb.week5homeworkfinal.Remote.myViewModel
import com.cb.week5homeworkfinal.adapters.NewsRecyclerAdapter
import com.cb.week5homeworkfinal.databinding.FragmentArticlesListBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ArticlesFragmentList : Fragment() {

    private lateinit var binding: FragmentArticlesListBinding
    private val networkStatusChecker by lazy {
        NetworkStatusChecker(activity?.getSystemService(ConnectivityManager::class.java))
    }
    private val ViewModel: myViewModel by viewModels {
        myViewModel.ModelFactory(newsService = buildAPIService())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentArticlesListBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getArticles()
        swipeToRefresh()
    }

    private fun getArticles() {
        networkStatusChecker.performIfConnectedToInternet {
            binding.srLayout.isRefreshing = true
            ViewModel.articleLiveData.observe(viewLifecycleOwner) { articleResult ->
                when (articleResult) {
                    is Result.Success -> {
                        setArticles(articleResult.value.articles)
                    }
                    is Result.Failure -> {
                        //todo display a dialog to show failur//
                    }
                }
            }
            binding.srLayout.isRefreshing = false
        }
        if (!networkStatusChecker.hasInternetConnection()) {
            noInternet()
        } else {
            binding.rvArticles.visibility = View.VISIBLE
            binding.ivNoInternet.visibility = View.GONE
            binding.tvNoInternet.visibility = View.GONE
        }
    }

    private fun setArticles(articles: List<Article>) {
        val articleAdapter = NewsRecyclerAdapter(articles) { article ->
            val direction =
                ArticlesFragmentListDirections.actionArticlesFragmentListToDetailFragment(
                    article
                )
            findNavController().navigate(direction)
        }
        binding.rvArticles.run {
            adapter = articleAdapter
        }
    }


    private fun noInternet() {

        binding.rvArticles.visibility = View.GONE
        binding.ivNoInternet.visibility = View.VISIBLE
        binding.tvNoInternet.visibility = View.VISIBLE
    }

    private fun swipeToRefresh() {
        val swipe: SwipeRefreshLayout = binding.srLayout
        swipe.setOnRefreshListener {
            getArticles()
            swipe.isRefreshing = false
        }
    }
}