package com.test.home.util

import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegate
import com.test.home.R
import com.test.home.domain.model.Offer
import com.test.home.domain.model.Suggest
import com.test.home.presentation.OnSuggestClickListener

fun offerAdapterDelegate() = adapterDelegate<Offer, Offer>(R.layout.item_music_post) {
    val name: TextView = findViewById(R.id.name)
    val city: TextView = findViewById(R.id.city)
    val price: TextView = findViewById(R.id.price)
    val image: ImageView = findViewById(R.id.image_music)

    bind {
        name.text = item.title
        city.text = item.town
        price.text = "от ${item.price.value} ${getString(com.test.core_ui.R.string.rubl)}"

        when(item.id){
            1L -> image.setImageDrawable(getDrawable(R.drawable.music_1))
            2L -> image.setImageDrawable(getDrawable(R.drawable.music_2))
            3L -> image.setImageDrawable(getDrawable(R.drawable.music_3))
        }
    }
}

fun suggestAdapterDelegate(onClickListener: OnSuggestClickListener) = adapterDelegate<Suggest, Suggest>(R.layout.item_suggest_town) {
    val city: TextView = findViewById(R.id.town_title)
    val description: TextView = findViewById(R.id.town_description)
    val image: ImageView = findViewById(R.id.photo_town)
    val itemView: RelativeLayout = findViewById(R.id.item_suggest)

    bind {

        city.text = item.title
        description.text = item.description

        itemView.setOnClickListener {
            onClickListener.onSuggestClick(item.title)
        }

        when(item.id){
            1L -> image.setImageDrawable(getDrawable(R.drawable.suggest_1))
            2L -> image.setImageDrawable(getDrawable(R.drawable.suggest_2))
            3L -> image.setImageDrawable(getDrawable(R.drawable.suggest_3))
        }
    }
}