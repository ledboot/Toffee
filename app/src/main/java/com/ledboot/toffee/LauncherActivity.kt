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
import android.support.v4.app.Fragment
import com.ledboot.toffee.base.BaseActivity
import com.ledboot.toffee.utils.Debuger
import com.ledboot.toffee.utils.MediaController
import java.util.*

class LauncherActivity : BaseActivity() {

    val TAG: String = LauncherActivity::class.java.simpleName

//    val laucherAdapter by lazy { LaucherPageAdapter(fragmentManager, MainData.fragmentList) }

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
        Debuger.logD("Launcher", "value = $value")
    }

    private fun initView() {
//        navigation.setupWithViewPager(view_page, true)
//        navigation.enableShiftMode(false)
//        navigation.enableItemShiftMode(false)
//        view_page.adapter = laucherAdapter
//        view_page.offscreenPageLimit = 3
//        view_page.setCurrentItem(0)

//        nav_view.menu.findItem(R.id.nav_manage).setOnMenuItemClickListener { item: MenuItem? ->
//            Handler().postDelayed({
//                run {
//                    startActivity(Intent(this@LauncherActivity, FingeprintActivity::class.java))
//                }
//            }, 1500L)
//            false
//        }
    }


    override fun onBackPressed() {
        super.onBackPressed()
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

        val intent = Intent(this, ComposeActivity::class.java)
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
