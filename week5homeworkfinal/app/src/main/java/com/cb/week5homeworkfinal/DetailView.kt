//package com.cb.week5homeworkfinal
//
//import android.content.Context
//import android.content.Intent
//import androidx.appcompat.app.AppCompatActivity
//import android.os.Bundle
//import android.widget.TextView
//import com.google.android.material.floatingactionbutton.FloatingActionButton
//
//class DetailView : AppCompatActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_detail_view)
//
//        val titleheading: TextView = findViewById(R.id.title_text)
//        val authorheading: TextView = findViewById(R.id.author_text)
//        val publisherheading: TextView = findViewById(R.id.publisher_text)
//        val descriptionheading: TextView = findViewById(R.id.description_text)
//        val contentheading: TextView = findViewById(R.id.content_view)
////        val imageheading: ImageView = findViewById(R.id.image_view)
//
//
//
//        val Bundle: Bundle? = intent.extras
//        val title = Bundle!!.getString("title")
//        val author = Bundle.getString("author")
//        val description = Bundle.getString("description")
//        val content = Bundle.getString("content")
//        val source = Bundle.getString("source")
////        val image = Bundle.getInt("image")
//
//        titleheading.text = title
//        authorheading.text = author
//        publisherheading.text = source
//        descriptionheading.text = description
//        contentheading.text = content
////        imageheading.setImageResource(image)
//
//        val backbutton: FloatingActionButton = findViewById(R.id.back_button)
//        backbutton.setOnClickListener {
//            val intent = Intent(this, MainActivity::class.java)
//            startActivity(intent)
//
//            //make all past save data null//
//            val sharedPreference = getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)
//            var editor = sharedPreference.edit()
//            editor.putString("titles",null)
//            editor.putString("author", null)
//            editor.putString("description", null)
//            editor.putString("content", null)
//            editor.putString("source", null)
////                editor.putString("image",dummyvalues[position].imageUrl)
//
//            editor.commit()
//
//
//
//        }
//
//    }
//}