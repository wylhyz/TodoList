package com.lhyz.android.todolist.data.source.local

/**
 * hello,android
 * @author lhyz on 2017/2/5
 *
 * 无法将源代码转换
 * TODO !!!
 */
class TasksPersistenceContract {
    abstract class TaskEntry {
        companion object {
            val _ID = "_id"
            //            val _COUNT = "_count"
            val TABLE_NAME = "task"
            val COLUMN_NAME_ENTRY_ID = "entryid"
            val COLUMN_NAME_TITLE = "title"
            val COLUMN_NAME_DESCRIPTION = "description"
            val COLUMN_NAME_COMPLETED = "completed"
        }
    }
}