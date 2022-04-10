package main

import (
	"log"
	"sillycat-authcenter-golang/config"
	_ "sillycat-authcenter-golang/docs"
	"sillycat-authcenter-golang/health"
	"sillycat-authcenter-golang/user"

	swaggerFiles "github.com/swaggo/files"
	ginSwagger "github.com/swaggo/gin-swagger"

	"github.com/gin-gonic/gin"
	"github.com/spf13/viper"
)

// @title AuthCenter Golang API
// @version 1.0
// @description This is AuthCenter Golang API

// @contact.name API Support
// @contact.url https://sillycat.iteye.com
// @contact.email luohuazju@gmail.com

// @license.name MIT
// @license.url https://opensource.org/licenses/MIT
func SetupRouter() *gin.Engine {
	if viper.GetBool("gin.debug") {
		log.Println("Gin is under debug mode")
	} else {
		log.Println("Gin is under prod mode")
		gin.SetMode(gin.ReleaseMode)
	}
	router := gin.Default()

	router.GET("/docs/*any", ginSwagger.WrapHandler(swaggerFiles.Handler))

	router.GET("/ping", health.Ping)
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
