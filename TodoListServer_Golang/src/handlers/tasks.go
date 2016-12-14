package handlers

import (
	"gopkg.in/mgo.v2"
	"github.com/labstack/echo"
	"net/http"
	"models"
	"gopkg.in/mgo.v2/bson"
)

type H map[string]interface {

}

func GetTasks(db *mgo.Collection) echo.HandlerFunc {
	return func(c echo.Context) error {
		result, err := models.GetTasks(db)
		if err == nil {
			return c.JSON(http.StatusOK, result)
		} else {
			return err
		}
	}
}

func PutTask(db *mgo.Collection) echo.HandlerFunc {
	return func(c echo.Context) error {
		var task models.Task
		task.Name = "lhyz"
		c.Bind(&task)
		id, err := models.PutTask(db, task.Name)
		if err == nil {
			return c.JSON(http.StatusCreated, H{
				"created":id,
			})
		} else {
			return err
		}
	}
}

func DeleteTask(db *mgo.Collection) echo.HandlerFunc {
	return func(c echo.Context) error {
		id := bson.ObjectIdHex(c.Param("id"))
		_, err := models.DeleteTask(db, id)
		if err == nil {
			return c.JSON(http.StatusOK, H{
				"deleted":id,
			})
		} else {
			return err
		}
	}
}