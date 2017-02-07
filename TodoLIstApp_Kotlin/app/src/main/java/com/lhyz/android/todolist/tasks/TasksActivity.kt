package com.lhyz.android.todolist.tasks

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import com.lhyz.android.todolist.Injection
import com.lhyz.android.todolist.R
import com.lhyz.android.todolist.statistics.StatisticsActivity
import com.lhyz.android.todolist.util.ActivityUtils

/**
 * hello,android
 * @author lhyz on 2017/2/6
 */
class TasksActivity : AppCompatActivity() {
    private val CURRENT_FILTERING_KEY = "CURRENT_FILTERING_KEY"

    private var mDrawerLayout: DrawerLayout? = null

    private var mTasksPresenter: TasksPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.tasks_act)

        // Set up the toolbar.
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        val ab = supportActionBar
        ab!!.setHomeAsUpIndicator(R.drawable.ic_menu)
        ab.setDisplayHomeAsUpEnabled(true)

        // Set up the navigation drawer.
        mDrawerLayout = findViewById(R.id.drawer_layout) as DrawerLayout
        mDrawerLayout!!.setStatusBarBackground(R.color.colorPrimaryDark)
        val navigationView = findViewById(R.id.nav_view) as NavigationView?
        if (navigationView != null) {
            setupDrawerContent(navigationView)
        }

        /**
         * kotlin.TypeCastException: null cannot be cast to non-null type 异常发生 ，解决方法是 as 后加上?
         */
        var tasksFragment: TasksFragment? = supportFragmentManager.findFragmentById(R.id.contentFrame) as? TasksFragment
        if (tasksFragment == null) {
            // Create the fragment
            tasksFragment = TasksFragment.newInstance()
            ActivityUtils.addFragmentToActivity(
                    supportFragmentManager, tasksFragment, R.id.contentFrame)
        }

        // Create the presenter
        mTasksPresenter = TasksPresenter(
                Injection.provideTasksRepository(applicationContext), tasksFragment)

        // Load previously saved state, if available.
        if (savedInstanceState != null) {
            val currentFiltering = savedInstanceState.getSerializable(CURRENT_FILTERING_KEY) as TasksFilterType
            mTasksPresenter!!.setFiltering(currentFiltering)
        }
    }

    public override fun onSaveInstanceState(outState: Bundle) {
        outState.putSerializable(CURRENT_FILTERING_KEY, mTasksPresenter!!.getFiltering())

        super.onSaveInstanceState(outState)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                // Open the navigation drawer when the home icon is selected from the toolbar.
                mDrawerLayout!!.openDrawer(GravityCompat.START)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupDrawerContent(navigationView: NavigationView) {
        navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.list_navigation_menu_item -> {
                }
                R.id.statistics_navigation_menu_item -> {
                    val intent = Intent(this@TasksActivity, StatisticsActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    startActivity(intent)
                }
                else -> {
                }
            }// Do nothing, we're already on that screen
            // Close the navigation drawer when an item is selected.
            menuItem.isChecked = true
            mDrawerLayout!!.closeDrawers()
            true
        }
    }
}