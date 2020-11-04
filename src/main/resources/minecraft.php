<?php
global $CONFIG;

$CONFIG['checkpass'] = "PutYourPasswordHere";
$CONFIG['hashAlgorithm'] = "sha512";

$json = websend_connect();

/**
 * Basic connection function that checks if everything is properly set up
 *
 * @global array $CONFIG
 * @return type
 */
function websend_connect() {
    global $CONFIG;

    $checkpass = $CONFIG['checkpass'];
    $hashAlgorithm =$CONFIG['hashAlgorithm'];

    $receivedHash = $_POST['authKey'];

    $args = $_POST["args"]; //each argument is stored in an array called "args"

    if ($args[0] == "") {
        websend_fatal_error("Empty argument!");
    }

    if ($receivedHash != "") {
        if ($receivedHash == hash($hashAlgorithm, $checkpass)) {
            if($_POST['isCompressed'] == "true" && isset($_FILES['jsonData']['tmp_name'])){
                $data = gzdecode(file_get_contents($_FILES['jsonData']['tmp_name']));
            } else {
                $data = $_POST["jsonData"];
            }

            $json = json_decode($data, true);
            if ($json == ''){
                websend_fatal_error("Failed to retrieve JSON data!");
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
    print('/Output/PrintToConsole: Websend Error:' . $msg . ';');
    error_log("Websend error: " . $msg);
    die();
}

