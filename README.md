# RecyclerArrayAdapter

###### `Java 22`

###### `Kotlin 1.9.23`

###### `SDk 34`

###### `Androidx`

* 方便快捷的 `ArrayAdapter`

```Kotlin
    adapter.add(Any)
adapter.add(List<Any>)
```

* 有 `ViewPager` 的特性

```Kotlin
    recyclerView.layoutManager = ViewPagerLayoutManager(context)

```

* 加载更多

1. 使用 `PageRecyclerView` 和 `PageAdapter`
2. 加载出错 `pageAdater.setState(LoadingFooter.State.Error)`
3. 可定制的 `LoadingFooter`

```Kotlin	
	pageAdapter.onLoadNextCall {
	    // 加载更多
	}
	PageRecyclerView.adapter = pageAdapter
```

* 在 `GridLayoutManager` 模式下指定条目通栏

#### 使用

```
implementation 'com.github.xiaoxiaoying:Page-RecyclerView-ArrayAdpter:1.5.5'
```
