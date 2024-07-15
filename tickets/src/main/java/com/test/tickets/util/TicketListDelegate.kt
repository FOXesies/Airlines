package com.test.tickets.util

import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegate
import com.test.tickets.R
import com.test.tickets.domain.model.Ticket
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit


private val baseFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
private val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
/*
fun ticketListDelegate() = adapterDelegate<Ticket, Ticket>(R.layout.item_ticket){
    val price = findViewById<TextView>(R.id.price_route)

    val startTime = findViewById<TextView>(R.id.start_time)
    val finishTime = findViewById<TextView>(R.id.finish_time)

    val airportFrom = findViewById<TextView>(R.id.airport_from)
    val airportTo = findViewById<TextView>(R.id.airport_to)

    val timeInRoute = findViewById<TextView>(R.id.time_in_route)
    val layHaveTransfer = findViewById<LinearLayout>(R.id.lay_has_transfer)

    bind {
        val startDate = baseFormat.parse(item.arrival.date)
        val finishDate = baseFormat.parse(item.departure.date)

        price.text = "${item.price.value} " + getString(com.test.core_ui.R.string.rubl)

        startTime.text = timeFormat.format(startDate)
        finishTime.text = timeFormat.format(finishDate)

        airportFrom.text = item.arrival.airport
        airportTo.text = item.departure.airport

        if(!item.has_transfer)
            layHaveTransfer.visibility = View.VISIBLE

        timeInRoute.text = getHoursLeft(startDate!!, finishDate)
    }
}
*/

fun ticketListDelegate() = adapterDelegate<Ticket, Ticket>(R.layout.item_ticket_list) {
    val price = findViewById<TextView>(R.id.price_route)
    val startTime = findViewById<TextView>(R.id.start_time)
    val badge = findViewById<CardView>(R.id.card_badge)
    val badgeInfo = findViewById<TextView>(R.id.info_badge)
    val finishTime = findViewById<TextView>(R.id.finish_time)
    val airportFrom = findViewById<TextView>(R.id.airport_from)
    val airportTo = findViewById<TextView>(R.id.airport_to)
    val timeInRoute = findViewById<TextView>(R.id.time_in_route)
    val layHaveTransfer = findViewById<LinearLayout>(R.id.lay_has_transfer)
    val baseFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
    val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

    bind {
        val startDate = baseFormat.parse(item.departure.date)
        val finishDate = baseFormat.parse(item.arrival.date)

        item.badge?.let {
            badgeInfo.text = it
            badge.visibility = View.VISIBLE
        }

        price.text = "${item.price.value} " + getString(com.test.core_ui.R.string.rubl)

        startTime.text = timeFormat.format(startDate)
        finishTime.text = timeFormat.format(finishDate)

        airportFrom.text = item.departure.airport
        airportTo.text = item.arrival.airport

        if (!item.has_transfer) {
            layHaveTransfer.visibility = View.VISIBLE
        }

        timeInRoute.text = startDate?.let { finishDate?.let { it1 -> getHoursLeft(it, it1) } }
    }
}


fun getHoursLeft(startDate: Date, endDate: Date): String {
    val diffInMillis = endDate.time - startDate.time
    val diffInHours = diffInMillis.toDouble() / TimeUnit.MILLISECONDS.convert(1, TimeUnit.HOURS)
    return "%.1fч. в пути".format(diffInHours)
}