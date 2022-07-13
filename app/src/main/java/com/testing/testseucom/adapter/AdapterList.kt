package com.testing.testseucom.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.testing.testseucom.databinding.ItemListBinding
import com.testing.testseucom.response.Data

class AdapterList constructor(
    val list: List<Data>,
    val context: Context,
    private val itemAdapterCallback: ItemAdapterCallback
) : RecyclerView.Adapter<AdapterList.MyViewHolder>() {

    var listenerCreate = { _:Int-> }
    var listenerUpdate = {_: Int ->}

    inner class MyViewHolder(
        val binding: ItemListBinding,
        val context: Context
    ) : RecyclerView.ViewHolder(binding.root){
        @SuppressLint("SetTextI18n")
        fun bind(position: Int){

            binding.tvLocName.text = list[position].locName
            binding.tvTypeLabel.text = list[position].locTypeLabel


            binding.ivUpdate.setOnClickListener {
                listenerUpdate.invoke(position)
            }

            itemView.setOnClickListener {
                itemAdapterCallback.onClick(it, list[position])
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemListBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MyViewHolder(binding, parent.context)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(position)
    }

    fun setOnCreateListener(listener:(Int)->Unit){
        this.listenerCreate = listener
    }

    fun setOnUpdateListener(listener: (Int) -> Unit) {
        this.listenerUpdate = listener
    }


    override fun getItemCount(): Int = list.size

    interface ItemAdapterCallback {
        fun onClick(view: View, data: Data)
    }
}