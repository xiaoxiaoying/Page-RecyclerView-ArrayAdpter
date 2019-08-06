package com.xiaoxiaoying.recyclerarrayadapter.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.xiaoxiaoying.recyclerarrayadapter.R
import com.xiaoxiaoying.recyclerarrayadapter.listener.OnItemClickListener
import com.xiaoxiaoying.recyclerarrayadapter.listener.OnItemLongClickListener
import java.util.*

/**
 * create by xiaoxiaoying on 2019-06-21
 * @author xiaoxiaoying
 */
abstract class ArrayAdapter<T, H : RecyclerView.ViewHolder>(
    private val context: Context,
    @LayoutRes private val resource: Int = 0,
    private val arrays: MutableList<T>
) : RecyclerView.Adapter<H>() {
    constructor(context: Context) : this(context, 0)
    constructor(context: Context, resource: Int) : this(context, resource, ArrayList<T>())


    companion object {
        private val mLock = Any()

        const val TYPE_NORMAL = 0

    }

    private var inflater: LayoutInflater? = null
    var onItemClickListener: OnItemClickListener<T>? = null
    var onItemLongClickListener: OnItemLongClickListener<T>? = null


    init {
        inflater = LayoutInflater.from(context)
    }

    fun add(t: T) {
        synchronized(mLock) {
            arrays.add(t)
        }
        val index = itemCount
        notifyItemInserted(index - 1)
    }


    fun addAll(collection: Collection<T>) {
        synchronized(mLock) {
            arrays.addAll(collection)
        }
        notifyDataSetChanged()
    }

    fun addAll(vararg objects: T) {
        synchronized(mLock) {
            Collections.addAll(arrays, *objects)
        }
        notifyDataSetChanged()
    }


    fun insert(t: T, index: Int) {

        if (index >= arrays.size) throw IndexOutOfBoundsException("It's arrays size ${arrays.size}")
        synchronized(mLock)
        {
            arrays.add(index, t)
        }
        notifyDataSetChanged()
    }

    /**
     * Removes the specified object from the array.
     *
     * @param t The object to remove.
     */
    fun remove(t: T) {
        synchronized(mLock)
        {
            if (arrays.contains(t))
                arrays.remove(t)
        }

        notifyDataSetChanged()
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

    fun clean() {
        synchronized(mLock)
        {
            arrays.clear()
        }

        notifyDataSetChanged()
    }


    fun getContext(): Context = context

    /**
     * 获取视图
     */
    fun getView(@LayoutRes mResource: Int, parent: ViewGroup): View =
        inflater!!.inflate(mResource, parent, false)

    fun getItem(position: Int): T? = if (position >= arrays.size) null else arrays[position]

    override fun getItemCount(): Int {
        return arrays.size + getCount()
    }

    open fun getCount(): Int = 0

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): H {
        val count = arrays.size
        if (count == 0)
            return getViewHolder(null, p0, p1)
        return getViewHolder(
            if (resource == 0)
                if (getItemResourceId(p1) == 0) null else getView(getItemResourceId(p1), p0)
            else getView(resource, p0), p0, p1
        )
    }

    override fun onBindViewHolder(holder: H, p1: Int) {
        val itemType = getItemViewType(position = p1)

        val item = getItem(p1) ?: return

        holder.itemView.setTag(R.id.itemClickTag, item)

        holder.itemView.setOnClickListener {
            val tag = it.getTag(R.id.itemClickTag) ?: return@setOnClickListener
            val itemT = tag as T
            onItemClickListener?.onItemClick(itemT, it)
        }

        holder.itemView.setOnLongClickListener {
            val tag = it.getTag(R.id.itemClickTag) ?: return@setOnLongClickListener false
            val itemLong = tag as T
            if (onItemLongClickListener != null) return@setOnLongClickListener false
            onItemLongClickListener?.onItemLongClick(itemLong, it)
            true
        }

        onBindView(holder, p1, itemType, item)
    }


    override fun getItemViewType(position: Int): Int =
        TYPE_NORMAL


    abstract fun getViewHolder(itemView: View?, parent: ViewGroup?, viewType: Int): H

    abstract fun onBindView(h: H, position: Int, viewType: Int, t: T?)

    open fun getItemResourceId(position: Int) = 0

}