package com.cb.week5homeworkfinal.Fragments

import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.navigation.fragment.findNavController
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.cb.week5homeworkfinal.DataBase.PrefsStore.PrefsStore
import com.cb.week5homeworkfinal.DataBase.Repo.NewsRepo
import com.cb.week5homeworkfinal.ModelData.Article
import com.cb.week5homeworkfinal.ModelData.NewsResponse
import com.cb.week5homeworkfinal.ModelData.Result
import com.cb.week5homeworkfinal.R
import com.cb.week5homeworkfinal.Remote.NetworkStatusChecker
//import com.cb.week5homeworkfinal.Remote.buildAPIService
import com.cb.week5homeworkfinal.Remote.myViewModel
import com.cb.week5homeworkfinal.adapters.NewsRecyclerAdapter
import com.cb.week5homeworkfinal.databinding.FragmentArticlesListBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@AndroidEntryPoint
class ArticlesFragmentList : Fragment() {

    private lateinit var binding : FragmentArticlesListBinding

    @Inject
    lateinit var newsArticleRepo: NewsRepo

    @Inject
    lateinit var prefsStore: PrefsStore

    private val viewModel: myViewModel by viewModels{
        myViewModel.Factory(newsRepo = newsArticleRepo, prefsStore = prefsStore)
    }
    private var myInternetMode = true // set to true by default to require wifi so not to
    //disrupt the users data plan
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
        viewModel.wifiEnabled.observe(viewLifecycleOwner){ toggleInternetActive ->
            myInternetMode = toggleInternetActive

            val defaultMode = if (toggleInternetActive){
                //if data to be used

                //temp use for night mode
                AppCompatDelegate.MODE_NIGHT_YES
            }else{
                //if data to not be used

                //temp use for night mode
                AppCompatDelegate.MODE_NIGHT_NO
            }
            //default data used true
        }
    }

    private fun getArticles(){
        binding.srLayout.isRefreshing = true
        viewModel.articles.observe(viewLifecycleOwner) { articleResult ->
            when(articleResult){
                is com.cb.week5homeworkfinal.ModelData.Result.Success -> {
                    setArticles(articleResult.value)
                    Log.d("RecievedArticles","Articles Recieved")
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
        Log.e("FD","$dialogMessage")
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
            Log.d("SwipeRefreshSuccess","Refreshed successfully")
            swipe.isRefreshing = false
        }
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.internetSwitch) {
            viewModel.toggleinternetMode()
        }
        return true
    }
}