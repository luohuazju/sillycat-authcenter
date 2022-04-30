Feature: Create and Read persons ...

  Background:
    * url baseUrl = 'http://localhost:9527/authcenter'
    * def userBase = '/users/'

  Scenario: Create a user

    Given path userBase
    And request { id: 3, loginName: 'kiko', email: 'kiko@gmail.com' }
    And header Accept = 'application/json'
    When method post
    Then status 200

  Scenario: Get user we just created
	* header Authentication = '111111'
    Given path userBase + '3'
    When method GET
    Then status 200
    And match response == { id: 1, loginName: 'luohuazju', email: 'luohuazju@gmail.com' }