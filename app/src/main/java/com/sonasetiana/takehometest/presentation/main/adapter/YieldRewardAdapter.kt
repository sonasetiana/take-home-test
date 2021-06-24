package com.sonasetiana.takehometest.presentation.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sonasetiana.takehometest.R
import com.sonasetiana.takehometest.data.ProductModel
import com.sonasetiana.takehometest.databinding.ItemYealdRewardBinding

class YieldRewardAdapter : RecyclerView.Adapter<YieldRewardAdapter.Holder>(){

    private var items = ArrayList<ProductModel>()

    private var period: String = "1W"

    fun setItems(items: ArrayList<ProductModel>){
        this.items = items
        notifyDataSetChanged()
    }

    fun setPeriod(period: String){
        this.period = period
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val inflater = LayoutInflater.from(parent.context)
        return Holder(ItemYealdRewardBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size
    
    inner class Holder(private val binding: ItemYealdRewardBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(item: ProductModel) = with(binding){
            val cardBgColor = when(layoutPosition){
                0 -> R.color.green_50
                1 -> R.color.violet_50
                else -> R.color.navy_50
            }
            cardTitle.apply {
                setCardBackgroundColor(ContextCompat.getColor(context, cardBgColor))
            }

            imgTitle.apply {
                Glide.with(context).load(item.details.im_avatar).into(this)
            }

            txtTitle.text = item.name

            val childAdapter = YieldRewardChildAdapter()
            rvData.adapter = childAdapter
            childAdapter.setItems(item.details.toList(
                    period = period,
                    isShowTitle = layoutPosition == 0,
                    cardBgColor
            ))

            btnDetail.setOnClickListener {

            }

            btnBuy.setOnClickListener {

            }
        }

    }
}