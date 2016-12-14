package models

import (
	"gopkg.in/mgo.v2"
	"gopkg.in/mgo.v2/bson"
)

type Task struct {
	Name string `json:"name"`
}

type TaskCollection struct {
	Tasks []Task `json:"items"`
}

func GetTasks(db *mgo.Collection) (TaskCollection, error) {
	result := TaskCollection{}
	err := db.Find(nil).All(&result.Tasks)
	if err != nil {
		return result, err
	}
	return result, nil
}

func PutTask(db *mgo.Collection, name string) (bson.ObjectId, error) {
	id := bson.NewObjectId()
	err := db.Insert(&Task{
		Name:name,
	})
	if err != nil {
		return id, err
	}
	return id, nil
}

func DeleteTask(db *mgo.Collection, id bson.ObjectId) (bson.ObjectId, error) {
	err := db.Remove(bson.M{"_id":id})
	if err != nil {
		return id, err
	}
	return id, nil
}