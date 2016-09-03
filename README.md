# unocard game 

Deployed on aws for illustration

http://default-environment.c7kwpuxtai.us-west-2.elasticbeanstalk.com/CreateGame.html

http://default-environment.c7kwpuxtai.us-west-2.elasticbeanstalk.com/PlayerView.html

It is our course assignment to create a Uno card game with JAVA EE as the backend and html5 chocolatechip ui as the client.
There is no database, data is stored in memory.
The webapp also make use of java ee's websocket and sass css preprocessor.

What the web app can do :

CreateGame.html (support multiple game)
Create game,View created game,Force Start a Game

PlayerView.html (support multiple player to a single game)
Create Player,Join Game,Start Game,Draw Card,Discard Card


Look and feel of our webapp:


![alt tag](http://i.imgur.com/4BKYdiw.png?1)

Done by SA42 Team10 : Huang He, Long Bowen , Li Rong, Tan Tian Kian

Even we have completed our submission for our course assignment, there are a few issues that we failed to accomplish in this assignment.
Therefore, to learn from our mistake and ensure we improve ourselves consistently,the project will be still be updated periodically.

A summary of the issues with this project:

1.Screen is not really designed to be view on mobile device , an issue with css

2.Websocket is not working correctly

3.Game logic is not implemented

4.Player name cannot be customized , to be fixed by in html by moving the text box away from the nav to section

5.Create game page and join game page is seperated, does not really feel like a single page application, to be combined in the future.

It was seperated in the beginning because we wanted a seperated project, one for a full jave ee server, other is a html5 application. 

