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

type Message struct {
	Message string `json:"message"`
}

var users = []User{
	{"1", "Carl Luo", "luohuazju@gmail.com", 100},
	{"2", "Yiyi Kang", "yiyikangrachel@gmail.com", 120},
	{"3", "Leo Luo", "leluozju@gmail.com", 3},
	{"4", "Angela Luo", "angelazju@gmail.com", 1},
}

// @Summary get all items in the list
// @ID get-all-users
// @Produce json
// @Success 200 {object} User
// @Router /api/v1/users [get]
func GetAllUsers(c *gin.Context) {
	c.JSON(http.StatusOK, users)
}

// @Summary get a user by ID
// @ID get-user-by-id
// @Produce json
// @Param id path string true "User ID"
// @Success 200 {object} User
// @Failure 404 {object} Message
// @Router /api/v1/users/{id} [get]
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
	r := Message{"User not found"}
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
		r := Message{"an error occurred while creating todo"}
		c.JSON(http.StatusBadRequest, r)
		return
	}

	// add the new todo item to todoList
	users = append(users, newTodo)
	c.JSON(http.StatusCreated, newTodo)
}

// @Summary add a new user to the list
// @ID create-user
// @Produce json
// @Param data body User true "User data"
// @Success 200 {object} User
// @Failure 400 {object} Message
// @Router /api/v1/users [post]
func CreateUser(c *gin.Context) {
	var newTodo User

	// bind the received JSON data to newTodo
	if err := c.BindJSON(&newTodo); err != nil {
		r := Message{"an error occurred while creating todo"}
		c.JSON(http.StatusBadRequest, r)
		return
	}

	// add the new todo item to todoList
	users = append(users, newTodo)
	c.JSON(http.StatusCreated, newTodo)
}

// @Summary delete a item by ID
// @ID delete-user-by-id
// @Produce json
// @Param id path string true "User ID"
// @Success 200 {object} User
// @Failure 404 {object} Message
// @Router /api/v1/users/{id} [delete]
func DeleteUser(c *gin.Context) {
	ID := c.Param("id")

	// loop through todoList and delete item with matching ID
	for index, todo := range users {
		if todo.Id == ID {
			users = append(users[:index], users[index+1:]...)
			r := Message{"successfully deleted todo"}
			c.JSON(http.StatusOK, r)
			return
		}
	}

	// return error message if todo is not found
	r := Message{"todo not found"}
	c.JSON(http.StatusNotFound, r)
}
