package com.xiaoxiaoying.recyclerarrayadapter.demo.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import com.xiaoxiaoying.recyclerarrayadapter.adapter.SimpleAdapter
import com.xiaoxiaoying.recyclerarrayadapter.demo.R
import kotlinx.android.synthetic.main.item_main.view.*

/**
 * create by xiaoxiaoying on 2019-07-04
 * @author xiaoxiaoying
 */
class MainAdapter(context: Context, private val isPager: Boolean) :
    SimpleAdapter<String>(context, R.layout.item_main) {

    constructor(context: Context) : this(context, false)

    override fun onBindView(
        holder: ViewHolder<String>,
        position: Int,
        viewType: Int,
        t: String?,
        payloads: MutableList<Any>
    ) {
        holder.itemView.apply {

            txt.text = t

        }
    }


}