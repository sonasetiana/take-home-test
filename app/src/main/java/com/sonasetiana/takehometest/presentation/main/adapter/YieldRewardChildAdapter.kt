package com.sonasetiana.takehometest.presentation.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.sonasetiana.takehometest.data.ProductChildAdapterData
import com.sonasetiana.takehometest.databinding.ItemChildYieldRewardBinding

class YieldRewardChildAdapter : RecyclerView.Adapter<YieldRewardChildAdapter.Holder>(){

    private var items = ArrayList<ProductChildAdapterData>()

    fun setItems(items: ArrayList<ProductChildAdapterData>){
        this.items = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val inflater = LayoutInflater.from(parent.context)
        return Holder(ItemChildYieldRewardBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size
    
    inner class Holder(private val binding: ItemChildYieldRewardBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(item: ProductChildAdapterData) = with(binding){
            cardItem.apply {
                setCardBackgroundColor(ContextCompat.getColor(context, item.bgColor))
            }
            if(item.showTitle){
                txtTitle.apply {
                    visibility = View.VISIBLE
                    text = item.title
                }
            }
            txtData.text = item.data
        }
    }
}