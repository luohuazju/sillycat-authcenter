package config

import (
	"fmt"
	"log"
	"os"

	"github.com/spf13/viper"
)

const appName = "config"

func InitViperConfig() {
	log.Println("Start to init the config--------")
	viper.SetDefault("http.port", "8080")

	if os.Getenv("ENVIRONMENT") == "PROD" {
		log.Println("system is running under PROD mode")
		viper.SetConfigName(appName + "-prod") // name of config file (without extension)
	} else if os.Getenv("ENVIRONMENT") == "STAGE" {
		log.Println("system is running under STAGE mode")
		viper.SetConfigName(appName + "-stage") // name of config file (without extension)
	} else {
		log.Println("system is running under DEV mode")
		viper.SetConfigName(appName + "-dev") // name of config file (without extension)
	}
	viper.AddConfigPath("/etc/" + appName + "/") // path to look for the config file in
	viper.AddConfigPath("../conf/")              // optionally look for config in the working directory
	viper.AddConfigPath("./conf/")
	viper.AutomaticEnv()
	err := viper.ReadInConfig() // Find and read the config file
	if err != nil {             // Handle errors reading the config file
		panic(fmt.Errorf("Fatal error config file: %s \n", err))
	}
}
