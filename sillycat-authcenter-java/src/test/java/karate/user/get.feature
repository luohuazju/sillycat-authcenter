Feature: Get Tests on /users

  Background:
    * url baseUrl = 'http://localhost:9527/authcenter'
    * def userBase = '/users/'

  Scenario: Get non-existent user

    Given path userBase + '123456'
    When method GET
    Then status 400
