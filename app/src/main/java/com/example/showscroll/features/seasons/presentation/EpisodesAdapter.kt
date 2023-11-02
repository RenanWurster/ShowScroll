package com.example.showscroll.features.seasons.presentation

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
import com.example.showscroll.R
import com.example.showscroll.data.Episodes
import com.example.showscroll.features.seasons.presentation.EpisodesAdapter.EpisodesViewHolder
import kotlinx.coroutines.ExperimentalCoroutinesApi

class EpisodesAdapter (private val callback: (Episodes) -> Unit) :
    ListAdapter<Episodes, EpisodesViewHolder>(EpisodesAdapter) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpisodesViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.item_episodes, parent, false)
        return EpisodesViewHolder(view)
    }

    @ExperimentalCoroutinesApi
    override fun onBindViewHolder(holder: EpisodesViewHolder, position: Int) {
        holder.bind(getItem(position), callback)
    }

    class EpisodesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        @SuppressLint("SetTextI18n")
        @ExperimentalCoroutinesApi
        fun bind(data: Episodes, callback: (Episodes) -> Unit) {
            with(itemView) {

                val ivEpisodes = findViewById<ImageView>(R.id.ivEpisode)
                val txtEpisodesName = findViewById<TextView>(R.id.txtEpisodeName)
                txtEpisodesName.text = "Episode:${data.name}"

                ivEpisodes.setOnClickListener {
                    callback.invoke(data)
                }

                data.image?.original?.let { ivEpisodes.load(it) {
                    crossfade(true)
                    transformations(RoundedCornersTransformation(15f))
                } }
            }
        }
    }

    companion object : DiffUtil.ItemCallback<Episodes>() {
        override fun areItemsTheSame(oldItem: Episodes, newItem: Episodes): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Episodes, newItem: Episodes): Boolean {
            return oldItem == newItem
        }
    }
}