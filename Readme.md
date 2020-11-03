# Websend

Websend enables PHP developers to access minecraft commands from a web-based script.
It allows in-game commands to execute PHP scripts and in return also execute actions in-game through PHP scripts.

# Installation

* Put the plugin in the plugin folder.
* Start/reload the server to allow the configuration file to be generated.
* Stop the server.
* Edit the configuration file.

# Configuration


Option | Comment
-------|--------
URL=phpURL | URL to send the POST data to when typing a command ingame.
PASS=password | Password to be used in connections.
WEBLISTENER_ACTIVE=false/true | Set to true to enable PHP -> Spigot connections. Disabled by default.
ALTPORT=portnumber | Enter a portnumber here to use a different TCP port than 4445 for hosting the PHP -> Spigot server.
Does not affect PHP -> Spigot connections.
HASH_ALGORITHM=algorithmname | If set, the specified hashing algorithm is used to hash the password instead of SHA-512.
DEBUG_WEBSEND=false/true | When set to true Websend will output additional output used for debugging. Disabled by default.
GZIP_REQUESTS=false/true | When set to true Websend will gzip the JSON data in a Spigot -> PHP connection. The data is then available in the $_FILES array instead of the regular $_POST. To use the data, read the "jsonData" file and use the gzdecode function to decompress the bytes into a json string. Then load the json using json_decode function. If the data does not show up serverside, check the upload_max_filesize, post_max_size and file_uploads variables in php.ini. Disabled by default.
SERVER_BIND_IP=123.456.789.123 | Use this option to bind the Websend server to a specific ip address.
WRAP_COMMAND_EXECUTOR=true/false | If true, Websend will use additional methods to capture plugin output. 

Note that a server restart is required to apply the changes to the configuration files.

# Bukkit to PHP 

This allows you to execute PHP scripts by typing in-game commands.

## Configuration

Use the command `/ws argument0 argument1` or `/websend argument0 argument1` to start a request from Spigot to PHP. 
