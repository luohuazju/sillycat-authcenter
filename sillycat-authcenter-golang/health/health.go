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
