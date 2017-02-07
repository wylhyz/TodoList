package com.lhyz.android.todolist.statistics

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.lhyz.android.todolist.R


/**
 * hello,android
 * @author lhyz on 2017/2/7
 */
class StatisticsFragment : Fragment(), StatisticsContract.View {

    companion object {
        fun newInstance(): StatisticsFragment {
            return StatisticsFragment()
        }
    }

    var mStatisticsTV: TextView? = null
    var mPresenter: StatisticsContract.Presenter? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater!!.inflate(R.layout.statistics_frag, container, false)
        mStatisticsTV = root.findViewById(R.id.statistics) as TextView
        return root
    }

    override fun onResume() {
        super.onResume()
        mPresenter!!.start()
    }

    override fun setProgressIndicator(active: Boolean) {
        if (active) {
            mStatisticsTV!!.text = getString(R.string.loading)
        } else {
            mStatisticsTV!!.text = ""
        }
    }

    override fun showStatistics(numberOfIncompleteTasks: Int, numberOfCompletedTasks: Int) {
        if (numberOfCompletedTasks === 0 && numberOfIncompleteTasks === 0) {
            mStatisticsTV!!.text = resources.getString(R.string.statistics_no_tasks)
        } else {
            val displayString = resources.getString(R.string.statistics_active_tasks) + " "
            (+numberOfIncompleteTasks).toString() + "\n" + resources.getString(
                    R.string.statistics_completed_tasks) + " " + numberOfCompletedTasks
            mStatisticsTV!!.text = displayString
        }
    }

    override fun showLoadingStatisticsError() {
        mStatisticsTV!!.text = resources.getString(R.string.statistics_error);
    }

    override fun isActive(): Boolean {
        return isAdded
    }

    override fun setPresenter(presenter: StatisticsContract.Presenter) {
        mPresenter = presenter
    }
}