const swaggerUi = require('swagger-ui-express');
const swaggerJSDoc = require('swagger-jsdoc');
const path = require("path");

function setSwagger(app) {
    const options = {
        definition: {
            openapi: '3.0.0',
            info: {
                title: 'Auth Center Page',
                version: '1.0.0',
            }
        },
        //swagger-jsdoc scan directory
        apis: [ path.join(__dirname, './*.js') ]
    }
    const swaggerSpec = swaggerJSDoc(options);

    app.get('/swagger.json', function(req, res) {
        res.setHeader('Content-Type', 'application/json');
        res.send(swaggerSpec);
    });
    app.use('/api-docs', swaggerUi.serve, swaggerUi.setup(swaggerSpec));
}

module.exports = setSwagger;