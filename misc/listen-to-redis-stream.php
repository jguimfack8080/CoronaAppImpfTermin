<?php  
function f($redis, $chan, $msg) {
    switch($chan) {
        case 'stream':
            print "got $msg from $chan\n";
            break;
    }
}
ini_set("default_socket_timeout", -1);
 
 
$redis = new Redis();
$redis->pconnect('redis-a',6379);
$redis->auth("foobared");
$redis->subscribe(array('stream'), 'f');
print "\n";
 
?>
