package com.xiaoxiaoying.recyclerarrayadapter.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.xiaoxiaoying.recyclerarrayadapter.listener.OnLoadNextListener
import com.xiaoxiaoying.recyclerarrayadapter.widget.LoadingFooter
import com.xiaoxiaoying.recyclerarrayadapter.widget.SimpleFooter

/**
 * create by xiaoxiaoying on 2019-07-01
 * @author xiaoxiaoying
 */
abstract class PageAdapter<T>(
    context: Context,
    @LayoutRes private val resource: Int = 0
) : ArrayAdapter<T, ArrayAdapter.ViewHolder<T>>(context, resource) {

    companion object {

        const val TYPE_HEADER = Int.MIN_VALUE//头部--支持头部增加一个headerView
        const val TYPE_FOOTER = Int.MIN_VALUE + 1//底部--往往是loading_more
        private const val TYPE_LAYOUT_MANAGER_STAGGER = 4//代码item展示模式是网格模式
        private const val TYPE_LAYOUT_MANAGER_NORMAL = 3
    }

    var hasHeaderView = false
    var hasFooterView = true
    private var mLayoutManagerType = TYPE_LAYOUT_MANAGER_NORMAL
    private var mHeadView: View? = null
    var onLoadNextListener: OnLoadNextListener? = null
    var mFooterView: LoadingFooter = SimpleFooter(context)
        set(value) {
            if (field == value)
                return
            field = value
            notifyItemRangeChanged(itemCount - 1, itemCount)
        }

    override fun getView(parent: ViewGroup, viewType: Int): View {
        return when (viewType) {
            TYPE_HEADER -> mHeadView!!
            TYPE_FOOTER -> mFooterView.getFooterView()
            else -> super.getView(parent, viewType)
        }
    }

    /**
     * 添加headView
     */
    fun addHeadView(view: View) {
        if (mHeadView == view)
            return

        hasHeaderView = true
        mHeadView = view
        notifyItemRangeInserted(0, 1)
    }

    /**
     * 移除头部view
     */
    fun removeHeadView() {

        if (!hasHeaderView || mHeadView == null)
            return

        hasHeaderView = false
        mHeadView = null
        notifyItemRemoved(0)
    }

    /**
     * @param footer 加载更多的视图
     * @see [SimpleFooter]
     */
    fun setFooterView(footer: LoadingFooter) {
        if (mFooterView == footer)
            return

        this.mFooterView = footer
        notifyItemRangeChanged(itemCount - 1, itemCount)
    }

    /**
     * 不显示尾部 默认是显示的
     */
    fun removeFootView() {
        if (!hasFooterView)
            return
        hasFooterView = false
        notifyItemRemoved(itemCount - 1)
    }

    fun setState(state: LoadingFooter.State) {
        mFooterView.setState(state)
    }

    fun getState(): LoadingFooter.State = mFooterView.getState()

    fun getLoadingFooter(): LoadingFooter = mFooterView
    override fun getItem(position: Int): T? {

        val headerPosition = 0
        val footerPosition = itemCount - 1
        return when {
            position == headerPosition && hasHeaderView -> null

            position == footerPosition && hasFooterView -> null

            else -> super.getItem(if (hasHeaderView) position - 1 else position)
        }

    }


    override fun getViewHolder(
        itemView: View,
        parent: ViewGroup,
        viewType: Int
    ): ArrayAdapter.ViewHolder<T> {
        return when (viewType) {

            TYPE_HEADER -> HeadView(mHeadView!!)

            TYPE_FOOTER -> {
                val footerView = FooterView<T>(mFooterView.getFooterView())
                footerView.itemView.layoutParams =
                    ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                    )
                footerView.itemView.setOnClickListener {
                    if (mFooterView.getState() == LoadingFooter.State.Error)
                        onLoadNextListener?.onLoadNext()

                }

                footerView
            }

            else -> getPagerViewHolder(itemView, parent, viewType)
        }
    }

    open fun getPagerViewHolder(
        itemView: View,
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder<T> = ViewHolder(itemView)


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
        return when {
            position == headerPosition && hasHeaderView -> TYPE_HEADER

            position == footerPosition && hasFooterView -> TYPE_FOOTER

            else -> getItemType(position)
        }
    }

    override fun getCount(): Int {
        var count = super.getCount()
        if (hasFooterView)
            count += 1
        if (hasHeaderView)
            count += 1
        return count
    }

    open fun getItemType(position: Int): Int =
        TYPE_NORMAL

    open class ViewHolder<T>(itemView: View) : ArrayAdapter.ViewHolder<T>(itemView)

    class HeadView<T>(itemView: View) : ArrayAdapter.ViewHolder<T>(itemView)

    class FooterView<T>(itemView: View) : ArrayAdapter.ViewHolder<T>(itemView)

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
                ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
        }
    }

}