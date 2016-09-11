# Seat reservation service
This is a restful service which provides endpoints to list/hold/reserve seats for the show. If there is only one instance of this service running, concurrent requests would not double book the same seat for multiple reservation. I have added a seat locking mechanism in memory which would facilitate this functionality. All the seat holds / reservations are also stored in memory. 

# Assumptions
  - The actual problem was about booking seats in the venue. I have slightly modified this requirement to support booking seats for various shows in the venue.
# Installation
    Add mvn commands

## List Seats
This is the endpoint to list the seats based on the requested status  
URI : http://localhost:8080/shows/{showId}/Seats?status={status}   
values for showId : any integer  
values for status: AVAILABLE/HELD/RESERVED  
Eg:  
GET /shows/1/Seats?status=AVAILABLE HTTP/1.1  
Host: localhost:8080  
Cache-Control: no-cache  
Postman-Token: ddcb613f-5625-cbf9-735d-399b07e6eff5
## Hold Seats
This is the end point to hold the seats on the behalf of the customer. Response header will have the unique seat Hold Identification number (UUID)  
URI : http://localhost:8080/shows/{showId}/holdSeats    
values for showId : any integer  
The posted content (application/json) should have customer's email and number of seats to hold.  
Eg:  
POST /shows/2/holdSeats HTTP/1.1  
Host: localhost:8080  
Content-Type: application/json  
Cache-Control: no-cache  
Postman-Token: ed015d02-07da-e5e4-83b2-6c1486bdd5f5  
{  
   "emailId":"p2333dp@gmail.com",  
   "numberOfSeatsToHold":"34"  
}  
## Reserve Seats  
This is the endpoint to reserve the seats which were previously held by the Hold Seats service  
URI: http://localhost:8080/shows/{showId}/reserveSeats/{seatHoldId}
values for showId : any integer  
value of seatHoldId : seatHoldId from hold Seats Service Request  
Eg:  
PUT /shows/2/reserveSeats/858fd0aa-1d04-4ccc-bf32-511452d5f60e HTTP/1.1  
Host: localhost:8080  
Cache-Control: no-cache  
Postman-Token: 21197b36-da8c-6fd5-a335-0e8a4b04cc91  
Content-Type: multipart/form-data; boundary=----WebKitFormBoundary7MA4YWxkTrZu0gW

## Postman Collection link
https://www.getpostman.com/collections/5d1f72c4a934628433cd





    
## Installation



  - Type some Markdown on the left
  - See HTML in the right
  - Magic

You can also:
  - Import and save files from GitHub, Dropbox, Google Drive and One Drive
  - Drag and drop files into Dillinger
  - Export documents as Markdown, HTML and PDF

Markdown is a lightweight markup language based on the formatting conventions that people naturally use in email.  As [John Gruber] writes on the [Markdown site][df1]

> The overriding design goal for Markdown's
> formatting syntax is to make it as readable
> as possible. The idea is that a
> Markdown-formatted document should be
> publishable as-is, as plain text, without
> looking like it's been marked up with tags
> or formatting instructions.

This text you see here is *actually* written in Markdown! To get a feel for Markdown's syntax, type some text into the left window and watch the results in the right.

### Tech

Dillinger uses a number of open source projects to work properly:

* [AngularJS] - HTML enhanced for web apps!
* [Ace Editor] - awesome web-based text editor
* [markdown-it] - Markdown parser done right. Fast and easy to extend.
* [Twitter Bootstrap] - great UI boilerplate for modern web apps
* [node.js] - evented I/O for the backend
* [Express] - fast node.js network app framework [@tjholowaychuk]
* [Gulp] - the streaming build system
* [keymaster.js] - awesome keyboard handler lib by [@thomasfuchs]
* [jQuery] - duh

And of course Dillinger itself is open source with a [public repository][dill]
 on GitHub.

### Installation

