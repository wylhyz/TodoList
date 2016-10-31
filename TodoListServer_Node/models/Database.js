/**
 * Created by lhyz on 2016/8/4.
 */
var mongo = require('mongodb');
var MongoClient = mongo.MongoClient;
var config = require('../config.json');
var url = 'mongodb://' + config.db_host + ':' + config.db_port + '/' + config.db_base;
var Task = require('./Task');

function DB() {

}

/**
 * 保存一条数据到库
 *
 * @param task 一条数据
 * @param callback 形式是 func(err,result)
 */
DB.prototype.save = function (task, callback) {
    var _task = {
        id: task.id,
        title: task.title,
        description: task.description,
        completed: task.completed
    };

    MongoClient.connect(url, function (err, db) {
        if (err) {
            return callback(err);
        }
        db.collection('tasks').insertOne(_task, callback);
        db.close();
    });
};

/**
 * 获取数据
 * @param id UUID
 * @param callback 方法类似 func(err,task)
 */
DB.prototype.get = function (id, callback) {
    MongoClient.connect(url, function (err, db) {
        if (err) {
            return callback(err);
        }
        db.collection('tasks').find({'id': id}).next(callback);
        db.close();
    });
};

/**
 * 获取所有数据
 * @param callback(err,tasks)
 */
DB.prototype.getAll = function (callback) {
    MongoClient.connect(url, function (err, db) {
        if (err) {
            return callback(err);
        }
        db.collection('tasks').find({}).toArray(callback);
        db.close();
    });
};

/**
 * 删除一条数据
 * @param id
 * @param callback
 */
DB.prototype.delete = function (id, callback) {
    MongoClient.connect(url, function (err, db) {
        if (err) {
            return callback(err);
        }
        db.collection('tasks').deleteOne({'id': id}, callback);
        db.close();
    });
};


DB.prototype.deleteCompleted = function (callback) {
    MongoClient.connect(url, function (err, db) {
        if (err) {
            return callback(err);
        }
        db.collection('tasks').deleteMany({"completed": true}, callback);
        db.close();
    });
};

/**
 *
 * 通过id匹配更新一条数据
 * 更新一条数据
 * @param id
 * @param params 参数是json类型的
 * @param callback
 */
DB.prototype.update = function (id, params, callback) {
    MongoClient.connect(url, function (err, db) {
        if (err) {
            return callback(err);
        }
        db.collection('tasks').updateOne({'id': id}, {$set: params}, callback);
        db.close();
    });
};

module.exports = new DB();