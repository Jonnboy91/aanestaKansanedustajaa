<?php
// https://github.com/tuoku helped with the PHP side! I used his base and learned what everything does and edited it after that to fit my purposes
// mysql info are retrieved from config
include ('/home1-3/j/jonne/config/config.php');

// separate parameters from the URL
$url = $_SERVER['REQUEST_URI'];
$url_comps = parse_url($url);
parse_str($url_comps['query'], $params);

// connecting to the database with the retrieved info
$con = mysqli_connect($mysql_host, $mysql_user, $mysql_pw, $mysql_db);

$comments = array();
$id = intval($params['id']);
$text = strval($params['comment']);

// If the connection succeeded
if($con){
    switch($params['action']) {
    case "getall":
        $sql = "SELECT * FROM `comment_database`";
        $res = mysqli_query($con,$sql);
        while ($row = mysqli_fetch_assoc($res)) {
            header("Content-Type: JSON");
            $comments[] = $row;
        }
        echo json_encode($comments,JSON_PRETTY_PRINT);
        break;
    case "addcomment":
        $sql = "INSERT INTO `comment_database` (`hetekaID`, `comment`) VALUES ($id, '$text')";
        $res = mysqli_query($con,$sql);
        if($res){
            echo json_encode(array("message" => "success"));
        }
        break;
    }

// If the connection failed
} else {
    echo "Failed to connect";
}
?>