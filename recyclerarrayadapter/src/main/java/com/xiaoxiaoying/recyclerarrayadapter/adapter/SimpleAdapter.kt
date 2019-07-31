package com.xiaoxiaoying.recyclerarrayadapter.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.xiaoxiaoying.recyclerarrayadapter.adapter.SimpleAdapter.*
import java.lang.NullPointerException

/**
 * create by xiaoxiaoying on 2019-07-01
 * @author xiaoxiaoying
 */
abstract class SimpleAdapter<T>(
    context: Context,
    @LayoutRes resource: Int = 0
) : ArrayAdapter<T, ViewHolder>(context, resource) {

    override fun getViewHolder(itemView: View?, parent: ViewGroup?, viewType: Int): ViewHolder {
        if (itemView == null) throw NullPointerException("resource not font")
        return ViewHolder(itemView)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}