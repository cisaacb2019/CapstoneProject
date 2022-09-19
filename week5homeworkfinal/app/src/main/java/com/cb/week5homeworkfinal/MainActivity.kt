package com.cb.week5homeworkfinal

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cb.week5homeworkfinal.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    //Private var for view binding
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
//    lateinit var NewsRecyclerView: RecyclerView

    //added the enum properties to the source and had to comment out due to week 5 hw assignment
    //you could move the sourcedummyvalues to a class for cleaner code//
//    private val sourcedummyvalues = arrayListOf(
//        Source(
//            "cnn",
//            "CNN",
//            Category.BUSINESS,
//            Language.EN,
//            Country.US,
//
//        ),
//        Source(
//            "the_new_york_times",
//            "TheNewYorkTimes",
//                    Category.BUSINESS,
//            Language.EN,
//            Country.US,
//        ),
//        Source(
//            "",
//            "",
//                    Category.BUSINESS,
//            Language.EN,
//            Country.US,
//        ),
//        Source(
//            "market_watch",
//            "Market Watch",
//                    Category.BUSINESS,
//            Language.EN,
//            Country.US,
//        ),
//        Source(
//            "Morning_Coffee",
//            "Morning Coffee",
//                    Category.BUSINESS,
//            Language.EN,
//            Country.US,
//        ),
//    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        NavigationUI.setupActionBarWithNavController(this, navController)

//        val dummyvalues: List<Article> = (NewsService().getarticles())


//        val sharedPreference = getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)
//        val getTitle = sharedPreference.getString("titles", null)
//        //check to see if there is a save state and load if there is//
//
//        if (getTitle != null) {
//            val intent = Intent(this@MainActivity, DetailView::class.java)
//            intent.putExtra("title",getTitle)
//            intent.putExtra("author", sharedPreference.getString("author", ""))
//            intent.putExtra("description", sharedPreference.getString("description", ""))
//            intent.putExtra("content", sharedPreference.getString("content", ""))
//            intent.putExtra("source", sharedPreference.getString("source", ""))
//            //                intent.putExtra("image",sharedPreference.getString("image","default value"))
//            startActivity(intent)
//
//        } else {


//        dummyvalues.forEach {
//            ArticleViewBinding.inflate(layoutInflater, binding.articleContainer, true).apply {
//
//                articleName.text = it.title
//                articleAuthor.text = it.author
//                articleDescription.text = it.description
//            }
//        }
//            val adapter = NewsRecyclerAdapter(dummyvalues)

//            NewsRecyclerView = findViewById(R.id.NewsRecycleView)
//            NewsRecyclerView.layoutManager = LinearLayoutManager(this)
//            NewsRecyclerView.adapter = adapter




//            adapter.setOnclickItemListener(object : NewsRecyclerAdapter.ArticleViewClickListener {
//                override fun articleClicked(position: Int) {
//                Toast.makeText(this@MainActivity, "You Clickws item no $position", Toast.LENGTH_SHORT).show()

//                    val intent = Intent(this@MainActivity, DetailView::class.java)
//                    intent.putExtra("title", dummyvalues[position].title)
//                    intent.putExtra("author", dummyvalues[position].author)
//                    intent.putExtra("description", dummyvalues[position].description)
//                    intent.putExtra("content", dummyvalues[position].content)
//                    intent.putExtra("source", dummyvalues[position].publishedAt)
////                intent.putExtra("image",dummyvalues[position].imageUrl)
//                    startActivity(intent)
//                    //store shared prefs here
//
//                    var editor = sharedPreference.edit()
//                    editor.putString("titles", dummyvalues[position].title)
//                    editor.putString("author", dummyvalues[position].author)
//                    editor.putString("description", dummyvalues[position].description)
//                    editor.putString("content", dummyvalues[position].content)
//                    editor.putString("source", dummyvalues[position].publishedAt)
//                editor.putString("image",dummyvalues[position].imageUrl)
//
//                    editor.commit()

                }
//            })


        }
//}