package com.xiaoxiaoying.recyclerarrayadapter.adapter

import android.content.Context
import com.xiaoxiaoying.recyclerarrayadapter.R
import kotlinx.android.synthetic.main.item_main.view.*

/**
 * create by xiaoxiaoying on 2019-07-04
 * @author xiaoxiaoying
 */
class DetailsAdapter(context: Context) : PageAdapter<String>(context, R.layout.item_main) {
    override fun onBindView(h: ViewHolder, position: Int, t: String?) {
        if (t.isNullOrEmpty()) return
        h.itemView.apply { txt.text = t }
    }
}