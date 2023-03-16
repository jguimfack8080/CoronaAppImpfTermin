#!/bin/bash
webapp=$(cat webapp.txt)
baseurl=$(cat baseurl.txt)
manager=$(cat manager.txt)
[ "$1" != "" ] && { webapp=$1; }

curl --netrc-file $HOME/.netrc "$baseurl/$manager/text/undeploy?path=/$webapp"

