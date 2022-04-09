const express = require('express');

/**
 * controllers (route handlers). 
 */
const AccountController = require('./controller/account-controller');
const PingController = require('./controller/ping-controller');

const pingController = new PingController();
const accountController = new AccountController();
//routing configuration
var router = express.Router();
/**
 * @swagger
 * /api/v1/ping:
 *   get:
 *     tags:
 *       - ping
 *     summary: ping dong
 *     description: ping dong health check
 *     responses:
 *       200:
 *         description: return dong
 */
router.get('/api/v1/ping', pingController.ping);
router.post('/api/v1/accounts', accountController.add);
router.put('/api/v1/accounts/:id', accountController.update);
router.delete('/api/v1/accounts/:id', accountController.delete);
router.get('/api/v1/accounts', accountController.query);
router.get('/api/v1/accounts/:id', accountController.get);

module.exports = router;



	