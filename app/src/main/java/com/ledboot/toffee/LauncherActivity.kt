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
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.ledboot.toffee.adapter.LaucherPageAdapter
import com.ledboot.toffee.base.BaseActivity
import com.ledboot.toffee.databinding.ActivityLauncherBinding
import com.ledboot.toffee.utils.Debuger
import com.ledboot.toffee.utils.MediaController
import java.util.*

class LauncherActivity : BaseActivity() {

    val TAG: String = LauncherActivity::class.java.simpleName

    val laucherAdapter by lazy { LaucherPageAdapter(supportFragmentManager, MainData.fragmentList) }

    lateinit var binding: ActivityLauncherBinding

    object MainData {
        val fragmentList: Array<Fragment> = arrayOf(HomeFrament(), GirlFrament(), UserFrament())
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_launcher)
        initView()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
            createDynamicShortcuts()
        }
        val value = MediaController.instance.startRecord("/mnt/sdcard/1.ppu")
        Debuger.logD("Launcher", "value = $value")
    }

    private fun initView() {
        binding.navigation.setupWithViewPager(binding.viewpager, true)
        binding.navigation.enableShiftMode(false)
        binding.navigation.enableItemShiftMode(false)
        binding.viewpager.adapter = laucherAdapter
        binding.viewpager.offscreenPageLimit = 3
        binding.viewpager.setCurrentItem(0)

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
