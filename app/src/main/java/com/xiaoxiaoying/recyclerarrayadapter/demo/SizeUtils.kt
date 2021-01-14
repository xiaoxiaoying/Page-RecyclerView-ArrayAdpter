package com.xiaoxiaoying.recyclerarrayadapter.demo

import android.content.Context
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.WindowManager

object SizeUtils {

    /**
     * px转dp
     *
     * @param context
     * @param pxValue
     * @return
     */
    fun px2dip(context: Context, pxValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (pxValue / scale + 0.5f).toInt()
    }

    /**
     * dip 转为 px
     *
     */
    fun dip2px(context: Context, dipValue: Float): Int {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dipValue, context.resources.displayMetrics).toInt()
    }

    /**
     * 获得屏幕宽度
     *
     * @param context
     * @return
     */
    fun getScreenWidth(context: Context): Int {
        return screen(context, true)
    }

    /**
     * 获取屏幕高度
     */
    fun getScreenHeght(context: Context): Int {
        return screen(context, false)
    }

    private fun screen(context: Context, isWidth: Boolean): Int {
        val wm = context
                .getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val outMetrics = DisplayMetrics()
        wm.defaultDisplay.getMetrics(outMetrics)
        return if (isWidth) outMetrics.widthPixels else outMetrics.heightPixels
    }

}