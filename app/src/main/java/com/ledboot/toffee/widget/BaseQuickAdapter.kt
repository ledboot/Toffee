package com.ledboot.toffee.widget;

import android.content.Context
import android.support.annotation.IntRange
import android.support.annotation.LayoutRes
import android.support.v7.widget.RecyclerView.Adapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import java.lang.reflect.Constructor
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Modifier
import java.lang.reflect.ParameterizedType


abstract class BaseQuickAdapter<T, K : BaseViewHolder> : Adapter<K> {

    protected var mData: List<T>? = null
    private var mloadMoreView: LoadMoreView = SimpleLoadMoreView()
    private var mLoadMoreEnable: Boolean = false
    private var mPreLoadNumber = 1
    private var mLoading: Boolean = false

    var mRequestLoadMoreListener: RequestLoadMoreListener? = null

    private var mLayoutResId: Int = 0

    private var mLayoutInflater: LayoutInflater? = null
    private var mContext: Context? = null

    //header footer
    private val mHeaderLayout: LinearLayout? = null
    private val mFooterLayout: LinearLayout? = null

    //empty
    private val mEmptyLayout: FrameLayout? = null
    private val mIsUseEmpty = true

    companion object {
        var HEADER_VIEW: Int = 0x00000111
        var LOADING_VIEW: Int = 0x00000222
        var FOOTER_VIEW: Int = 0x00000333
        var EMPTY_VIEW: Int = 0x00000555
    }

    constructor(@LayoutRes layoutResId: Int) : this(layoutResId, null)

    constructor(@LayoutRes layoutResId: Int, data: List<T>?) {
        mLayoutResId = layoutResId
        mData = data
    }

    override fun onBindViewHolder(holder: K, position: Int) {
        autoLoadMore(position)
        var viewType: Int = holder.itemViewType
        when (viewType) {
            HEADER_VIEW -> {
            }
            LOADING_VIEW -> {
                mloadMoreView.convert(holder)
            }
            FOOTER_VIEW -> {
            }
            EMPTY_VIEW -> {
            }
            else -> {
                convert(holder, getItem(position))
            }
        }
    }

    override fun getItemCount(): Int {
        var count: Int
        if (getEmptyViewCount() == 1) {
            count = 1
        } else {
            count = getLoadMoreViewCount()
        }
        return count
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): K? {
        var baseViewHolder: K? = null
        mContext = parent!!.context
        mLayoutInflater = LayoutInflater.from(mContext)
        when (viewType) {
            HEADER_VIEW -> {
            }
            LOADING_VIEW -> {
                baseViewHolder = getLoadingView(parent)
            }
            FOOTER_VIEW -> {
            }
            EMPTY_VIEW -> {
            }
            else -> {
                baseViewHolder = onCreateDefViewHolder(parent, viewType)
            }
        }
        baseViewHolder!!.mAdapter = this
        return baseViewHolder
    }

    override fun getItemViewType(position: Int): Int {
        if (getEmptyViewCount() == 1) {
            when (position) {
                0 -> return EMPTY_VIEW
                1 -> return FOOTER_VIEW
                else -> return super.getItemViewType(position)
            }
        }
//        return LOADING_VIEW
        return super.getItemViewType(position)
//        val numFooters = getFooterLayoutCount()
//        var adjPosition: Int = position - mData!!.size
//        if (adjPosition < numFooters)
//            return FOOTER_VIEW
//        else
//            return LOADING_VIEW
    }

    /**
     * if addHeaderView will be return 1, if not will be return 0
     */
    fun getHeaderLayoutCount(): Int {
        return if (mHeaderLayout == null || mHeaderLayout.childCount == 0) 0 else 1
    }

    fun getFooterLayoutCount(): Int {
        return if (mFooterLayout == null || mFooterLayout.childCount == 0) 0 else 1
    }

    private fun getLoadingView(parent: ViewGroup?): K {
        val view = getItemView(mloadMoreView.layoutId, parent)
        val holder: K = createBaseViewHolder(view)
        return holder
    }

    protected fun onCreateDefViewHolder(parent: ViewGroup?, viewType: Int): K {
        return createBaseViewHolder(getItemView(mLayoutResId, parent))
    }

