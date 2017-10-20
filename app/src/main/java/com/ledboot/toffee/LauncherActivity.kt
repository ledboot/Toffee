package com.ledboot.toffee

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import cn.com.bsfit.dfp.android.FRMS
import com.ledboot.toffee.adapter.LaucherPageAdapter
import com.ledboot.toffee.base.BaseActivity
import kotlinx.android.synthetic.main.activity_launcher.*
import kotlinx.android.synthetic.main.content_launcher.*
import kotlinx.android.synthetic.main.nav_header_launcher.*

class LauncherActivity : BaseActivity() {

    val TAG: String = LauncherActivity::class.java.simpleName
    private var toolbar: Toolbar? = null
    private var drawer: DrawerLayout? = null

    val laucherAdapter by lazy { LaucherPageAdapter(this, supportFragmentManager, MainData.fragmentList) }

    object MainData {
        val fragmentList: Array<Fragment> = arrayOf(HomeFrament(), GirlFrament(), UserFrament())
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launcher)
        initView()
        startDFP()
    }

    private fun startDFP() {
//        val fingerprint = FRMS.getInstance().get(5000)
//        textView.text = "指纹：" + fingerprint
    }

    private fun initView() {
        toolbar = findViewById(R.id.toolbar) as Toolbar
        nav_view.setNavigationItemSelectedListener(mSideNavigationItemSeletedListener)
        setSupportActionBar(toolbar)
        drawer = findViewById(R.id.drawer_layout) as DrawerLayout
        val toggle = ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer?.addDrawerListener(toggle)
        toggle.syncState()
        navigation.setupWithViewPager(view_page, true)
        navigation.enableShiftMode(false)
        navigation.enableItemShiftMode(false)
        view_page.adapter = laucherAdapter
        view_page.offscreenPageLimit = 3
        view_page.setCurrentItem(0)
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    private val mSideNavigationItemSeletedListener = NavigationView.OnNavigationItemSelectedListener { item ->

        val id = item.itemId

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }
        drawer?.closeDrawer(GravityCompat.START)
        true

    }

    override fun onBackPressed() {
        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.launcher, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId


        return if (id == R.id.action_settings) {
            true
        } else super.onOptionsItemSelected(item)

    }

}
