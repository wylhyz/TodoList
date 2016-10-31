/**
 * RESTful API v1
 * Created by lhyz on 2016/7/27.
 */
var express = require('express');
var Task = require('../../models/Task');
var DB = require('../../models/Database');
var router = express.Router();//router导出

//API设计第一版

/**
 * API结果 构造函数
 * @param error
 * @param errMsg
 * @param result
 * @constructor
 */
function Result(error, errMsg, result) {
    this.error = error;
    this.errMsg = errMsg;
    this.result = result;
}

/**
 * 查询所有数据
 */
router.get('/task', function (req, res) {
    var result;
    DB.getAll(function (err, tasks) {
        if (err) {
            result = new Result(true, err.message, null);
            res.send(result);
            return;
        }
        result = new Result(false, null, tasks.reverse());
        res.send(result);
    });
});

/**
 * 查询单条数据
 */
router.get('/task/:id', function (req, res) {
    var id = req.params.id;
    var result;
    DB.get(id, function (err, task) {
        if (err) {
            result = new Result(true, err.message, null);
            res.send(result);
            return;
        }
        result = new Result(false, null, [task]);//为将单个对象转换成json数组对象需要在对象上加数组括号[task]
        res.send(result);
    });
});

//noinspection JSUnresolvedFunction
/**
 * 添加一条数据
 *
 * request body Sample title="hello",description="world"
 */
router.post('/task', function (req, res) {
    var id = req.body.id;
    var title = req.body.title;
    var description = req.body.description;
    var newTask = new Task(id, title, description, false);
    var result;
    DB.save(newTask, function (err) {
        if (err) {
            result = new Result(true, err.message, null);
            res.send(result);
            return;
        }
        result = new Result(false, null, [newTask]);
        res.send(result);
    });
});


/**
 * 顺序很重要，因为 :id 参数也可以被解析成 completed
 *
 */
//noinspection JSUnresolvedFunction
router.delete('/task/completed', function (req, res) {
    var result;
    DB.deleteCompleted(function (err) {
        if (err) {
            result = new Result(true, err.message, null);
            res.send(result);
            return;
        }
        result = new Result(false, null, null);
        res.send(result);
    })
});

//noinspection JSUnresolvedFunction
/**
 * 删除一条数据
 *
 * 数据由id标识
 */
router.delete('/task/:id', function (req, res) {
    //noinspection JSUnresolvedVariable
    var id = req.params.id;
    var result;
    DB.delete(id, function (err) {
        if (err) {
            result = new Result(true, err.message, null);
            res.send(result);
            return;
        }
        result = new Result(false, null, null);
        res.send(result);
    })
});

//noinspection JSUnresolvedFunction
router.put('/task/complete/:id', function (req, res) {
    var id = req.params.id;
    var completed = req.body.completed;
    var result;
    DB.update(id, {"completed": completed}, function (err) {
        if (err) {
            result = new Result(true, err.message, null);
            res.send(result);
            return;
        }
        result = new Result(false, null, null);
        res.send(result);
    });
});


//noinspection JSUnresolvedFunction
/**
 * 更新一条数据
 *
 * 数据通过request body传输，格式为json（不是json数组）
 *
 * Sample : params={"completed":"true"}
 *          params={"completed":"true","description":"测试数据"}
 */
router.put('/task/:id', function (req, res) {
    var id = req.params.id;
    var title = req.body.title;
    var description = req.body.description;
    var completed = req.body.completed;
    var result;
    DB.update(id, {"title": title, "description": description, "completed": completed}, function (err, task) {
        if (err) {
            result = new Result(true, err.message, null);
            res.send(result);
            return;
        }
        result = new Result(false, null, null);
        res.send(result);
    });
});

module.exports = router;