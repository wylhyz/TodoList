# TodoList接口文档

1-明确目标要求

RESTful API 接口分为以下几种，主要也对应数据库中的集中操作---
CRUD（增/查/改/删），也就是Create/Read/Update/Delete几种操作，对应的API操作分别是POST/GET/PUT/DELETE对应，如下表格

操作|相应HTTP方法
-------|-------
Create | POST /add
Read | GET /:id
Update | PUT /:id
Delete | DELETE /:id

设计稿
=======

首先规定接口规范，为了方便，会忽略很多错误控制状态和其他复杂状态

1.错误处理

- 首先，规定错误标志位 `error` 键值为`true`或是`false` 
- 错误信息`errMsg`，键值为出错字符串,`error`为`false`时值为`null`

2.结果

- 使用`result`作为结果的键，值为如下列表对应的值

 操作|结果值
 ---|-----
 GET /task | 结果列表List  ([{...},{...},...])
 GET /task/:id | 单个结果 ({...})
 POST /task | 完整的结果对象({...})
 PUT /task/:id | 修改完成的结果对象 ({...})
 PUT /task/complete/:id | 修改完成的结果对象 ({...})
 DELETE /task/completed | 空对象 (`null`)
 DELETE /task/:id | 空对象 (`null`)
 
- 出错无法返回结果时，`result`值为`null` 


有两个简单的json结果示例

- Demo Success
```json
{
  "error": false,
  "errMsg": null,
  "result": {
    "id": "454878-46587-44878-5112",
    "title": "hello,world",
    "description": "desc",
    "completed": true
  }
}
```
- Demo Error
```json
{
  "error": true,
  "errMsg": "can't find the id",
  "result": null
}
```
