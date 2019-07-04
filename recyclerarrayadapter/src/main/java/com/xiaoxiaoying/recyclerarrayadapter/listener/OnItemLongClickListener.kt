package com.xiaoxiaoying.recyclerarrayadapter.listener

import android.view.View

/**
 * create by xiaoxiaoying on 2019-06-21
 * @author xiaoxiaoying
 */
interface OnItemLongClickListener<T> {
    fun onItemLongClick(t: T?, view: View)
}