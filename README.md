#Node for Android

## Overview
Node4Android is a library porting of Node 0.8.8. It's an android library project of Eclipse.

## How to build
Build it with Eclipse JUNO

## Design
First of all. Node4Android is a part of [ShuttlePlay](http://shuttleplay.net).
ShuttlePlay is designed as a game framework. So fro code you don't need. just remove it.

ShuttlePlay is intended to run a web game on mobile device. It's not a web game client but a
server. The web server is established with node.js code. So for game developer, both to frontend
and backend implementation, javascript knownledge is OK.

### LaunchActivity
Defaultly, The Node4Android is launched by "LaunchActivity", please find a sample in 
[Chinese Chess](https://github.com/zhentao-huang/cchess). 
In the first time, LaunchActivity is try to find **/res/raw/web.zip**, and extract it in 
the files folder of your andorid app. The phase is called "deploy"

**/res/raw/web.zip** should contains these files:
 * config.prop
 * node
 * web

The config.prop defines the game's basic settings. 
 * port -- The HTTP port of the game. http uses 80, but for non-root priviledge app. the port number should bigger than 1024
 * main -- The main file of node.js code. what should node4android launched.
 * index -- The main page of local web client should load
 * runlabel -- The title of the notificaiton bar should used
 * sdfolder -- The sdcard folder for saving game data.
 * debug -- if true, debug "main" node.js

LaunchActivity would then launch node.js code by invoking "NodeJsService" interface. 
and send intent to let browser in the device to handle the URL link "http://<local wifi ip address>:<port>/<index>"
Finally LaunchActivity finish it self. so actually it's invisible.

LaunchActivity either handle exit event. If it received a intent called "net.shuttleplay.node.ShutDown", it would then
do cleanup and cancel node.js's running.

### NodeService
NodeService is backend service for keep node.js web server running and not be killed by system. It would be a foreground
service. Hence user can see a notification on the status bar.

As default the status bar should has an icon, a title and two buttons "about" and "exit". the resource isn't defined in the node4android but it
depends on it host android project. so for the host project, these resource should be provided.
 * /res/layout/notify.xml -- the layout of the notification bar.
 * /res/value/strings.xml -- strings, at least should be 3, "app_name", "exit" and "about" buttons' title
 * for others, please refer to /res folder in project [Chinese Chess](https://github.com/zhentao-huang/cchess).
For "about" button, an http request would be sent to "http://<local wifi ip address>:<port>/about.html"
For "exit" button, an intent "net.shuttleplay.node.ShutDown" would be sent, actually it would be handled by LaunchActivity

Beside this, NodeService provide NodeJsService interface to launch and debug node.js.
 * launchInstance(in String file) -- just like "node file" in command line
 * debugInstance(in String file) -- just like "node --debug-brk file" in command line. 
Please note no other arguments is allowed for these two methods

### Node.js porting
The porting just work. And it's not just a porting. I'd add one feature for the node.
 * process.str2qr(string) -- Convert a string be a png stream of QR code.
To implement this feature, I includes two additional libraries
 * libpng
 * qrencode
QR code enable other players' devices connect to the shuttleplay game easier

## License
The project is conform MIT License

## Contact me
Any comments please send me a mail to zhentao_huang#hotmail.com, 
Please replace # with @

## Test for images
![Use cases][imgs/scanengine_usecase.png]
[Use cases](imgs/scanengine_usecase.svg)