Dillinger requires [Node.js](https://nodejs.org/) v4+ to run.

Download and extract the [latest pre-built release](https://github.com/joemccann/dillinger/releases).

Install the dependencies and devDependencies and start the server.

```sh
$ cd dillinger
$ npm install -d
$ node app
```

For production environments...

```sh
$ npm install --production
$ npm run predeploy
$ NODE_ENV=production node app
```

### Plugins

Dillinger is currently extended with the following plugins

* Dropbox
* Github
* Google Drive
* OneDrive

Readmes, how to use them in your own application can be found here:

* [plugins/dropbox/README.md] [PlDb]
* [plugins/github/README.md] [PlGh]
* [plugins/googledrive/README.md] [PlGd]
* [plugins/onedrive/README.md] [PlOd]

### Development

Want to contribute? Great!

Dillinger uses Gulp + Webpack for fast developing.
Make a change in your file and instantanously see your updates!

Open your favorite Terminal and run these commands.

First Tab:
```sh
$ node app
```

Second Tab:
```sh
$ gulp watch
```

(optional) Third:
```sh
$ karma start
```
#### Building for source
For production release:
```sh
$ gulp build --prod
```
Generating pre-built zip archives for distribution:
```sh
$ gulp build dist --prod
```
### Docker
Dillinger is very easy to install and deploy in a Docker container.

By default, the Docker will expose port 80, so change this within the Dockerfile if necessary. When ready, simply use the Dockerfile to build the image.

```sh
cd dillinger
npm run-script build-docker
```
This will create the dillinger image and pull in the necessary dependencies. Moreover, this uses a _hack_ to get a more optimized `npm` build by copying the dependencies over and only installing when the `package.json` itself has changed.  Look inside the `package.json` and the `Dockerfile` for more details on how this works.

Once done, run the Docker image and map the port to whatever you wish on your host. In this example, we simply map port 8000 of the host to port 80 of the Docker (or whatever port was exposed in the Dockerfile):

```sh
docker run -d -p 8000:8080 --restart="always" <youruser>/dillinger:latest
```

Verify the deployment by navigating to your server address in your preferred browser.

```sh
127.0.0.1:8000
```

#### Kubernetes + Google Cloud

See [KUBERNETES.md](https://github.com/joemccann/dillinger/blob/master/KUBERNETES.md)


#### docker-compose.yml

Change the path for the nginx conf mounting path to your full path, not mine!

### N|Solid and NGINX

More details coming soon.


### Todos

 - Write Tests
 - Rethink Github Save
 - Add Code Comments
 - Add Night Mode

License
----

MIT


**Free Software, Hell Yeah!**

[//]: # (These are reference links used in the body of this note and get stripped out when the markdown processor does its job. There is no need to format nicely because it shouldn't be seen. Thanks SO - http://stackoverflow.com/questions/4823468/store-comments-in-markdown-syntax)


   [dill]: <https://github.com/joemccann/dillinger>
   [git-repo-url]: <https://github.com/joemccann/dillinger.git>
   [john gruber]: <http://daringfireball.net>
   [@thomasfuchs]: <http://twitter.com/thomasfuchs>
   [df1]: <http://daringfireball.net/projects/markdown/>
   [markdown-it]: <https://github.com/markdown-it/markdown-it>
   [Ace Editor]: <http://ace.ajax.org>
   [node.js]: <http://nodejs.org>
   [Twitter Bootstrap]: <http://twitter.github.com/bootstrap/>
   [keymaster.js]: <https://github.com/madrobby/keymaster>
   [jQuery]: <http://jquery.com>
   [@tjholowaychuk]: <http://twitter.com/tjholowaychuk>
   [express]: <http://expressjs.com>
   [AngularJS]: <http://angularjs.org>
   [Gulp]: <http://gulpjs.com>

   [PlDb]: <https://github.com/joemccann/dillinger/tree/master/plugins/dropbox/README.md>
   [PlGh]:  <https://github.com/joemccann/dillinger/tree/master/plugins/github/README.md>
   [PlGd]: <https://github.com/joemccann/dillinger/tree/master/plugins/googledrive/README.md>
   [PlOd]: <https://github.com/joemccann/dillinger/tree/master/plugins/onedrive/README.md>

