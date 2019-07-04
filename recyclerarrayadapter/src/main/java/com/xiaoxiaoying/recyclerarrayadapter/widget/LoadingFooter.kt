package com.xiaoxiaoying.recyclerarrayadapter.widget

import android.view.View

/**
 * create by xiaoxiaoying on 2019-07-02
 * @author xiaoxiaoying
 */
abstract class LoadingFooter(


) {


    enum class State {
        Idle,
        TheEnd,
        Loading,
        Error
    }


    /**
     * 设置加载没有更多数据提醒
     */
    open fun setTheEndText(mTheEnd: String) {}

    /**
     * 加载出错提醒
     */
    open fun setErrorText(mError: String) {}

    /**
     * 加载中提示
     */
    open fun setHint(mHint: String) {}

    abstract fun getState(): State


    open fun setState(state: State, delay: Long) {}

    open fun setState(state: State) {}
    abstract fun getFooterView(): View
}