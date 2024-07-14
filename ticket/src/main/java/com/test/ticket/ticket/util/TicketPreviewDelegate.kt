package com.test.ticket.ticket.util

import android.view.View
import android.widget.TextView
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
        priceLine.text = item.price.value.toString()
        timesLine.text = item.time_range.joinToString { " " }

        when(it.indexOf(item)){
            0 -> colorCard.setBackgroundColor(getColor(com.test.core_ui.R.color.fire_action))
            1 -> colorCard.setBackgroundColor(getColor(com.test.core_ui.R.color.globus_action))
            2 -> colorCard.setBackgroundColor(getColor(com.test.core_ui.R.color.white))
        }
    }

}