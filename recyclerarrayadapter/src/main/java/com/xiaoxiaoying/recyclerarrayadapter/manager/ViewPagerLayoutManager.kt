package com.xiaoxiaoying.recyclerarrayadapter.manager

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.xiaoxiaoying.recyclerarrayadapter.listener.OnPagerNextListener
import com.xiaoxiaoying.recyclerarrayadapter.listener.OnViewPagerListener

/**
 * Create by xiaoxiaoying on 2019-06-17
 * @author xiaoxiaoying
 */
class ViewPagerLayoutManager(context: Context, orientation: Int, reverseLayout: Boolean) :
        LinearLayoutManager(context, orientation, reverseLayout), RecyclerView.OnChildAttachStateChangeListener {

    private var mDrift = 0//位移，用来判断移动方向

    override fun onChildViewDetachedFromWindow(p0: View) {
        if (mDrift >= 0) {
            mOnViewPagerListener?.onPageRelease(true, getPosition(p0))
        } else {
            mOnViewPagerListener?.onPageRelease(false, getPosition(p0))
        }
    }

    override fun onChildViewAttachedToWindow(p0: View) {
        if (mOnViewPagerListener != null && childCount == 1) {
            mOnViewPagerListener?.onInitComplete()
        }
    }

    constructor(context: Context) : this(context, VERTICAL)
    constructor(context: Context, orientation: Int) : this(context, orientation, false)

    private var mOnViewPagerListener: OnViewPagerListener? = null
    private var mPagerSnapHelper: PagerSnapHelper? = null
    private var mRecyclerView: RecyclerView? = null
    private var mOnPagerNextListener: OnPagerNextListener? = null

    init {
        mPagerSnapHelper = PagerSnapHelper()
        mPagerSnapHelper = object : PagerSnapHelper() {
            override fun findTargetSnapPosition(layoutManager: RecyclerView.LayoutManager?, velocityX: Int, velocityY: Int): Int {
                val position = super.findTargetSnapPosition(layoutManager, velocityX, velocityY)
                mOnPagerNextListener?.onNext(position)
                return position
            }
        }
    }

    override fun onAttachedToWindow(view: RecyclerView?) {
        super.onAttachedToWindow(view)
        mPagerSnapHelper?.attachToRecyclerView(view)
        mRecyclerView = view
        mRecyclerView?.addOnChildAttachStateChangeListener(this)
    }

    override fun scrollVerticallyBy(dy: Int, recycler: RecyclerView.Recycler?, state: RecyclerView.State?): Int {
        mDrift = dy

        return super.scrollVerticallyBy(dy, recycler, state)
    }

    override fun scrollHorizontallyBy(dx: Int, recycler: RecyclerView.Recycler?, state: RecyclerView.State?): Int {
        mDrift = dx
        return super.scrollHorizontallyBy(dx, recycler, state)
    }

    /**
     * 滑动监听
     */
    fun setOnViewPagerListener(mOnViewPagerListener: OnViewPagerListener) {
        this.mOnViewPagerListener = mOnViewPagerListener
    }

    /**
     * 添加下一页监听器
     * @param mOnPagerNextListener {@link OnPagerNextListener}
     */
    fun setOnPagerNextListener(mOnPagerNextListener: OnPagerNextListener) {
        this.mOnPagerNextListener = mOnPagerNextListener

    }

    override fun onScrollStateChanged(state: Int) {
        super.onScrollStateChanged(state)
        when (state) {
            RecyclerView.SCROLL_STATE_IDLE -> {
                val viewIdle = mPagerSnapHelper?.findSnapView(this) ?: return
                val positionIdle = getPosition(viewIdle)
                if (mOnViewPagerListener != null && childCount == 1) {
                    mOnViewPagerListener?.onPageSelected(positionIdle, positionIdle == itemCount - 1)
                }
            }
        }
    }
}