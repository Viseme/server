var express = require('express');
var app = express();
var http = require('http').Server(app);
var bodyParser = require('body-parser');

app.use(express.static(__dirname + '/public'));
app.get('/', function(req, res) {
    res.sendFile(__dirname + '/index.html');
});

app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: true }));

http.listen((process.env.PORT || 3000), function(){
  console.log('listening on *:3000');
});