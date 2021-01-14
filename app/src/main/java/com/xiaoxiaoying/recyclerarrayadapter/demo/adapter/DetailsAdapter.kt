package com.xiaoxiaoying.recyclerarrayadapter.demo.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import com.xiaoxiaoying.recyclerarrayadapter.adapter.PageAdapter
import com.xiaoxiaoying.recyclerarrayadapter.demo.R
import kotlinx.android.synthetic.main.item_main.view.*

/**
 * create by xiaoxiaoying on 2019-07-04
 * @author xiaoxiaoying
 */
class DetailsAdapter(context: Context) : PageAdapter<String>(context) {

    override fun getItemResourceId(viewType: Int): Int = R.layout.item_main

    override fun getPagerViewHolder(
        itemView: View,
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder<String> {
        return Holder(itemView)
    }


    class Holder(itemView: View) : PageAdapter.ViewHolder<String>(itemView) {

        override fun onBind(position: Int, viewType: Int, t: String?, payloads: MutableList<Any>) {
            super.onBind(position, viewType, t, payloads)
            itemView.apply {
                txt.text = t
            }
        }

    }
}