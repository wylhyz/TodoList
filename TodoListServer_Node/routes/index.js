var express = require('express');
var Task = require("../models/Task.js");
var DB = require('../models/Database');
var uuid = require('node-uuid');
var moment = require('moment');
var router = express.Router();//router导出

//此路由root=/

/* GET home page. */
router.get('/', function (req, res) {
    DB.getAll(function (err, tasks) {
        if (err) {
            res.send(err.message);
        }
        res.render('index', {allContent: tasks.reverse()});
    });
});


//noinspection JSUnresolvedFunction
router.post('/add', function (req, res) {
    var title = req.body.title;
    var description = req.body.description;
    var newTask = new Task(uuid.v4(), title, description, false);
    DB.save(newTask, function (err) {
        if (err) {
            console.log(err.message);
            return;
        }
        res.redirect('/');
    });
});

//noinspection JSUnresolvedFunction
router.post('/delete', function (req, res) {
    var id = req.body.id;
    DB.delete(id, function (err) {
        if (err) {
            console.log(err.message);
            return;
        }
        res.redirect('/');
    });
});

router.get('/all', function (req, res) {
    DB.getAll(function (err, tasks) {
        res.render('all', {allContent: tasks.reverse()});
    });
});

module.exports = router;
