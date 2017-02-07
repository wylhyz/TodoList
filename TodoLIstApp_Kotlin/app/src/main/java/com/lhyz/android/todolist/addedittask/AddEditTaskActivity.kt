package com.lhyz.android.todolist.addedittask

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import com.lhyz.android.todolist.Injection
import com.lhyz.android.todolist.R
import com.lhyz.android.todolist.util.ActivityUtils


/**
 * hello,android
 * @author lhyz on 2017/2/6
 */
class AddEditTaskActivity : AppCompatActivity() {
    companion object {
        val REQUEST_ADD_TASK = 1

        val SHOULD_LOAD_DATA_FROM_REPO_KEY = "SHOULD_LOAD_DATA_FROM_REPO_KEY"
    }

    private var mAddEditTaskPresenter: AddEditTaskContract.Presenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.addtask_act)

        // Set up the toolbar.
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        val actionBar = supportActionBar
        actionBar!!.setDisplayHomeAsUpEnabled(true)
        actionBar.setDisplayShowHomeEnabled(true)

        var addEditTaskFragment: AddEditTaskFragment? = supportFragmentManager.findFragmentById(R.id.contentFrame) as AddEditTaskFragment?

        val taskId = intent.getStringExtra(AddEditTaskFragment.ARGUMENT_EDIT_TASK_ID)

        if (addEditTaskFragment == null) {
            addEditTaskFragment = AddEditTaskFragment.newInstance()

            if (intent.hasExtra(AddEditTaskFragment.ARGUMENT_EDIT_TASK_ID)) {
                actionBar.setTitle(R.string.edit_task)
                val bundle = Bundle()
                bundle.putString(AddEditTaskFragment.ARGUMENT_EDIT_TASK_ID, taskId)
                addEditTaskFragment.arguments = bundle
            } else {
                actionBar.setTitle(R.string.add_task)
            }

            ActivityUtils.addFragmentToActivity(supportFragmentManager,
                    addEditTaskFragment, R.id.contentFrame)
        }

        var shouldLoadDataFromRepo = true

        // Prevent the presenter from loading data from the repository if this is a config change.
        if (savedInstanceState != null) {
            // Data might not have loaded when the config change happen, so we saved the state.
            shouldLoadDataFromRepo = savedInstanceState.getBoolean(SHOULD_LOAD_DATA_FROM_REPO_KEY)
        }

        // Create the presenter
        mAddEditTaskPresenter = AddEditTaskPresenter(
                taskId,
                Injection.provideTasksRepository(applicationContext),
                addEditTaskFragment,
                shouldLoadDataFromRepo)

        addEditTaskFragment.setPresenter(mAddEditTaskPresenter!!)
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        outState!!.putBoolean(SHOULD_LOAD_DATA_FROM_REPO_KEY, mAddEditTaskPresenter!!.isDataMissing())
        super.onSaveInstanceState(outState)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}