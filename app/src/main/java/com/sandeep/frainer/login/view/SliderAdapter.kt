package com.sandeep.frainer.login.view

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bumptech.glide.Glide
import com.sandeep.frainer.login.view.SliderAdapter.SliderAdapterVH
import com.smarteist.autoimageslider.SliderViewAdapter
import edu.bu.metcs.myproject.R
import edu.bu.metcs.myproject.login.model.SliderItem
import kotlinx.android.synthetic.main.image_slider_layout_item.view.*
import java.util.*

class SliderAdapter(private val context: Context?) : SliderViewAdapter<SliderAdapterVH>() {
    private var mSliderItems: MutableList<SliderItem> = ArrayList()

    fun addItem(sliderItem: SliderItem?) {
        if (sliderItem != null) {
            mSliderItems.add(sliderItem)
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup): SliderAdapterVH {
        val inflate = LayoutInflater.from(parent.context).inflate(R.layout.image_slider_layout_item, null)
        return SliderAdapterVH(inflate)
    }

    override fun onBindViewHolder(viewHolder: SliderAdapterVH, position: Int) {

        val sliderItem = mSliderItems[position]
        viewHolder.itemView.tv_auto_image_slider.text = sliderItem.description
        viewHolder.itemView.tv_auto_image_slider.textSize = 16f
        viewHolder.itemView.tv_auto_image_slider.setTextColor(Color.WHITE)
        Glide.with(viewHolder.itemView)
                .load(sliderItem.imageUrl)
                .fitCenter()
                .into(viewHolder.itemView.iv_auto_image_slider)
        viewHolder.itemView.setOnClickListener { v: View? -> Toast.makeText(context, "This is item in position $position", Toast.LENGTH_SHORT).show() }
    }

    override fun getCount(): Int {
        //slider view count could be dynamic size
        return mSliderItems.size
    }

    class SliderAdapterVH(itemView: View) : ViewHolder(itemView)
}