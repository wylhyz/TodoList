var express = require('express');
var path = require('path');
var favicon = require('serve-favicon');
var logger = require('morgan');
var cookieParser = require('cookie-parser');
var bodyParser = require('body-parser');
var nunjucks = require('nunjucks');

//本地路由
var roots = require('./routes/index');
var api_v1 = require('./routes/api/v1');

//express app
var app = express();

//配置views目录和渲染模板关联
nunjucks.configure(path.join(__dirname, 'views'), {
    autoescape: true,
    express: app
});

// view engine setup
app.set('view engine', 'njk');//模板文件的后缀名是njk

// uncomment after placing your favicon in /public
app.use(favicon(path.join(__dirname, 'public', 'favicon.ico')));
app.use(logger('dev'));
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({extended: false}));
app.use(cookieParser());
//使用静态文件目录
app.use(express.static(path.join(__dirname, 'public')));

app.use('/', roots);
app.use('/api/v1', api_v1);

// catch 404 and forward to error handler
app.use(function (req, res, next) {
    var err = new Error('Not Found');
    err.status = 404;
    next(err);
});

// error handlers

// development error handler
// will print stacktrace
if (app.get('env') === 'development') {
    app.use(function (err, req, res, next) {
        res.status(err.status || 500);
        res.render('error', {
            message: err.message,
            error: err
        });
    });
}

// production error handler
// no stacktraces leaked to user
app.use(function (err, req, res, next) {
    res.status(err.status || 500);
    res.render('error', {
        message: err.message,
        error: {}
    });
});


module.exports = app;
