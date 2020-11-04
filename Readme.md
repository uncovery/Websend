
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

Note that a server restart is required to apply the changes to the configuration files.


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


These replies can be sent in the format of



    By using "-playername", you can specify the player to send the message to, otherwise the message will be sent to the player that started the websend request.
    Examples: `/Command/ExecutePlayerCommand: time set 0;`, `/Command/ExecutePlayerCommand-notch: time set 0;`
*   `/Command/ExecuteConsoleCommand :command arguments;`
    Prints to text to a player currently playing on your server.
    Example: `/Command/ExecuteConsoleCommand: time set 0;`
*   `/Command/ExecuteScript :scriptname;`
    Runs a script. The script has to be in the Websend scripts directory and has to be compiled and loaded before this is run.
    Example: `/Command/ExecuteScript: scriptname;`


# PHP to Spigot
You can initiate a command to the server via a PHP script as well. For this, you need the `Websend.php` file from the plugin directory. It contains a class that connects to the minecraft server and manages the communication. Further, you need a PHP script that loads the class and executes a command. There is an example `ExternalTimeSet.php` in the plugin directory.

The base process is as follows:
````php
    <?php
    // you include the Websend class
    require_once 'Websend.php';
    // You connect to the IP/DNS of your minecraft server
    $ws = new Websend("172.0.0.1");
    // You set the password as configured in the plugin config
    $ws->password = "websendpassword";

   // you attempt to connect
   if($ws->connect()) {
        // execute an action on the server
        $check = $ws->doCommandAsConsole("time set 6000");
        // the check is returning true or false
        if (!$check) {
            echo "Failure to set time";
        } else {
            echo "Time set.";
        }
   } else {
        echo "Failed to connect.";
   }
   $ws->disconnect();
````
There are several commands that can be executed on the minecraft server, similar to the Spigot->PHP direction:

* `$ws->doCommandAsConsole($cmd);`:  Executes a command on the console
* `$ws->doCommandAsPlayer($cmd, $player);`: Executes a command as if done by a player
* `$ws->writeOutputToConsole($cmd);`: Writes text to the console
* `$ws->writeOutputToPlayer($cmd, $player);`: Writes text to a player
* `$ws->broadcast($cmd);`: Broadcasts text to all players
* `$ws->doScript($cmd);`: Executes a script.

