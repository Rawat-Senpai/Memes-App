package com.example.kotlin_meme

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var imageUrl:String=""
    var previousUrl:String=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        nextMeme()

        next.setOnClickListener(){
            nextMeme()
        }
        previous.setOnClickListener(){
            previousMeme()
        }

        share.setOnClickListener(){
            shareMeme()
        }

       // nextMeme()

    }

    private fun shareMeme() {
        val sendIntent: Intent= Intent().apply {
            action= Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT,imageUrl)
            type="text/plain"
        }
        val shareIntent= Intent.createChooser(sendIntent,null)
        startActivity(shareIntent)
    }

    private fun previousMeme() {
        Glide.with(this).load(previousUrl).into(imageView)
    }

    private fun nextMeme() {

        val queue= Volley.newRequestQueue(this)
        previousUrl=imageUrl

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, "https://meme-api.herokuapp.com/gimme", null,
            { response ->

                imageUrl=response.getString("url")
                    Glide.with(this).load(imageUrl).into(imageView)

            },
            { error ->
                Toast.makeText(this,"error ",Toast.LENGTH_SHORT).show()
            }
        )
        queue.add(jsonObjectRequest)
    }
}