# DiceGame-Springboot-Api-JWT-Auth-Security

Spring boot Game (MySql & Mongodb)

The dice game is played with two dice. If the result of the sum of the two dice is 7, the game is won, otherwise it is lost. A player can view a list of all the rolls they have made and their success rate.

To play the game and make a roll, a user must register with a unique name. Upon creation, a unique numerical identifier and registration date are assigned. If the user desires, they can add no name and will be referred to as "ANONYMOUS". There can be more than one "ANONYMOUS" player. Each player can view a list of all their rolls, with the value of each die and whether the game was won or lost. In addition, they can know their success rate for all the rolls they have made.

A specific game cannot be deleted, but a player's roll list can be deleted.

The software must allow listing all players in the system, each player's success rate and the average success rate of all players in the system.

The software must comply with the main design patterns.

How to use it:

1) Clone the repository and create a new project in your IDE software
2) Open your IDE software and clone from version control
3) Open MySql or Mongodb database (depends on your IDE software) and create a new database with the name specified
4) First of all, Spring Security transfer information between two parties, that means any two users or any two servers. 5)In that project I decided to use Json Web Token as spring security technology. It must be said that in spring there are other ways to implement security, such as AuthO, it will depend on each programmer to choose the most appropriate option for their project
5) At this point we must understand that Spring security has two basic actions that are Authenticate and Authorize the users who will be able to access our server.
6) Now it's time to run the program
7) Check AuthController class, in this class we implement the endpoints for authentication and authorization
8) How should we authenticate the users? we will be able to access our server and their password, password will be stored in the database and returned to the user who will be able to access our server again
9) Open Postman database and configure your request accordingly with your sever configuration ex.(Http://localhost:8080/register) if the registration is successful then we receive a Json Web Token (save your client token for future use)
10) Next step is to login the user with your account information, we need to use the corresponding parameters in the body of the request and the token should be inserted into the authorization, if the user is successfully logged in then we receive a response from the server like this: user is logged in
11) We already have our user created and authorized, if you have followed the steps up to this point, you should have access to the game and be able to test its functionalities
12) Check GameController class, as you can see, in this class we can review all the endpoints created, we can see that in addition to the basic crud methods we have defined new methods adapted to the typology of the project
13) It's time to play, start adding a player to the game and roll the dices

    Good luck and enjoy!