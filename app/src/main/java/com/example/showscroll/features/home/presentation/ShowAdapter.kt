package com.example.showscroll.features.home.presentation

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
import com.example.showscroll.data.Series
import com.example.showscroll.features.home.presentation.ShowAdapter.SeriesViewHolder
import kotlinx.coroutines.ExperimentalCoroutinesApi

class ShowAdapter(private val callback: (Series) -> Unit) :
    ListAdapter<Series, SeriesViewHolder>(SeriesDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeriesViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_serie, parent, false)
        return SeriesViewHolder(view)
    }

    @ExperimentalCoroutinesApi
    override fun onBindViewHolder(holder: SeriesViewHolder, position: Int) {
        holder.bind(getItem(position), callback)
    }

    class SeriesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val seriesImage: ImageView? = itemView.findViewById(R.id.ivSerie)
        private val ratingStar: ImageView? = itemView.findViewById(R.id.imgRatingStar)
        private val seriesText: TextView? = itemView.findViewById(R.id.txtSerie)
        private val seriesRating: TextView? = itemView.findViewById(R.id.txtRating)
        private val seriesGenres: TextView? = itemView.findViewById(R.id.txtGenres)

        @ExperimentalCoroutinesApi
        fun bind(data: Series, callback: (Series) -> Unit) {
            seriesText?.text = data.name
            seriesRating?.text = data.rating.average.toString()

            val genresList = data.genres.toList() // convert to list
            val formattedGenres = genresList.joinToString(", ") // format list in String

            seriesGenres?.text = formattedGenres

            ratingStar?.visibility = View.VISIBLE

            itemView.setOnClickListener {
                callback.invoke(data)
            }

            seriesImage?.let {
                data.image?.original?.let { imageUrl ->
                    it.load(imageUrl) {
                        crossfade(true)
                        transformations(RoundedCornersTransformation(15f))
                    }
                }
            }
        }
    }

    private object SeriesDiffCallback : DiffUtil.ItemCallback<Series>() {
        override fun areItemsTheSame(oldItem: Series, newItem: Series): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Series, newItem: Series): Boolean {
            return oldItem == newItem
        }
    }
}