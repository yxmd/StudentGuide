package com.yxl.student_guide.main.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.decode.SvgDecoder
import coil.load
import com.yxl.student_guide.core.data.Api
import com.yxl.student_guide.core.data.Institute
import com.yxl.student_guide.databinding.ItemInstituteBinding

class ContentAdapter() : RecyclerView.Adapter<ContentAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemInstituteBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(institute: Institute){
            binding.apply {
                if(institute.img != null ){
                    ivPoster.load(Api.BASE_URL + "/"  + institute.img)
                }
                if(institute.logo != null){
                    if(institute.logo.contains("svg")){
                        ivLogo.load(Api.BASE_URL + "/" + institute.logo){
                            decoderFactory { result, options, _ -> SvgDecoder(result.source, options) }
                        }
                    }else{
                        ivLogo.load(Api.BASE_URL + "/" + institute.logo)
                    }

                }
                tvTitle.text = institute.name
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemInstituteBinding.inflate(inflater, parent, false)
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

    private val differCallBack = object : DiffUtil.ItemCallback<Institute>(){
        override fun areItemsTheSame(oldItem: Institute, newItem: Institute): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Institute, newItem: Institute): Boolean {
            return oldItem == newItem
        }
    }
    val differ = AsyncListDiffer(this, differCallBack)

    interface OnClickListener{
        fun onFavClick(position: Int, model: Institute)
        fun onInstituteClick(position: Int, model: Institute)
    }
}