package main

import (
	"log"
	"sillycat-authcenter-golang/config"
	"sillycat-authcenter-golang/user"

	"github.com/gin-gonic/gin"
	"github.com/spf13/viper"
)

func SetupRouter() *gin.Engine {
	if viper.GetBool("gin.debug") {
		log.Println("Gin is under debug mode")
	} else {
		log.Println("Gin is under prod mode")
		gin.SetMode(gin.ReleaseMode)
	}
	router := gin.Default()

	// Ping test
	router.GET("/ping", func(c *gin.Context) {
		c.String(200, "pong")
	})

	v1 := router.Group("api/v1")
	{
		v1.GET("/users", user.GetAllUsers)
		v1.GET("/users/:id", user.GetUserByID)
		v1.POST("/users", user.CreateUser)
		v1.PUT("/users/:id", user.UpdateUser)
		v1.DELETE("/users/:id", user.DeleteUser)
	}

	return router
}

func main() {
	config.InitViperConfig()
	router := SetupRouter()
	port := viper.GetString("http.port")
	router.Run(":" + port)
}
