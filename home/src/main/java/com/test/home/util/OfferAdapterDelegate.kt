package com.test.home.util

import android.widget.ImageView
import android.widget.TextView
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegate
import com.test.home.R
import com.test.home.domain.model.Offer
import com.test.home.domain.model.Suggest

fun offerAdapterDelegate() = adapterDelegate<Offer, Offer>(R.layout.item_music_post) {
    val name: TextView = findViewById(R.id.name)
    val city: TextView = findViewById(R.id.city)
    val price: TextView = findViewById(R.id.price)
    val image: ImageView = findViewById(R.id.image_music)

    bind { diffPayloads ->
        name.text = item.title
        city.text = item.town
        price.text = item.price.value.toString()
        price.text = item.price.value.toString()

        when(item.id){
            1L -> image.setImageDrawable(getDrawable(R.drawable.music_1))
            2L -> image.setImageDrawable(getDrawable(R.drawable.music_2))
            3L -> image.setImageDrawable(getDrawable(R.drawable.music_3))
        }
    }
}

fun suggestAdapterDelegate() = adapterDelegate<Suggest, Suggest>(R.layout.item_suggest_town) {
    val city: TextView = findViewById(R.id.town_title)
    val description: TextView = findViewById(R.id.town_description)
    val image: ImageView = findViewById(R.id.photo_town)

    bind { diffPayloads ->
        city.text = item.title
        description.text = item.description

        when(item.id){
            1L -> image.setImageDrawable(getDrawable(R.drawable.suggest_1))
            2L -> image.setImageDrawable(getDrawable(R.drawable.suggest_2))
            3L -> image.setImageDrawable(getDrawable(R.drawable.suggest_3))
        }
    }
}