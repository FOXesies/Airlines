package com.test.ticket.ticket.util

import android.graphics.drawable.Drawable
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegate
import com.test.ticket.R
import com.test.ticket.ticket.domain.model.TicketPreview

fun ticketPreviewDelegate() = adapterDelegate<TicketPreview, TicketPreview>(R.layout.item_ticket){
    val nameLine = findViewById<TextView>(R.id.name_line)
    val priceLine = findViewById<TextView>(R.id.price_line)
    val timesLine = findViewById<TextView>(R.id.times_line)
    val colorCard = findViewById<View>(R.id.color_card)

    bind {
        nameLine.text = item.title
        priceLine.text = item.price.value.toString() + ContextCompat.getString(context, com.test.core_ui.R.string.rubl)
        timesLine.text = item.time_range.joinToString { time -> "$time " }

        val color = when (adapterPosition % 3) {
            0 -> ContextCompat.getColor(context, com.test.core_ui.R.color.fire_action)
            1 -> ContextCompat.getColor(context, com.test.core_ui.R.color.globus_action)
            else -> ContextCompat.getColor(context, com.test.core_ui.R.color.white)
        }

        val background: Drawable = DrawableCompat.wrap(colorCard.background)
        DrawableCompat.setTint(background, color)
        colorCard.background = background
    }

}