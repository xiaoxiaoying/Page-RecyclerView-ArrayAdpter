package com.xiaoxiaoying.recyclerarrayadapter.demo

import android.graphics.Rect
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xiaoxiaoying.recyclerarrayadapter.adapter.ArrayAdapter
import com.xiaoxiaoying.recyclerarrayadapter.demo.adapter.DetailsAdapter
import com.xiaoxiaoying.recyclerarrayadapter.demo.adapter.MainAdapter
import com.xiaoxiaoying.recyclerarrayadapter.demo.databinding.ActivityDetailsBinding
import com.xiaoxiaoying.recyclerarrayadapter.listener.OnLoadNextListener
import com.xiaoxiaoying.recyclerarrayadapter.manager.ViewPagerLayoutManager
import com.xiaoxiaoying.recyclerarrayadapter.widget.LoadingFooter

/**
 * create by xiaoxiaoying on 2019-07-04
 * @author xiaoxiaoying
 */
class DetailsActivity : AppCompatActivity(), OnLoadNextListener {
    override fun onLoadNext() {

        setData()
    }

    private var adapter: ArrayAdapter<String, *>? = null
    private var page = 1
    private var binding: ActivityDetailsBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        val bundle = savedInstanceState ?: intent.extras ?: return

        val array = resources.getStringArray(R.array.list_arr)
        val item = bundle.getString("item", array[0])
        title = item
        actionBar?.title = item
        binding?.root?.layoutManager = GridLayoutManager(this, 2)
        when (array.indexOf(item)) {
            2 -> {
                adapter = MainAdapter(this, true)
                binding?.root?.layoutManager = ViewPagerLayoutManager(this)
                setData()
            }
            1 -> {
                adapter = DetailsAdapter(this)
                val text = TextView(this)
                text.layoutParams =
                    ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        SizeUtils.dip2px(this, 80F)
                    )
                text.gravity = Gravity.CENTER
                text.text = "THIS IS HEAD VIEW"
                (adapter as DetailsAdapter).addHeadView(text)
//                (adapter as DetailsAdapter).removeFootView()
                setData(true)
            }

            0 -> {
                adapter = DetailsAdapter(this)
                (adapter as DetailsAdapter).onLoadNextListener = this
                setData()

            }

        }
        val div = SizeUtils.dip2px(this, 10f)
        binding?.root?.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(
                outRect: Rect,
                view: View,
                parent: RecyclerView,
                state: RecyclerView.State
            ) {
                outRect.set(div, div, div, 0)
            }
        })
        binding?.root?.adapter = adapter

    }

    private fun setData() {
        setData(false)
    }

    private fun setData(isParent: Boolean) {


        ((page - 1) * 60 until page * 60).forEach {
            adapter?.add(it.toString())
        }

        if (isParent) {
//            pageRecycler.setItemLayoutMatchParent(2)
            binding?.root?.addItemLayoutMatchParent(1, 5, 6)
        }
        if (adapter is DetailsAdapter)
            (adapter as DetailsAdapter).setState(LoadingFooter.State.Idle)
        if (page == 3)
            (adapter as DetailsAdapter).setState(LoadingFooter.State.TheEnd)
        page++
    }

}