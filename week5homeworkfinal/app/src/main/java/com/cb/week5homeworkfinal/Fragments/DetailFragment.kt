package com.cb.week5homeworkfinal

import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.cb.week5homeworkfinal.databinding.FragmentDetailBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import androidx.work.*
import com.bumptech.glide.Glide
import com.cb.week5homeworkfinal.Workers.DownloadWorker
import com.cb.week5homeworkfinal.Workers.FileClearWorker
import com.cb.week5homeworkfinal.Workers.SepiaFilterWorker
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private lateinit var binding: FragmentDetailBinding
    private val args by navArgs<DetailFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvSourceId.text = args.article.source.name
        binding.tvSourceDescription.text = args.article.description
        binding.tvSourceUrl.text = args.article.source.url
        binding.tvArticleAuthor.text = args.article.author
        binding.tvArticleDescription.text = args.article.url
        binding.tvArticlePublishedAt.text = args.article.publishedAt
        binding.tvArticleContent.text = args.article.content
        binding.tvArticleTitle.text = args.article.title
        args.article.urlToImage?.let { downloadImage() }
    }


    private fun downloadImage() {
        val constraints = Constraints.Builder()
            .setRequiresBatteryNotLow(true)
            .setRequiresStorageNotLow(true)
            .setRequiredNetworkType(NetworkType.NOT_ROAMING)
            .build()
        val clearFilesWorker = OneTimeWorkRequestBuilder<FileClearWorker>()
            .build()
        val downloadRequest = OneTimeWorkRequestBuilder<DownloadWorker>()
            .setInputData(workDataOf("image_path" to args.article.urlToImage))
            .setConstraints(constraints)
            .build()
//        val sepiaFilterWorker = OneTimeWorkRequestBuilder<SepiaFilterWorker>()
//            .setConstraints(constraints)
//            .build()
//
//        val downloadImageWorker = OneTimeWorkRequestBuilder<DownloadWorker>()
//            .setConstraints(constraints)
//            .build()


        val workManager = context?.let { WorkManager.getInstance(it) }
        workManager
            ?.beginWith(clearFilesWorker)
            ?.then(downloadRequest)
            ?.enqueue()

        workManager?.getWorkInfoByIdLiveData(downloadRequest.id)
            ?.observe(viewLifecycleOwner) { info ->
                GlobalScope.launch(Dispatchers.IO) {
                    if (info.state.isFinished) {

                        val imagePath = info.outputData.getString("image_path")

                        if (imagePath != null) {
                            displayImage(imagePath)
                        }
                    }
                }
            }
    }

    private fun displayImage(imagePath: String) {
        Log.d("DisplayImage","Attempted to display image")
        GlobalScope.launch(Dispatchers.Main) {
            val bitmap = loadImageFromFile(imagePath)
            binding.imageView.setImageBitmap(bitmap)
        }
    }

    private suspend fun loadImageFromFile(imagePath: String) = withContext(Dispatchers.IO) {
        BitmapFactory.decodeFile(imagePath)
    }
}