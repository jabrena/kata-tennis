# Tennis KATA


## running test
Unit tests can be run from IDE
TennisGameResourceTestIT only works with a runing mongoDB

## Running the kata-tennis
You need a mongoDB to process this action
complete the apllication.yml with local information
run command 'gradle bootRun'
the server runs by default on port : 8080

## API:
a postman example is set in 'src/test/resources/kata-tennis.postman_collection.json'
call at first POST /game to create a new game
call PUT/game/{idGame}/score/{player} to score a point for a chosen player
call GET /game/{idGame} to get the current status of the game, and the score