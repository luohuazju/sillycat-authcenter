package user

import (
	"net/http"

	"github.com/gin-gonic/gin"
)

type User struct {
	Id    string `json:"id"`
	Name  string `json:"name"`
	Email string `json:"email"`
	Age   int    `json:"age"`
}

type message struct {
	Message string `json:"message"`
}

var users = []User{
	{"1", "Carl Luo", "luohuazju@gmail.com", 100},
	{"2", "Yiyi Kang", "yiyikangrachel@gmail.com", 120},
	{"3", "Leo Luo", "leluozju@gmail.com", 3},
	{"4", "Angela Luo", "angelazju@gmail.com", 1},
}

func GetAllUsers(c *gin.Context) {
	c.JSON(http.StatusOK, users)
}

func GetUserByID(c *gin.Context) {
	Id := c.Param("id")

	// loop through todoList and return item with matching ID
	for _, todo := range users {
		if todo.Id == Id {
			c.JSON(http.StatusOK, todo)
			return
		}
	}

	// return error message if todo is not found
	r := message{"User not found"}
	c.JSON(http.StatusNotFound, r)
}

func UpdateUser(c *gin.Context) {
	Id := c.Param("id")

	// loop through todoList and delete item with matching ID
	for index, todo := range users {
		if todo.Id == Id {
			users = append(users[:index], users[index+1:]...)
		}
	}
	var newTodo User

	// bind the received JSON data to newTodo
	if err := c.BindJSON(&newTodo); err != nil {
		r := message{"an error occurred while creating todo"}
		c.JSON(http.StatusBadRequest, r)
		return
	}

	// add the new todo item to todoList
	users = append(users, newTodo)
	c.JSON(http.StatusCreated, newTodo)
}

func CreateUser(c *gin.Context) {
	var newTodo User

	// bind the received JSON data to newTodo
	if err := c.BindJSON(&newTodo); err != nil {
		r := message{"an error occurred while creating todo"}
		c.JSON(http.StatusBadRequest, r)
		return
	}

	// add the new todo item to todoList
	users = append(users, newTodo)
	c.JSON(http.StatusCreated, newTodo)
}

func DeleteUser(c *gin.Context) {
	ID := c.Param("id")

	// loop through todoList and delete item with matching ID
	for index, todo := range users {
		if todo.Id == ID {
			users = append(users[:index], users[index+1:]...)
			r := message{"successfully deleted todo"}
			c.JSON(http.StatusOK, r)
			return
		}
	}

	// return error message if todo is not found
	r := message{"todo not found"}
	c.JSON(http.StatusNotFound, r)
}
