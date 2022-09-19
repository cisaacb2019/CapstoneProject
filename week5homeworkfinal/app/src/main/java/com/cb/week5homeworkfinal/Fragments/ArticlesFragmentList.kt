package com.cb.week5homeworkfinal.Fragments

import android.net.ConnectivityManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.cb.week5homeworkfinal.ModelData.Result
import com.cb.week5homeworkfinal.R
import com.cb.week5homeworkfinal.Remote.NetworkStatusChecker
import com.cb.week5homeworkfinal.adapters.NewsRecyclerAdapter
import com.cb.week5homeworkfinal.databinding.FragmentArticlesListBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ArticlesFragmentList : Fragment() {

    private lateinit var binding : FragmentArticlesListBinding
    private val remoteApi = App.remoteApi
    private val networkStatusChecker by lazy {
        NetworkStatusChecker(activity?.getSystemService(ConnectivityManager::class.java))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        binding = FragmentArticlesListBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setArticles()
    }

   private fun setArticles(){
        networkStatusChecker.performIfConnectedToInternet {
            viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO){
                val result = remoteApi.getArticles()
                withContext(Dispatchers.Main){
                    when (result) {
                        is Result.Success -> {
                            val articleAdapter = NewsRecyclerAdapter(result.data.articles) {
                                val direction =
                                    ArticlesFragmentListDirections.actionArticlesFragmentListToDetailFragment(
                                        it
                                    )
                                findNavController().navigate(direction)
                            }
                            binding.rvArticles.run {
                                adapter = articleAdapter
                                swipeToRefresh()
                            }
                        }
                        is Result.Failure -> {
                            failureDialog()
                        }
                    }
                }
            }
        }

        if (!networkStatusChecker.hasInternetConnection()){
            noInternet()
        }else{
            binding.rvArticles.visibility = View.VISIBLE
            binding.ivNoInternet.visibility = View.GONE
            binding.tvNoInternet.visibility = View.GONE
        }
        swipeToRefresh()
    }

    private fun failureDialog(){
        val dialogTitle = "Sorry"
        val dialogMessage = Result.Failure(Exception("The news articles could not load.")).toString()
        val builder = activity?.let { AlertDialog.Builder(it) }

        builder?.setTitle(dialogTitle)
        builder?.setMessage(dialogMessage)
        builder?.setPositiveButton("Ok") {dialog, _ ->
            dialog.dismiss()
        }
        builder?.create()?.show()
        binding.ivNoInternet.visibility = View.VISIBLE
    }

    private fun noInternet(){

        binding.rvArticles.visibility = View.GONE
        binding.ivNoInternet.visibility = View.VISIBLE
        binding.tvNoInternet.visibility = View.VISIBLE
    }

    private fun swipeToRefresh(){
        val swipe : SwipeRefreshLayout = binding.srLayout
        swipe.setOnRefreshListener {
            setArticles()
            swipe.isRefreshing = false
        }
    }
}