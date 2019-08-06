package com.xiaoxiaoying.recyclerarrayadapter.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.xiaoxiaoying.recyclerarrayadapter.listener.OnLoadNextListener
import com.xiaoxiaoying.recyclerarrayadapter.widget.LoadingFooter
import com.xiaoxiaoying.recyclerarrayadapter.widget.SimpleFooter
import java.lang.NullPointerException

/**
 * create by xiaoxiaoying on 2019-07-01
 * @author xiaoxiaoying
 */
abstract class PageAdapter<T>(
    context: Context,
    @LayoutRes private val resource: Int = 0
) : ArrayAdapter<T, PageAdapter.ViewHolder>(context) {

    companion object {
        private const val TYPE_HEADER = 1//头部--支持头部增加一个headerView
        private const val TYPE_FOOTER = 2//底部--往往是loading_more
        private const val TYPE_LAYOUT_MANAGER_STAGGER = 4//代码item展示模式是网格模式
        private const val TYPE_LAYOUT_MANAGER_NORMAL = 3
    }

    var hasHeaderView = false
    var hasFooterView = true
    private var mLayoutManagerType = TYPE_LAYOUT_MANAGER_NORMAL
    private var mHeadView: View? = null
    var onLoadNextListener: OnLoadNextListener? = null
    private var mFooterView: LoadingFooter = SimpleFooter(context)
    /**
     * 添加headView
     */
    fun addHeadView(view: View) {
        hasHeaderView = true
        mHeadView = view
        notifyDataSetChanged()
    }

    /**
     * 移除头部view
     */
    fun removeHeadView() {
        hasHeaderView = false
        mHeadView = null
        notifyDataSetChanged()
    }

    /**
     * @param footer 加载更多的视图
     * @see [SimpleFooter]
     */
    fun setFooterView(footer: LoadingFooter) {
        this.mFooterView = footer
        notifyDataSetChanged()
    }

    /**
     * 不显示尾部 默认是显示的
     */
    fun removeFootView() {
        hasFooterView = false
        notifyItemRemoved(itemCount - 1)
    }

    fun setState(state: LoadingFooter.State) {
        mFooterView.setState(state)
    }

    fun getState(): LoadingFooter.State = mFooterView.getState()

    fun getLoadingFooter(): LoadingFooter = mFooterView


    override fun getViewHolder(itemView: View?, parent: ViewGroup?, viewType: Int): ViewHolder {

        return when (viewType) {

            TYPE_HEADER -> HeadView(
                mHeadView!!
            )

            TYPE_FOOTER -> {
                val footerView = FooterView(
                    mFooterView.getFooterView()
                )
                footerView.itemView.layoutParams =
                    ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                footerView.itemView.setOnClickListener {
                    if (mFooterView.getState() == LoadingFooter.State.Error)
                        onLoadNextListener?.onLoadNext()

                }

                footerView
            }

            else -> {
                if (itemView == null) {
                    throw NullPointerException("view is null")
                }

                ViewHolder(itemView)
            }
        }
    }


    /**
     * 不要重写这个方法
     * 不然会错乱
     * 可以重写
     * [getItemType]
     *  返回的type类型不要和
     *  [TYPE_FOOTER]; [TYPE_HEADER] ;
     *  [ArrayAdapter.TYPE_NORMAL]; 重复
     */

    override fun getItemViewType(position: Int): Int {
        val headerPosition = 0
        val footerPosition = itemCount - 1

        return when (position) {
            headerPosition -> if (hasHeaderView) TYPE_HEADER else getItemType(position)

            footerPosition -> if (hasFooterView) TYPE_FOOTER else getItemType(position)

            else -> getItemType(position)
        }
    }

    override fun getCount(): Int {
        var count = super.getCount()
        if (hasFooterView)
            count++
        if (hasHeaderView)
            count++
        return count
    }

    open fun getItemType(position: Int): Int =
        TYPE_NORMAL

    open class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    class HeadView(itemView: View) : ViewHolder(itemView)
    class FooterView(itemView: View) : ViewHolder(itemView)

    private fun setLayoutParams(view: View) {
        if (mLayoutManagerType == TYPE_LAYOUT_MANAGER_STAGGER) {
            val layoutParams = StaggeredGridLayoutManager.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            layoutParams.isFullSpan = true
            view.layoutParams = layoutParams
        } else {
            view.layoutParams =
                ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        }
    }

}