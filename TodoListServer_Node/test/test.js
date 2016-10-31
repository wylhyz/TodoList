/**
 * Created by lhyz on 2016/7/24.
 */
var request = require('supertest');

//noinspection JSCheckFunctionSignatures
var req = request('http://localhost:3000/api/v1');

describe('REST API v1', function () {
    //测试GET方法
    it("GET /task", function (done) {
        req
            .get('/task')
            .expect(200, function (err) {
                if (err)
                    throw err;
                done();
            });
    });
    //测试GET id 方法
    it("GET /task/:id", function (done) {
        req
            .get('/task/sadwasdasdawdas')
            .expect(200, function (err) {
                if (err)
                    throw err;
                done();
            });
    });
    //测试POST方法
    it("POST /task", function (done) {
        req
            .post('/task')
            .type('form')
            .send({
                'id': '1111111111',
                'title': '111111111',
                'description': '11111122'
            })
            .expect(200, function (err) {
                if (err)
                    throw err;
                done();
            });
    });
});