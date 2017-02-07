package com.lhyz.android.todolist.tasks

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.view.*
import android.widget.*
import com.lhyz.android.todolist.R
import com.lhyz.android.todolist.addedittask.AddEditTaskActivity
import com.lhyz.android.todolist.data.Task
import com.lhyz.android.todolist.taskdetail.TaskDetailActivity
import java.util.*


/**
 * hello,android
 * @author lhyz on 2017/2/6
 */
class TasksFragment : Fragment(), TasksContract.View {

    companion object {
        fun newInstance(): TasksFragment {
            return TasksFragment()
        }
    }

    private var mPresenter: TasksContract.Presenter? = null

    private var mListAdapter: TasksAdapter? = null

    private var mNoTasksView: View? = null

    private var mNoTaskIcon: ImageView? = null

    private var mNoTaskMainView: TextView? = null

    private var mNoTaskAddView: TextView? = null

    private var mTasksView: LinearLayout? = null

    private var mFilteringLabelView: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mListAdapter = TasksAdapter(ArrayList<Task>(0), mItemListener)
    }

    override fun onResume() {
        super.onResume()
        mPresenter!!.start()
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater!!.inflate(R.layout.tasks_frag, container, false)

        val listView = root.findViewById(R.id.tasks_list) as ListView
        listView.adapter = mListAdapter
        mFilteringLabelView = root.findViewById(R.id.filteringLabel) as TextView
        mTasksView = root.findViewById(R.id.tasksLL) as LinearLayout

        mNoTasksView = root.findViewById(R.id.noTasks)
        mNoTaskIcon = root.findViewById(R.id.noTasksIcon) as ImageView
        mNoTaskMainView = root.findViewById(R.id.noTasksMain) as TextView
        mNoTaskAddView = root.findViewById(R.id.noTasksAdd) as TextView
        mNoTaskAddView!!.setOnClickListener { showAddTask() }

        val fab = activity.findViewById(R.id.fab_add_task) as FloatingActionButton
        fab.setImageResource(R.drawable.ic_add)
        fab.setOnClickListener { mPresenter!!.addNewTask() }

        val swipeRefreshLayout = root.findViewById(R.id.refresh_layout) as ScrollChildSwipeRefreshLayout
        swipeRefreshLayout.setColorSchemeColors(
                ContextCompat.getColor(activity, R.color.colorPrimary),
                ContextCompat.getColor(activity, R.color.colorAccent),
                ContextCompat.getColor(activity, R.color.colorPrimaryDark)
        )
        swipeRefreshLayout.setScrollUpChild(listView)
        swipeRefreshLayout.setOnRefreshListener { mPresenter!!.loadTasks(false) }

        setHasOptionsMenu(true)

        return root
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater!!.inflate(R.menu.tasks_fragment_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            R.id.menu_clear -> mPresenter!!.clearCompletedTasks()
            R.id.menu_filter -> showFilteringPopUpMenu()
            R.id.menu_refresh -> mPresenter!!.loadTasks(true)
        }
        return true
    }

    override fun setLoadingIndicator(active: Boolean) {
        if (view == null) {
            return
        }

        val srl = view!!.findViewById(R.id.refresh_layout) as SwipeRefreshLayout
        srl.post { srl.isRefreshing = true }
    }

    override fun showTasks(tasks: List<Task>) {
        mListAdapter!!.replaceData(tasks)

        mTasksView!!.visibility = View.VISIBLE
        mNoTasksView!!.visibility = View.GONE
    }

    override fun showAddTask() {
        val intent = Intent(context, AddEditTaskActivity::class.java)
        startActivityForResult(intent, AddEditTaskActivity.REQUEST_ADD_TASK)
    }

    override fun showTaskDetailsUi(taskId: String) {
        val intent = Intent(context, TaskDetailActivity::class.java)
        intent.putExtra(TaskDetailActivity.EXTRA_TASK_ID, taskId)
        startActivity(intent)
    }

    override fun showTaskMarkedComplete() {
        showMessage(getString(R.string.task_marked_complete))
    }

    override fun showTaskMarkedActive() {
        showMessage(resources.getString(R.string.task_marked_active))
    }

    override fun showCompletedTasksCleared() {
        showMessage(getString(R.string.completed_tasks_cleared))
    }

    override fun showLoadingTasksError() {
        showMessage(getString(R.string.loading_tasks_error))
    }

    override fun showNoTasks() {
        showNoTasksViews(
                resources.getString(R.string.no_tasks_all),
                R.drawable.ic_assignment_turned_in_24dp,
                false
        )
    }

    override fun showActiveFilterLabel() {
        mFilteringLabelView!!.text = resources.getString(R.string.label_active)
    }

    override fun showCompletedFilterLabel() {
        mFilteringLabelView!!.text = resources.getString(R.string.label_completed)
    }

    override fun showAllFilterLabel() {
        mFilteringLabelView!!.text = resources.getString(R.string.label_all)
    }

    override fun showNoActiveTasks() {
        showNoTasksViews(
                resources.getString(R.string.no_tasks_active),
                R.drawable.ic_check_circle_24dp,
                false
        )
    }

    override fun showNoCompletedTasks() {
        showNoTasksViews(
                resources.getString(R.string.no_tasks_completed),
                R.drawable.ic_verified_user_24dp,
                false
        )
    }

    override fun showSuccessfullySavedMessage() {
        showMessage(getString(R.string.successfully_saved_task_message))
    }

    fun showMessage(message: String) {
        Snackbar.make(view!!, message, Snackbar.LENGTH_LONG).show()
    }


    fun showNoTasksViews(mainText: String, iconRes: Int, showAddView: Boolean) {
        mTasksView!!.visibility = View.GONE
        mNoTasksView!!.visibility = View.VISIBLE

        mNoTaskMainView!!.text = mainText
        mNoTaskIcon!!.setImageDrawable(resources.getDrawable(iconRes))
        mNoTaskAddView!!.visibility = if (showAddView) View.VISIBLE else View.GONE
    }

    override fun isActive(): Boolean {
        return isAdded
    }

    override fun showFilteringPopUpMenu() {
        val popup = PopupMenu(context, activity.findViewById(R.id.menu_filter))
        popup.menuInflater.inflate(R.menu.filter_tasks, popup.menu)

        popup.setOnMenuItemClickListener { item ->
            when (item!!.itemId) {
                R.id.active -> mPresenter!!.setFiltering(TasksFilterType.ACTIVE_TASKS)
                R.id.completed -> mPresenter!!.setFiltering(TasksFilterType.COMPLETED_TASKS)
                else -> mPresenter!!.setFiltering(TasksFilterType.ALL_TASKS)
            }
            mPresenter!!.loadTasks(false)
            true
        }
    }

    override fun setPresenter(presenter: TasksContract.Presenter) {
        mPresenter = presenter
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        mPresenter!!.result(requestCode, resultCode)
    }

    private val mItemListener = object : TaskItemListener {
        override fun onTaskClick(clickedTask: Task) {
            mPresenter!!.openTaskDetails(clickedTask)
        }

        override fun onCompleteTaskClick(completedTask: Task) {
            mPresenter!!.completeTask(completedTask)
        }

        override fun onActivateTaskClick(activatedTask: Task) {
            mPresenter!!.activateTask(activatedTask)
        }
    }

    inner class TasksAdapter(tasks: List<Task>, itemListener: TaskItemListener) : BaseAdapter() {
        var mTasks = tasks
        var mItemListener = itemListener

        fun replaceData(tasks: List<Task>) {
            setList(tasks)
            notifyDataSetChanged()
        }

        fun setList(tasks: List<Task>) {
            mTasks = tasks
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            var rowView = convertView
            if (rowView == null) {
                val inflater = LayoutInflater.from(parent?.context)
                rowView = inflater.inflate(R.layout.task_item, parent, false)!!
            }

            val task = getItem(position)

            val titleTV = rowView.findViewById(R.id.title) as TextView
            titleTV.text = task.getTitleForList()

            val completeCB = rowView.findViewById(R.id.complete) as CheckBox

            // Active/completed task UI
            completeCB.isChecked = task.completed
            if (task.completed) {
                rowView.setBackgroundDrawable(parent!!.context
                        .resources.getDrawable(R.drawable.list_completed_touch_feedback))
            } else {
                rowView.setBackgroundDrawable(parent!!.context
                        .resources.getDrawable(R.drawable.touch_feedback))
            }

            completeCB.setOnClickListener {
                if (!task.completed) {
                    mItemListener.onCompleteTaskClick(task)
                } else {
                    mItemListener.onActivateTaskClick(task)
                }
            }

            rowView.setOnClickListener(View.OnClickListener { mItemListener.onTaskClick(task) })

            return rowView
        }

        override fun getItem(position: Int): Task {
            return mTasks[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getCount(): Int {
            return mTasks.size
        }
    }

    interface TaskItemListener {
        fun onTaskClick(clickedTask: Task)

        fun onCompleteTaskClick(completedTask: Task)

        fun onActivateTaskClick(activatedTask: Task)
    }
}