    protected fun createBaseViewHolder(view: View): K {
        var temp: Class<*>? = javaClass
        var z: Class<*>? = null
        while (z == null && null != temp) {
            z = getInstancedGenericKClass(temp)
            temp = temp.superclass
        }
        val k: K?
        // 泛型擦除会导致z为null
        if (z == null) {
            k = BaseViewHolder(view) as K
        } else {
            k = createGenericKInstance(z, view) as K
        }
        return if (k != null) k else BaseViewHolder(view) as K
    }

    protected fun getItemView(@LayoutRes layoutResId: Int, parent: ViewGroup?): View {
        return mLayoutInflater!!.inflate(layoutResId, parent, false)
    }

    /**
     * get generic parameter K
     *
     * @param z
     * @return
     */
    private fun getInstancedGenericKClass(z: Class<*>): Class<*>? {
        val type = z.genericSuperclass
        if (type is ParameterizedType) {
            val types = type.getActualTypeArguments()
            for (temp in types) {
                if (temp is Class<*>) {
                    val tempClass = temp
                    if (BaseViewHolder::class.java.isAssignableFrom(tempClass)) {
                        return tempClass
                    }
                } else if (temp is ParameterizedType) {
                    val rawType = temp.getRawType()
                    if (rawType is Class<*> && BaseViewHolder::class.java.isAssignableFrom(rawType)) {
                        return rawType
                    }
                }
            }
        }
        return null
    }

    private fun createGenericKInstance(z: Class<*>, view: View): Any? {
        try {
            val constructor: Constructor<*>
            // inner and unstatic class
            if (z.isMemberClass && !Modifier.isStatic(z.modifiers)) {
                constructor = z.getDeclaredConstructor(javaClass, View::class.java)
                constructor.setAccessible(true)
                return constructor.newInstance(this, view)
            } else {
                constructor = z.getDeclaredConstructor(View::class.java)
                constructor.setAccessible(true)
                return constructor.newInstance(view)
            }
        } catch (e: NoSuchMethodException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        } catch (e: InstantiationException) {
            e.printStackTrace()
        } catch (e: InvocationTargetException) {
            e.printStackTrace()
        }
        return null
    }

    protected abstract fun convert(holder: K, item: T?)

    fun getItem(@IntRange(from = 0) position: Int): T? {
        if (position < mData!!.size)
            return mData!!.get(position)
        else
            return null

    }

    fun getEmptyViewCount(): Int {
        if (mEmptyLayout == null || mEmptyLayout.getChildCount() === 0) {
            return 0
        }
        if (!mIsUseEmpty) {
            return 0
        }
        return if (mData!!.size !== 0) {
            0
        } else 1
    }

    fun autoLoadMore(position: Int) {
        if (getLoadMoreViewCount() == 0) {
            return
        }
        if (position < (getItemCount() - mPreLoadNumber)) {
            return
        }
        if (mloadMoreView!!.getLoadStatus() != LoadMoreView.STATUS_DEFAULT) {
            return
        }
        mloadMoreView.setLoadStatus(LoadMoreView.STATUS_LOADING)

        if (!mLoading) {
            //TODO
        }
    }

    fun getLoadMoreViewCount(): Int {
        if (mRequestLoadMoreListener == null || !mLoadMoreEnable) {
            return 0
        }
        if (mData!!.size == 0) {
            return 0
        }
        return 1
    }

    fun addData(@IntRange(from = 0) position: Int, newData: List<T>) {
        (mData as ArrayList<T>).addAll(position, newData)
        notifyItemInserted(position)
        notifyDataSetChanged()
    }

    fun addData(newData: List<T>) {
        (mData as ArrayList).addAll(newData)
        notifyItemRangeInserted(mData!!.size - newData.size, newData.size)
        notifyDataSetChanged()
    }

    fun setNewData(data: List<T>) {
        this.mData = if (data == null) ArrayList<T>() else data
        if (mRequestLoadMoreListener != null) {
            mLoadMoreEnable = true
            mLoading = false
            mloadMoreView.setLoadStatus(LoadMoreView.STATUS_DEFAULT)
        }
        notifyDataSetChanged()
    }


    interface RequestLoadMoreListener {
        fun onLoadMoreRequest()
    }

}