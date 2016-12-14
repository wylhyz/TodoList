package main

import (
	"github.com/labstack/echo"
	"gopkg.in/mgo.v2"
	"github.com/labstack/gommon/log"
)

import "handlers"

func main() {
	//数据库
	db := initDB()
	db.Insert()

	e := echo.New()

	e.File("/", "public/index.html")
	e.GET("/tasks", handlers.GetTasks(db))
	e.PUT("/tasks", handlers.PutTask(db))
	e.DELETE("/tasks/:id", handlers.DeleteTask(db))
	e.Logger.Fatal(e.Start(":8080"))

}

func initDB() *mgo.Collection {
	session, err := mgo.Dial("localhost:27017")
	if err != nil {
		log.Fatal(err)
	}
	c := session.DB("todo-list").C("tasks")
	return c
}