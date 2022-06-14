package com.xiaoxiaoying.recyclerarrayadapter.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.recyclerview.widget.RecyclerView
import com.xiaoxiaoying.recyclerarrayadapter.R
import com.xiaoxiaoying.recyclerarrayadapter.listener.OnItemClickListener
import com.xiaoxiaoying.recyclerarrayadapter.listener.OnItemLongClickListener
import java.util.*

/**
 * create by xiaoxiaoying on 2019-06-21
 * @author xiaoxiaoying
 */
abstract class ArrayAdapter<T, H : ArrayAdapter.ViewHolder<T>>(
    private val context: Context,
    @LayoutRes private val resId: Int = 0,
    private val arrays: MutableList<T>
) : RecyclerView.Adapter<H>() {
    constructor(context: Context) : this(context, 0)
    constructor(context: Context, resource: Int) : this(context, resource, ArrayList<T>())


    companion object {
        private val mLock = Any()

        const val TYPE_NORMAL = 0

    }

    private var inflater = LayoutInflater.from(context)
    var onItemClickListener: OnItemClickListener<T>? = null
    var onItemLongClickListener: OnItemLongClickListener<T>? = null

    fun add(t: T) {
        synchronized(mLock) {
            arrays.add(t)
        }
        val index = itemCount
        notifyItemInserted(index - 1)
    }


    fun addAll(collection: Collection<T>) {
        val oldItemCount = itemCount
        synchronized(mLock) {
            arrays.addAll(collection)
        }

        notifyItemRangeChanged(oldItemCount, collection.size)
    }

    fun addAll(vararg objects: T) {
        val oldItemCount = itemCount
        synchronized(mLock) {

            Collections.addAll(arrays, *objects)
        }
        notifyItemRangeChanged(oldItemCount, objects.size)
    }


    fun insert(t: T, index: Int) {

        if (index > arrays.size) throw IndexOutOfBoundsException("It's arrays size ${arrays.size}")
        synchronized(mLock)
        {
            arrays.add(index, t)
        }
        notifyItemRangeInserted(index, 1)
    }

    /**
     * Removes the specified object from the array.
     *
     * @param t The object to remove.
     */
    fun remove(t: T) {
        val position = arrays.indexOf(t)
        synchronized(mLock)
        {
            if (arrays.contains(t))
                arrays.remove(t)
        }
        if (position > -1)
            notifyItemRemoved(position)
    }

    /**
     * 移除指定的Item
     *
     * @param position
     * if position >= arrays.size throw indexOutOfBoundsException
     */
    fun removeItem(position: Int) {

        if (position >= arrays.size) throw IndexOutOfBoundsException("It's arrays size ${arrays.size}")

        synchronized(mLock)
        {
            arrays.removeAt(position)
        }
        notifyItemRemoved(position)
    }

    /**
     * 移除指定的Item
     * @param count 移除的条目数
     * @param startPosition 移除启始位置
     */
    fun removeItem(count: Int, startPosition: Int) {
        synchronized(mLock)
        {
            for (position in 0 until count) {
                val deletePosition = position + startPosition
                if (arrays.size <= deletePosition) return
                arrays.removeAt(deletePosition)

            }
            notifyItemRangeRemoved(startPosition, count)
        }
    }


    fun getData(): MutableList<T> = arrays

    /**
     * 找到相对应的下标
     */
    fun findItemPosition(item: T): Int = arrays.indexOf(item)

    fun clean() {
        synchronized(mLock)
        {
            val size = itemCount
            arrays.clear()
            notifyItemRangeRemoved(0, size)
        }
    }


    fun getContext(): Context = context


    fun getString(@StringRes res: Int): String = context.resources.getString(res)

    /**
     * 获取视图
     */
    open fun getView(parent: ViewGroup, viewType: Int): View {
        val resourceId = getItemResourceId(viewType)
        if (resourceId == 0 && resId == 0)
            throw NullPointerException("resource id is null")
        return inflater.inflate(if (resourceId != 0) resourceId else resId, parent, false)
    }


    open fun getItem(position: Int): T? = if (position >= arrays.size) null else arrays[position]

    override fun getItemCount(): Int {
        return arrays.size + getCount()
    }

    open fun getCount(): Int = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): H {
        return getViewHolder(getView(parent, viewType), parent, viewType)
    }

    override fun onBindViewHolder(
        holder: H,
        position: Int,
        payloads: MutableList<Any>
    ) {
        val itemType = getItemViewType(position)
        val item = getItem(position)
        holder.onItemClickListener = onItemClickListener
        holder.onItemLongClickListener = onItemLongClickListener
        holder.itemView.setTag(R.id.itemClickTag, item)
        onBindView(holder, position, itemType, item, payloads)
    }


    override fun onBindViewHolder(holder: H, position: Int) {
    }


    abstract fun getViewHolder(itemView: View, parent: ViewGroup, viewType: Int): H

    protected abstract fun onBindView(
        holder: H,
        position: Int,
        viewType: Int,
        t: T?,
        payloads: MutableList<Any>
    )

    /**
     * 根据viewType 获取 View 的资源ID
     * @param viewType
     */
    open fun getItemResourceId(viewType: Int) = 0

    open class ViewHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var onItemClickListener: OnItemClickListener<T>? = null
        var onItemLongClickListener: OnItemLongClickListener<T>? = null

        init {
            itemView.setOnClickListener {
                onItemClickListener?.onItemClick(it.getTag(R.id.itemClickTag) as T?, it)
            }

            itemView.setOnLongClickListener {
                if (onItemLongClickListener == null) {
                    false
                } else {
                    onItemLongClickListener?.onItemLongClick(it.getTag(R.id.itemClickTag) as T?, it)
                    true
                }
            }
        }


    }
}