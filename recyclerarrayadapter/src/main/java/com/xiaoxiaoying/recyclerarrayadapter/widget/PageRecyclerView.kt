package com.xiaoxiaoying.recyclerarrayadapter.widget

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.xiaoxiaoying.recyclerarrayadapter.adapter.PageAdapter
import java.util.ArrayList
import kotlin.math.max

/**
 * create by xiaoxiaoying on 2019-07-03
 * @author xiaoxiaoying
 */
class PageRecyclerView(context: Context, attrs: AttributeSet?, defStyle: Int = 0) :
    RecyclerView(context, attrs, defStyle) {

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    /**
     * 通栏集合
     */
    private val indexItemMatch = ArrayList<Int>()

    init {

        addOnScrollListener(object : OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (adapter == null || adapter !is PageAdapter<*>) return

                val footer = (adapter as PageAdapter<*>).getLoadingFooter()

                val state = footer.getState()

                if (state == LoadingFooter.State.Loading || state == LoadingFooter.State.TheEnd)
                    return

                val itemCount = adapter!!.itemCount
                val lastPosition = getLastVisiblePosition()
                val hasScrolled = if (isVertical())
                    dy != 0 else dx != 0
                if (itemCount == lastPosition + 1 && hasScrolled) {
                    footer.setState(LoadingFooter.State.Loading)
                    (adapter as PageAdapter<*>).onLoadNextCall()

                }


            }
        })
    }

    private fun isVertical(): Boolean {
        return when (val manager = layoutManager) {
            is GridLayoutManager -> manager.orientation
            is LinearLayoutManager -> manager.orientation

            is StaggeredGridLayoutManager -> manager.orientation
            else -> 1
        } == 1
    }

    /**
     * 获取最后一条展示的位置
     *
     * @return
     */
    private fun getLastVisiblePosition(): Int {
        return when (layoutManager) {
            is GridLayoutManager,
            is LinearLayoutManager -> (layoutManager as GridLayoutManager).findLastVisibleItemPosition()

            is StaggeredGridLayoutManager -> {
                val layoutManager = layoutManager as StaggeredGridLayoutManager?
                val lastPositions =
                    layoutManager!!.findLastVisibleItemPositions(IntArray(layoutManager.spanCount))
                getMaxPosition(lastPositions)
            }

            else -> layoutManager!!.itemCount - 1
        }
    }

    /**
     * 获得最大的位置
     *
     * @param positions
     * @return
     */
    private fun getMaxPosition(positions: IntArray): Int {
        val size = positions.size
        var maxPosition = Integer.MIN_VALUE
        for (i in 0 until size) {
            maxPosition = max(maxPosition, positions[i])
        }
        return maxPosition
    }

    override fun setLayoutManager(layout: LayoutManager?) {
        super.setLayoutManager(layout)
        if (layout == null) return

        when (layout) {
            is GridLayoutManager ->
                layout.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                    override fun getSpanSize(p0: Int): Int {

                        if (isHeader(p0) || isFooter(p0))
                            return layout.spanCount
                        println("size === ${indexItemMatch.size}")
                        if (indexItemMatch.size != 0 && indexItemMatch.contains(if (hasHeader) p0 + 1 else p0)) {
                            println(" p0 == $p0")
                            return layout.spanCount
                        }
                        return 1
                    }

                }

            is StaggeredGridLayoutManager -> layout.gapStrategy =
                StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS
        }


    }


    private fun getLayoutManagerType(): Int = when (layoutManager) {
        is StaggeredGridLayoutManager -> 4
        else -> 3
    }

    private fun isHeader(position: Int): Boolean =
        position == 0 && adapter != null && adapter is PageAdapter<*> && (adapter as PageAdapter<*>).hasHeaderView


    private fun isFooter(position: Int): Boolean =
        if (adapter != null) {
            if (adapter is PageAdapter<*> && (adapter as PageAdapter<*>).hasFooterView)
                position >= adapter!!.itemCount - 1
            else position >= adapter!!.itemCount
        } else false

    private val hasHeader: Boolean =
        adapter != null && adapter is PageAdapter<*> && (adapter as PageAdapter<*>).hasHeaderView


    /**
     * 指定条目通栏
     *
     * @param position item index
     */
    fun setItemLayoutMatchParent(position: Int) {

        if (indexItemMatch.contains(position))
            return

        indexItemMatch.add(position)

        if (adapter == null)
            return
        if (adapter!!.itemCount <= position) return



        adapter?.notifyItemChanged(if (hasHeader) position + 1 else position)


    }

    fun addItemLayoutMatchParent(vararg position: Int) {
        position.forEach {
            if (!indexItemMatch.contains(it)) {
                indexItemMatch.add(it)
                println(" it === $it")
                adapter?.notifyItemChanged(if (hasHeader) it + 1 else it)
            }
        }
    }

    fun addItemLayoutMatchParent(arr: List<Int>) {
        if (indexItemMatch.containsAll(arr))
            return
        indexItemMatch.addAll(arr)
        if (adapter == null)
            return
        arr.forEach {
            if (adapter!!.itemCount <= it) return@forEach
            adapter?.notifyItemChanged(if (hasHeader) it + 1 else it)

        }
    }


}