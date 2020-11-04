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

This allows you to execute PHP scripts by typing in-game commands. Websend creates a HTTP POST request to a URL you give and parses the response given by PHP.

## Configuration

* Open the “minecraft.php” file included in the Websend download.
* Set the password variable to the password specified in the Websend configuration file.
* Upload this file to your website.
* Open the Websend plugin configuration file.
* Set the "URL=" option to the url of your php file.
* Start your Bukkit server.
* Try command “/ws example”

## Data sent by websend

When Websend sends its request it includes information in the POST data.  Most of the data is contained in a JSON string under `$_POST['jsonData']`, unless `GZIP_REQUESTS` is enabled, in which case it is available in gzip form under `$_FILES['jsonData']`. Whether the JSON data is compressed or not is specified under the `$_POST['isCompressed']` variable. The `$_POST['authKey']` variable contains the hashed password and the `$_POST['args'][]` has the arguments.

## Sending a reply

You can repond to the request by simply printing commands with print() or echo. Websend will then parse and execute these commands in the order that they were printed. Each command is seperated by a semi-colon (;).

## Output

* PrintToConsole :Text;
  Print text to console.
  Ex: PrintToConsole_: Message from Websend;
* PrintToPlayer [-playername]:Text;
    Prints to text to a player currently playing on your server.
    By using "-playername", you can specify the player to send the message to, otherwise the message will be sent to the player that started the websend request.
    Ex: PrintToPlayer: Message from Websend;
    Ex: PrintToPlayer-notch: Message from Websend;
    Broadcast :Text;
    Broadcast a message to all players currently playing on the server.
    Ex: Broadcast: Message from Websend;

## PHP Commands

These commands can be sent back to spigot in order to execute something on the server. This happens in the format of

    print("/Command/ExecuteConsoleCommand: msg playername hello there!;");
  
That for exampple would send an in-game message to the player "playername".
Here are all the commands:

*   `ExecutePlayerCommand [-playername]:command arguments;` 
    Executes a command as a player currently playing on your server. By using "-playername", you can specify the player to set as command source, otherwise the command will be ran as the player that started the websend request. This allows you to 'force' a user to execute a command.
    Examples: `ExecutePlayerCommand: time set 0;`, `ExecutePlayerCommand-notch: time set 0;`
*   `ExecuteConsoleCommand :command arguments;`
    Prints to text to a player currently playing on your server.
    Example: `ExecuteConsoleCommand: time set 0;`
*   `ExecuteScript :scriptname;`
    Runs a script. The script has to be in the Websend scripts directory and has to be compiled and loaded before this is runned.
    Example: `ExecuteScript: scriptname;`



Use the command `/ws argument0 argument1` or `/websend argument0 argument1` to start a request from Spigot to PHP. 
