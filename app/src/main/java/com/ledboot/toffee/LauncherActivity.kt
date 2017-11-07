package com.ledboot.toffee

import android.annotation.TargetApi
import android.content.Intent
import android.content.pm.ShortcutInfo
import android.content.pm.ShortcutManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Icon
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.support.design.widget.BottomNavigationView
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import com.ledboot.toffee.adapter.LaucherPageAdapter
import com.ledboot.toffee.base.BaseActivity
import com.ledboot.toffee.utils.Debuger
import com.ledboot.toffee.utils.MediaController
import kotlinx.android.synthetic.main.activity_launcher.*
import kotlinx.android.synthetic.main.content_launcher.*
import java.util.*

class LauncherActivity : BaseActivity() {

    val TAG: String = LauncherActivity::class.java.simpleName
    private lateinit var mToolbar: Toolbar
    private lateinit var drawer: DrawerLayout

    val laucherAdapter by lazy { LaucherPageAdapter(this, supportFragmentManager, MainData.fragmentList) }

    object MainData {
        val fragmentList: Array<Fragment> = arrayOf(HomeFrament(), GirlFrament(), UserFrament())
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launcher)
        initView()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
            createDynamicShortcuts()
        }
        val value = MediaController.instance.startRecord("/mnt/sdcard/1.ppu")
        Debuger.logD("Launcher","value = $value")
    }

    private fun initView() {
        mToolbar = findViewById<Toolbar>(R.id.toolbar) as Toolbar
        nav_view.setNavigationItemSelectedListener(mSideNavigationItemSeletedListener)
        setSupportActionBar(mToolbar)
        drawer = findViewById<DrawerLayout>(R.id.drawer_layout) as DrawerLayout
        val toggle = ActionBarDrawerToggle(
                this, drawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer.addDrawerListener(toggle)
        toggle.syncState()
        navigation.setupWithViewPager(view_page, true)
        navigation.enableShiftMode(false)
        navigation.enableItemShiftMode(false)
        view_page.adapter = laucherAdapter
        view_page.offscreenPageLimit = 3
        view_page.setCurrentItem(0)

        nav_view.menu.findItem(R.id.nav_manage).setOnMenuItemClickListener { item: MenuItem? ->
            Handler().postDelayed({
                run {
                    startActivity(Intent(this@LauncherActivity, FingeprintActivity::class.java))
                }
            }, 1500L)
            false
        }
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
        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout) as DrawerLayout
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
        } else if (id == R.id.action_add_shortcut) {
            installShortcut()
            true
        } else super.onOptionsItemSelected(item)

    }

    fun installShortcut() {
        val intent = createShortcut()
        intent.action = "com.android.launcher.action.INSTALL_SHORTCUT"
        AppLoader.appContext.sendBroadcast(intent)
    }

    fun createShortcut(): Intent {
        val userId = 10001
        val userName = "皮特"

        val shortcutIntent = Intent(Intent.ACTION_MAIN)
        shortcutIntent.setClass(AppLoader.appContext, ShortcutEntryActivity::class.java)
        shortcutIntent.addCategory(Intent.CATEGORY_LAUNCHER)
        shortcutIntent.putExtra("userId", userId)
        shortcutIntent.action = "com.ledboot.shortentry" + userId
        shortcutIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP


        val addIntent = Intent()
        var bitmap: Bitmap = BitmapFactory.decodeResource(resources, R.mipmap.if_unknown)
        addIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent)
        addIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, userName)
        addIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON, bitmap)
        addIntent.putExtra("duplicate", false)

        return addIntent
    }

    @TargetApi(Build.VERSION_CODES.N_MR1)
    fun createDynamicShortcuts() {
        val shortcutManager: ShortcutManager = getSystemService(ShortcutManager::class.java)

        val intent:Intent = Intent(this, ComposeActivity::class.java)
        intent.action = "com.ledboot.shortentry"
        val shortcut: ShortcutInfo = ShortcutInfo.Builder(this, "id1")
                .setShortLabel(getString(R.string.compose_shortcut_short_label))
                .setLongLabel(getString(R.string.compose_shortcut_long_label))
                .setIntent(intent)
                .setIcon(Icon.createWithResource(this, R.mipmap.ic_eye))
                .build()

        shortcutManager.setDynamicShortcuts(Arrays.asList(shortcut))
    }

}
