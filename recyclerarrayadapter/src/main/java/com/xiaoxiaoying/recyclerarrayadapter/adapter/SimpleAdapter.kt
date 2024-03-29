package com.xiaoxiaoying.recyclerarrayadapter.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import com.xiaoxiaoying.recyclerarrayadapter.adapter.SimpleAdapter.*

/**
 * create by xiaoxiaoying on 2019-07-01
 * @author xiaoxiaoying
 */
abstract class SimpleAdapter<T>(
    context: Context,
    @LayoutRes resource: Int = 0
) : ArrayAdapter<T, ViewHolder<T>>(context, resource) {

    override fun getViewHolder(
        itemView: View,
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder<T> {
        return getSimpleViewHolder(itemView, parent, viewType)
    }

    override fun onBindView(
        holder: ViewHolder<T>,
        position: Int,
        viewType: Int,
        t: T?,
        payloads: MutableList<Any>
    ) {
        holder.onBindViewHolder(position, viewType, t, payloads)
    }

    /**
     * 获取ViewHolder [ViewHolder]
     * @param itemView view视图
     * @param viewType [getItemViewType]
     */
    open fun getSimpleViewHolder(
        itemView: View,
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder<T> =
        ViewHolder(itemView)

    open class ViewHolder<T>(itemView: View) : ArrayAdapter.ViewHolder<T>(itemView) {
        open fun onBindViewHolder(
            position: Int,
            viewType: Int,
            t: T?,
            payloads: MutableList<Any>
        ) {
        }
    }
}