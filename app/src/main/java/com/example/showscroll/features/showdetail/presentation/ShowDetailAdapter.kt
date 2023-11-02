package com.example.showscroll.features.showdetail.presentation

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.example.showscroll.data.Seasons
import com.example.showscroll.R
import com.example.showscroll.features.showdetail.presentation.ShowDetailAdapter.SeasonsViewHolder
import kotlinx.coroutines.ExperimentalCoroutinesApi

class ShowDetailAdapter(private val callback: (Seasons) -> Unit) :
    ListAdapter<Seasons, SeasonsViewHolder>(ShowDetailAdapter) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeasonsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.item_seasons, parent, false)
        return SeasonsViewHolder(view)
    }

    @ExperimentalCoroutinesApi
    override fun onBindViewHolder(holder: SeasonsViewHolder, position: Int) {
        holder.bind(getItem(position), callback)
    }

    class SeasonsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        @SuppressLint("SetTextI18n")
        @ExperimentalCoroutinesApi
        fun bind(data: Seasons, callback: (Seasons) -> Unit) {
            with(itemView) {

                val ivSeasons = findViewById<ImageView>(R.id.ivSeason)
                val txtEpisodeNumber = findViewById<TextView>(R.id.txtEpisodesNumber)
                txtEpisodeNumber.text = "Season:${data.number}     Episodes: ${data.episodeOrder}"

                ivSeasons.setOnClickListener {
                    callback.invoke(data)
                }

                data.image?.original?.let { ivSeasons.load(it) {
                    crossfade(true)
                    transformations(RoundedCornersTransformation(15f))
                } }
            }
        }
    }

    companion object : DiffUtil.ItemCallback<Seasons>() {
        override fun areItemsTheSame(oldItem: Seasons, newItem: Seasons): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Seasons, newItem: Seasons): Boolean {
            return oldItem == newItem
        }

    }
}