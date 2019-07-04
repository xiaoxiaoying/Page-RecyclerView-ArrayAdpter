package com.xiaoxiaoying.recyclerarrayadapter.adapter

import android.content.Context
import android.view.ViewGroup
import com.xiaoxiaoying.recyclerarrayadapter.R
import kotlinx.android.synthetic.main.item_main.view.*

/**
 * create by xiaoxiaoying on 2019-07-04
 * @author xiaoxiaoying
 */
class MainAdapter(context: Context, private val isPager: Boolean) : SimpleAdapter<String>(context, R.layout.item_main) {

    constructor(context: Context) : this(context, false)

    override fun onBindView(h: ViewHolder, position: Int, t: String?) {
        if (t.isNullOrEmpty()) return

        h.itemView.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            if (isPager) ViewGroup.LayoutParams.MATCH_PARENT else ViewGroup.LayoutParams.WRAP_CONTENT
        )

        h.itemView.apply {
            txt.text = t
        }
    }

}