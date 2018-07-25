/*
 * Copyright (c) 2012-2018 Arne Schwabe
 * Distributed under the GNU GPL v2 with additional terms. For full terms see the file doc/LICENSE.txt
 */

package manu.ui.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.design.internal.BottomNavigationItemView
import android.support.design.internal.BottomNavigationMenuView
import android.support.design.widget.BottomNavigationView
import android.support.v4n.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.view.View
import de.blinkt.openvpn.R
import de.blinkt.openvpn.fragments.VPNProfileList
import de.blinkt.openvpn.views.ScreenSlidePagerAdapter
import manu.ui.todo.FragmentToDo


class MainActivityManu : AppCompatActivity(), MainContract.View {

    companion object {
        const val VPN_PAGE = 0
        const val BOX_PAGE = 1
        const val SECURITY_PAGE = 2
        const val ABOUT_PAGE = 3
        const val EXIT_PAGE = 4
    }

    lateinit var mPresenter: MainContract.Presenter
    lateinit var mPager: ViewPager
    lateinit var mBottomNavigationView: BottomNavigationView
    lateinit var mPagerAdapter: ScreenSlidePagerAdapter
    private var mCurrentItem = VPN_PAGE

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_manu)

        mPager = findViewById<View>(R.id.activity_main_manu_vp_pager) as ViewPager
        mPager.offscreenPageLimit = 5
        mBottomNavigationView = findViewById<View>(R.id.activity_main_manu_bnv_bottom_navigation) as BottomNavigationView
        mPagerAdapter = ScreenSlidePagerAdapter(fragmentManager, this)

        mBottomNavigationView.disableShiftMode()

        mPresenter = MainPresenter(this)
        mPresenter.onCreate()

    }

    override fun setUpUi() {

        mPagerAdapter.addTab(R.string.vpn_list_title, VPNProfileList::class.java)
        mPagerAdapter.addTab(R.string.fragment_to_do, FragmentToDo::class.java)
        mPagerAdapter.addTab(R.string.fragment_to_do, FragmentToDo::class.java)
        mPagerAdapter.addTab(R.string.fragment_to_do, FragmentToDo::class.java)
        mPager.adapter = mPagerAdapter

        mBottomNavigationView.setOnNavigationItemSelectedListener {

            when (it.itemId) {
                R.id.action_vpn -> mPager.currentItem = VPN_PAGE
                R.id.action_box -> mPager.currentItem = BOX_PAGE
                R.id.action_security -> mPager.currentItem = SECURITY_PAGE
                R.id.action_about -> mPager.currentItem = ABOUT_PAGE
                R.id.action_exit -> mPager.currentItem = EXIT_PAGE
            }

            true

        }

        mPager.setOnPageChangeListener(object : ViewPager.OnPageChangeListener {

            override fun onPageSelected(position: Int) {

                mBottomNavigationView.menu.getItem(mCurrentItem).isChecked = false
                mBottomNavigationView.menu.getItem(position).isChecked = true
                mCurrentItem = position

            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}
            override fun onPageScrollStateChanged(state: Int) {}
        })

    }

    fun BottomNavigationView.disableShiftMode() {
        val menuView = getChildAt(0) as BottomNavigationMenuView

        menuView.javaClass.getDeclaredField("mShiftingMode").apply {
            isAccessible = true
            setBoolean(menuView, false)
            isAccessible = false
        }

        @SuppressLint("RestrictedApi")
        for (i in 0 until menuView.childCount) {
            (menuView.getChildAt(i) as BottomNavigationItemView).apply {
                setShiftingMode(false)
                setChecked(false)
            }
        }
    }

}
