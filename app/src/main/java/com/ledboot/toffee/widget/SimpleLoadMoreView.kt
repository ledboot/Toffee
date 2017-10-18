package com.ledboot.toffee.widget

import com.ledboot.toffee.R

/**
 * Created by Gwynn on 17/10/16.
 */
class SimpleLoadMoreView : LoadMoreView() {

    override val layoutId: Int
        get() = R.layout.quick_load_more_view
    override val loadingViewId: Int
        get() = R.id.load_more_loading_view
    override val loadingFailViewId: Int
        get() = R.id.load_more_load_fail_view
    override val loadingEndViewId: Int
        get() = R.id.load_more_load_end_view
}