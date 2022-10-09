package com.cb.week5homeworkfinal.Fragments

import android.net.ConnectivityManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
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
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ArticlesFragmentList : Fragment() {

    private lateinit var binding : FragmentArticlesListBinding
    private val networkStatusChecker by lazy {
        NetworkStatusChecker(activity?.getSystemService(ConnectivityManager::class.java))
    }
    private val viewModel: myViewModel by viewModels{
        myViewModel.Factory(newsRepo = App.newsRepo)
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

        val TextListener = object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let{ searchQuery ->
                    viewModel.searchArticles(searchQuery)
                }
                return true
            }
        }

        binding.searchView.setOnQueryTextListener(TextListener)
    }

    private fun getArticles(){
        binding.srLayout.isRefreshing = true
        viewModel.articles.observe(viewLifecycleOwner) { articleResult ->
            when(articleResult){
                is com.cb.week5homeworkfinal.ModelData.Result.Success -> {
                    setArticles(articleResult.value)
                }
                is com.cb.week5homeworkfinal.ModelData.Result.Failure -> {
                    failureDialog()
                }
            }
            binding.srLayout.isRefreshing = false
        }
    }

    private fun setArticles(articles: List<Article>){

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

    private fun failureDialog(){
        val dialogTitle = "Error:"
        val dialogMessage = com.cb.week5homeworkfinal.ModelData.Result.Failure(Exception("No data")).toString()
        val builder = activity?.let { AlertDialog.Builder(it) }

        builder?.setTitle(dialogTitle)
        builder?.setMessage(dialogMessage)
        builder?.setPositiveButton("Ok") {dialog, _ ->
            dialog.dismiss()
        }
        builder?.create()?.show()
        binding.ivNoInternet.visibility = View.VISIBLE
    }

    private fun swipeToRefresh(){
        val swipe : SwipeRefreshLayout = binding.srLayout
        swipe.setOnRefreshListener {
            getArticles()
            swipe.isRefreshing = false
        }
    }
}