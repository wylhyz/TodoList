package com.lhyz.android.todolist.util

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager

/**
 * hello,android
 * @author lhyz on 2017/2/6
 */
class ActivityUtils{
    companion object{
        fun addFragmentToActivity(fragmentManager: FragmentManager,
                                  fragment: Fragment,
                                  frameId: Int) {
            val transaction = fragmentManager.beginTransaction();
            transaction.add(frameId, fragment)
            transaction.commit()
        }
    }
}

