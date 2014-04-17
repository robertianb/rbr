Alter Script Generator
===

This is a tool to generate alter scripts by comparing 2 database models


Use
==

1. Launch 
2. Open tool's web page
3. Paste 2 databases models (as sql create base scripts)
4. Generate


Configuring & Launching
==
You must change the _supersql.properties_ content to match your IP & port.
You must change the 2 URLs in src/main/resources/index.html to match the IPs set :

_http://yourIp:yourPortNb/crebas2alter/alter_
_http://yourIp:yourPortNb/crebas2alter/rollback_

There are currently 2 main classes to run, each one instnciates an http server:

_supersql.rest.HttpStaticMain_
_supersql.rest.Main_

Open _http://staticHost:staticPort/index.htm_
