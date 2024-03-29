package com.xiaoxiaoying.recyclerarrayadapter.demo.adapter

import android.content.Context
import com.xiaoxiaoying.recyclerarrayadapter.adapter.ArrayAdapter
import com.xiaoxiaoying.recyclerarrayadapter.adapter.PageAdapter
import com.xiaoxiaoying.recyclerarrayadapter.demo.R
import com.xiaoxiaoying.recyclerarrayadapter.demo.databinding.ItemMainBinding

/**
 * create by xiaoxiaoying on 2019-07-04
 * @author xiaoxiaoying
 */
class DetailsAdapter(context: Context) : PageAdapter<String>(context) {

    override fun getItemResourceId(viewType: Int): Int = R.layout.item_main

    override fun onBindView(
        holder: ArrayAdapter.ViewHolder<String>,
        position: Int,
        viewType: Int,
        t: String?,
        payloads: MutableList<Any>
    ) {

        if (viewType != TYPE_NORMAL)
            return

        holder.itemView.apply {
            val bind = ItemMainBinding.bind(this)
            bind.txt.text = t
        }
    }
}