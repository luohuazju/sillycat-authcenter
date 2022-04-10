package health

import (
	"net/http"

	"github.com/gin-gonic/gin"
)

type Message struct {
	Message string `json:"message"`
}

// @Summary ping health check
// @ID ping
// @Produce json
// @Success 200
// @Router /ping [get]
func Ping(c *gin.Context) {
	r := Message{"Dong"}
	c.JSON(http.StatusOK, r)
}

// @Summary health health check
// @ID health
// @Produce json
// @Success 200
// @Router /health [get]
func Health(c *gin.Context) {
	token := c.Request.Header["Authorization"]
	tokenStr := "Dong"
	if token != nil {
		tokenStr = token[0]
	}
	r := Message{tokenStr}
	c.JSON(http.StatusOK, r)
}
