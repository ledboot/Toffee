package com.ledboot.toffee.module.user

import android.widget.Toast
import com.ledboot.toffee.base.ListBaseFragment
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.fra_list.view.*
import java.util.concurrent.TimeUnit

/**
 * Created by Eleven on 2017/9/13.
 */
class UserFragment : ListBaseFragment() {

    public val TAG: String = UserFragment::class.java.canonicalName

    override fun onFirstUserVisible() {
        super.onFirstUserVisible()
    }

    override fun onUserVisible() {
        super.onUserVisible()
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
    }

    override fun onLoadMore() {
        Toast.makeText(context, "加载", Toast.LENGTH_SHORT).show()
        Observable.timer(2, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally {
                    Toast.makeText(context, "加载成功！", Toast.LENGTH_SHORT).show()
                    view!!.refresh_view.onLoadFinish()
                }
    }

    override fun onRefresh() {
        Toast.makeText(context, "刷新", Toast.LENGTH_SHORT).show()
        Observable.timer(3, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally({
                    kotlin.run {
                        view!!.refresh_view.refreshFinish()
                        Toast.makeText(context, "doFinally！", Toast.LENGTH_SHORT).show()
                    }
                })
                .subscribe({
                    Toast.makeText(context, "刷新成功！", Toast.LENGTH_SHORT).show()

                })
    }
}