# UNOcard game 

#Motivation
It would be nice to actually make a fully usable product at some point, but so far the project has mostly served as our course assignment requirement. That said, any contributions that move toward the goal of making something useful are most definitely welcomed.
#Objective 
Learn and implement subjects taught through Uno card game with JAVA EE as the backend and html5 chocolatechip ui as the client. 

#Summary of the the techonlogy used

##Back-end
There is no database, data is applicationscoped and stored in memory.
* Jax-rs, jersey
* Javaee Websocket
* JavaEE on Payara server
* Maven

##Front-end
* Javascript
* Websocket
* Jquery
* CSS (sass preprocessor)
* HTML5
* ChocolatechipUI
* Handlebar

##Deployment
Researched various cloud platform like heroku,aws and openshift.
Chosen aws because of the preconfigured docker. We would have used heroku otherwise as its free for single dynos. But we are still figuring out docker configuration for glassfish.

~~Deployed on aws for illustration:
http://default-environment.a2rkfrjd6t.us-west-2.elasticbeanstalk.com/CreateGame.html)
http://default-environment.a2rkfrjd6t.us-west-2.elasticbeanstalk.com/PlayerView.html)
.~~Undeployed to save cost
###What the web app can do :

* Create Game View (support multiple game)
Create game,View created game,Force Start a Game

* Join and play game View (support multiple player to a single game) [heavily depenedent on websocket]
Create Player,Join Game,Start Game,Draw Card,Discard Card


##Look and feel of our webapp:


![alt tag](http://i.imgur.com/4BKYdiw.png?1)

Done by SA42 Team10 : Huang He, Long Bowen , Li Rong, Tan Tian Kian

Even we have completed our submission for our course assignment, there are a few issues that we failed to accomplish in this assignment.
Therefore, to learn from our mistake and ensure we improve ourselves consistently,the project will be still be updated periodically, paused as of september2016 , as we are all busy with our final projects and upcoming internships.

##A summary of the issues with this project:

* Screen is not really designed to be view on mobile device , an issue with css
* Websocket is not working correctly

* Game logic is not implemented

* Create game page and join game page is seperated, does not really feel like a single page application, to be combined in the future.

* Javascript is very messy

* Page was seperated in the beginning because we wanted a seperated project, one for a full jave ee server, other is a html5 application. But to make things simpler, and save the trouble of adding in CORS filter, we just make everything into a single project.
* Websocket does not work on aws deployment 

