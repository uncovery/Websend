<?php
ini_set('display_errors', 1);
ini_set('display_startup_errors', 1);
error_reporting(E_ALL);

global $CONFIG;

$CONFIG['checkpass'] = "PutYourPasswordHere";
$CONFIG['hashAlgorithm'] = "sha512";

$json = websend_connect();
// get the command arguments and return them to the console:
$arguments = $_POST['args'];
websend_command('PrintToConsole', 'you sent the arguments: '. implode(",", $arguments));

/**
 * Basic connection function that checks if everything is properly set up
 *
 * @global array $CONFIG
 * @return type
 */
function websend_connect() {
    global $CONFIG;

    $checkpass = $CONFIG['checkpass'];
    $hashAlgorithm = $CONFIG['hashAlgorithm'];

    $receivedHash = filter_input(INPUT_POST, 'authKey', FILTER_UNSAFE_RAW);
    $is_compressed = filteR_input(INPUT_POST, 'isCompressed', FILTER_SANITIZE_STRING);

    $args = $_POST["args"]; //each argument is stored in an array called "args"

    if ($args[0] == "") {
        websend_fatal_error("Empty argument! Don't just call ws/websend, add more arguments.");
    }

    if ($receivedHash != "") {
        if ($receivedHash == hash($hashAlgorithm, $checkpass)) {
            if ($is_compressed == "true" && isset($_FILES['jsonData']['tmp_name'])){
                $data = gzdecode(file_get_contents($_FILES['jsonData']['tmp_name']));
            } else {
                $data = $_POST["jsonData"];
            }

            $json = json_decode($data, true);
            if ($json == ''){
                websend_fatal_error("Failed to retrieve & decode JSON data!");
            } else {
                return $json;
            }
        } else {
            websend_fatal_error("Invalid hash!");
        }
    } else {
        websend_fatal_error("Empty hash!");
    }
}

/**
 * Just an error function, adjust to whatever you need to happen in case of an
 * error.
 *
 * @param type $msg
 */
function websend_fatal_error($msg) {
    websend_command('PrintToConsole',  'Websend Error: ' . $msg, null);
    error_log("Websend error: " . $msg);
    die();
}

/**
 * Sending replies to the minecraft server when a command has been executed with ws....
 *
 * @param type $action
 * @param type $cmd
 * @param type $player
 */
function websend_command($action, $cmd = '', $target_player = null) {
    $websend_array = array(
        'action' => $action,
        'targetPlayer' => $target_player,
        'command' => $cmd,
    );

    $json = json_encode($websend_array) . CHR(10);
    print($json);
}