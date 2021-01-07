# Websend

Websend enables PHP developers to access minecraft commands from a web-based script.
It allows in-game commands to execute PHP scripts and in return also execute actions in-game through PHP scripts.

# Installation

* Put the plugin in the plugin folder.
* Restart the server to allow the configuration file to be generated.
* Stop the server.
* Edit the configuration file.
* Start the server
* run a command as player or console, such as /ws test

# Configuration

Option | Comment
-------|--------
URL=phpURL | URL to send the POST data to when typing a command ingame. You can point it to the minecraft.php in the example files.
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

# Spigot to PHP

This allows you to execute PHP scripts by typing in-game commands. Websend creates a HTTP POST request to a URL you give and parses the response given by PHP.

## Configuration

* Open the “minecraft.php” file included in the Websend download.
* Set the password variable to the password specified in the Websend configuration file.
* Upload this file to your website.
* Open the Websend plugin configuration file.
* Set the "URL=" option to the url of your php file.
* Start your Bukkit server.
* Try command “/ws example”

## Sending a command

Use the command `/ws argument0 argument1` or `/websend argument0 argument1` to start a request from Spigot to PHP.  You can add any number of arguments. These will be passed to your PHP script.

## Data sent by websend

When Websend sends its request it includes information in the POST data.  Most of the data is contained in a JSON string under `$_POST['jsonData']`, unless `GZIP_REQUESTS` is enabled, in which case it is available in gzip form under `$_FILES['jsonData']`. Whether the JSON data is compressed or not is specified under the `$_POST['isCompressed']` variable. The `$_POST['authKey']` variable contains the hashed password and the `$_POST['args'][]` has the arguments.

## Sending a reply

You can respond to the request by simply printing commands with print() or echo. Websend will then parse and execute these commands in the order that they were printed.

ATTENTION: Since Websend 5.x, replies need to be sent in JSON Format and be terminated with CHR(10) which is a linefeed There is no more colon (`;`) needed to terminate. Newer NBT codes in Minecraft use a colon and break the old method of terminating a line with a colon. \n or \r instead of CHR(10) do not work.
Ideally,  this is done with a small PHP Function such as:

````php
<?php
// array key capitalization is relevant.
function websend_command($action, $command = '', $targetPlayer = null) {
    $websend_array = array(
        'action' => $action,  // this is the action you want to do (see list below)
        'targetPlayer' => $targetPlayer, // use this in case you have a target player other than the user who ran the command in-game
        'command' => $command, // this is the command/text to be executed/displayed
    );

    $json = json_encode($websend_array) . CHR(10); // json-encode the array and append the terminating CHR(10) to conclude
    print($json); // finally send this to websend.
}
?>
````

You can execute this function several times to do several things in sequence. Please note that minecraft will do commands with the same action in sequence,
but there is a risk that commands such as ExecuteConsoleCommand take longer than PrintToConsole. So if you execeute a PrintToConsole after a ConsoleCommand, the PrintToConsole might still happen first.

A text send to the player or console can contain a line break with \n. This line break will be executed on the console and on the user screen.

The action needs to be one of the following types, capitalization is not relevant:

*   `PrintToConsole`: Prints whatever the `command` is set to the console. In-game players won't see this. The targetPlayer argument is ignored and should be `null`.
*   `PrintToPlayer`: Prints whatever the `command` is set to as a message to a player currently playing on your server. If you provide targetPlayer, this will be the target, otherwise it will be who
*   `Broadcast`: Broadcasts whatever the `command` is set to to all players currently playing on the server.
*   `ExecutePlayerCommand`: Executes whatever the `command` is set as a player currently playing on your server. By using "targetPlayer", you can specify the player to set as command source, otherwise the command will be ran as the player that started the websend request. This allows you to 'force' a user to execute a command.
*   `ExecuteConsoleCommand`: Executes hatever the `command` is set to as if it was entered directly on the server console.
*   `toggleDebug`: Switches debug on or off, depending what the current status is. `command` is ignored, should ideally be '', targetPlayer needs to be `null`.
*   `executeScript`: Runs a script. The script (specified by `command`) that has to be in the Websend scripts directory and has to be compiled and loaded before this is run.
*   `setResponseURL`: Changes the responseURL as set in the websend config file to whatever is set in `command`. `targetPlayer` needs to be `null`.

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

