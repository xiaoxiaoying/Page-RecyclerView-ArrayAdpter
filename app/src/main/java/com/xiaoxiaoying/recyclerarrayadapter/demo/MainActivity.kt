package com.xiaoxiaoying.recyclerarrayadapter.demo

import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xiaoxiaoying.recyclerarrayadapter.demo.R
import com.xiaoxiaoying.recyclerarrayadapter.demo.adapter.MainAdapter
import com.xiaoxiaoying.recyclerarrayadapter.listener.OnItemClickListener
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), OnItemClickListener<String> {
    override fun onItemClick(t: String?, view: View) {
        if (t.isNullOrEmpty())
            return
        val intent = Intent(this, DetailsActivity::class.java)
        val bundle = Bundle()
        bundle.putString("item", t)
        intent.putExtras(bundle)
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val adapter = MainAdapter(this)
        recycler.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        val div = SizeUtils.dip2px(this, 10f)
        recycler.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(
                outRect: Rect,
                view: View,
                parent: RecyclerView,
                state: RecyclerView.State
            ) {
                outRect.set(div, div, div, 0)
            }
        })
        adapter.onItemClickListener = this
        recycler.adapter = adapter

        val arr = resources.getStringArray(R.array.list_arr)
        arr.forEach {
            adapter.add(it)
        }

        recycler.postDelayed({
            adapter.addAll(*arr)
        }, 1000)
    }
}
