package com.yxl.student_guide.profile.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.yxl.student_guide.databinding.ItemScoreBinding
import com.yxl.student_guide.profile.data.Score

class ScoreAdapter() : RecyclerView.Adapter<ScoreAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemScoreBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(score: Score){
            binding.apply {
                tvScoreId.text = score.id.toString()
                tvScoreName.text = score.name
                tvScoreValue.text = score.value.toString()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemScoreBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if(differ.currentList.isNotEmpty()){
            holder.bind(differ.currentList[position])
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private val differCallBack = object : DiffUtil.ItemCallback<Score>(){
        override fun areItemsTheSame(oldItem: Score, newItem: Score): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Score, newItem: Score): Boolean {
            return oldItem == newItem
        }
    }
    val differ = AsyncListDiffer(this, differCallBack)

}