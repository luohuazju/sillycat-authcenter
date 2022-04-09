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
 * definitions:
 *   Account:
 *     properties:
 *       name:
 *         type: string
 *       email:
 *         type: string
 *       age:
 *         type: integer
 */
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
/**
 * @swagger
 * /api/v1/accounts:
 *   post:
 *     tags:
 *       - account
 *     description: Create a new Account
 *     produces:
 *       - application/json
 *     parameters:
 *       - name: account
 *         description: Account Object
 *         in: body
 *         required: true
 *         schema:
 *           $ref: '#/definitions/Account'
 *     responses:
 *       200:
 *         description: Successfully created
 */
router.post('/api/v1/accounts', accountController.add);
/**
 * @swagger
 * /api/v1/accounts/{id}:
 *   put:
 *     tags:
 *       - account
 *     description: Updates a single account
 *     produces:
 *       - application/json
 *     parameters:
 *       - name: id
 *         description: Account's id
 *         in: path
 *         required: true
 *         type: integer
 *       - name: account
 *         description: Account object
 *         in: body
 *         required: true
 *         schema:
 *           $ref: '#/definitions/Account'
 *     responses:
 *       200:
 *         description: Successfully updated
 */
router.put('/api/v1/accounts/:id', accountController.update);
/**
 * @swagger
 * /api/v1/accounts/{id}:
 *   delete:
 *     tags:
 *       - account
 *     description: Deletes a single account
 *     produces:
 *       - application/json
 *     parameters:
 *       - name: id
 *         description: Account's id
 *         in: path
 *         required: true
 *         type: integer
 *     responses:
 *       200:
 *         description: Successfully deleted
 */
router.delete('/api/v1/accounts/:id', accountController.delete);
/**
 * @swagger
 * /api/v1/accounts:
 *   get:
 *     tags:
 *       - account
 *     descriptions: Return all accounts
 *     produces:
 *       - application/json
 *     responses:
 *       200:
 *         description: an array of accounts 
 *         schema:
 *           $ref: '#/definitions/Account'
 */
router.get('/api/v1/accounts', accountController.query);
/**
 * @swagger
 * /api/accounts/{id}:
 *   get:
 *     tags:
 *       - account
 *     description: Returns a single Account
 *     produces:
 *       - application/json
 *     parameters:
 *       - name: id
 *         description: account's id
 *         in: path
 *         required: true
 *         type: integer
 *     responses:
 *       200:
 *         description: A single Account
 *         schema:
 *           $ref: '#/definitions/Account'
 */
router.get('/api/v1/accounts/:id', accountController.get);

module.exports = router;



	