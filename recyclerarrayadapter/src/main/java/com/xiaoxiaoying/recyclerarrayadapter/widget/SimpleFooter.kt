package com.xiaoxiaoying.recyclerarrayadapter.widget

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.annotation.LayoutRes
import com.xiaoxiaoying.recyclerarrayadapter.R


/**
 * create by xiaoxiaoying on 2019-07-03
 * @author xiaoxiaoying
 */
class SimpleFooter(private val context: Context) : LoadingFooter() {
    override fun getFooterView(): View = mLoadingFooter!!

    @LayoutRes
    private val resource: Int = R.layout.loading_footer
    private var mLoadingFooter: View? = null
    private var mProgressBar: ProgressBar? = null
    private var mHint: TextView? = null
    private var mState: State = State.Idle
    private var mHintStr: String = ""
    private var mTheEnd: String = ""
    private var mError: String = ""

    init {
        mLoadingFooter = LayoutInflater.from(context).inflate(resource, null, false)
        mProgressBar = mLoadingFooter!!.findViewById(R.id.progress)
        mHint = mLoadingFooter!!.findViewById(R.id.hint)
        setTheEndText(context.resources.getString(R.string.footer_the_end))
        setErrorText(context.resources.getString(R.string.footer_err))
        setHint(context.resources.getString(R.string.footer_load))
        setState(State.Idle)
    }

    /**
     * 设置加载没有更多数据提醒
     */
    override fun setTheEndText(mTheEnd: String) {
        this.mTheEnd = mTheEnd
    }

    /**
     * 加载出错提醒
     */
    override fun setErrorText(mError: String) {
        this.mError = mError
    }

    /**
     * 加载中提示
     */
    override fun setHint(mHint: String) {
        this.mHintStr = mHint
    }

    override fun getState(): State = mState


    override fun setState(state: State, delay: Long) {
        mLoadingFooter?.postDelayed({
            setState(state)
        }, delay)
    }

    override fun setState(state: State) {
        mState = state

        when (state) {
            State.TheEnd -> {
                setProgressVisibility(true)
                setHintText(mTheEnd)
            }

            State.Loading -> {
                setHintText(mHintStr)
                setProgressVisibility(false)
            }

            State.Error -> {
                setProgressVisibility(true)
                setHintText(mError)
            }
            else -> {
                setHintText(mHintStr)
                setProgressVisibility(false)
            }
        }

    }

    private fun setProgressVisibility(isGone: Boolean) {
        mProgressBar?.visibility = if (isGone) View.GONE else View.VISIBLE
    }

    private fun setHintText(txt: String) {
        mHint?.text = txt
    }
}