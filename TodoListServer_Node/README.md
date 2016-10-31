# TodoListServer

[![Build Status](https://travis-ci.org/wylhyz/TodoListServer.svg?branch=master)](https://travis-ci.org/wylhyz/TodoListServer)

Node.js 服务端
============
使用MongoDB作为数据库，bootstrap做网页UI模板


RESTful API
============

使用http://localhost:3000/api/v1/ 作为EndPoint（本地运行node.js服务器），
在移动模拟器上（Genymotion上）需要通过在本地查询到的适配器（Adapter）ipv4作为上述localhost的代替

基本实现了简单的CRUD操作

分别是
操作|描述
-----|--------
GET /task | 获取所有数据
GET /task/:id | 获取一条数据
POST /task | 添加一条数据
DELETE /task/:id | 删除一条数据
PUT /task/:id | 更新（修改）一条数据

为了和移动端统一，id使用了UUID生成了唯一标识（mongoDB的ObjectId不在范围内）

### API 文档
位置 [/doc/TodoList接口设计文档.md](./doc/TodoList接口设计文档.md)


# Licence

> Copyright (c) 2016 lhyz Node.js Open Source Project
>
> Licensed under the Apache License, Version 2.0 (the "License");
> you may not use this file except in compliance with the License.
> You may obtain a copy of the License at
>
>     http://www.apache.org/licenses/LICENSE-2.0
>
> Unless required by applicable law or agreed to in writing, software
> distributed under the License is distributed on an "AS IS" BASIS,
> WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
> See the License for the specific language governing permissions and
> limitations under the License.