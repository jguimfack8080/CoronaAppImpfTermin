#!/bin/bash
webapp=$(cat webapp.txt)
manager=$(cat manager.txt)
baseurl=$(cat baseurl.txt)


curl -s -f -n -T target/$webapp.war "$baseurl/$manager/text/deploy?path=/$webapp&update=true" &&
  echo "deploy success" &&
  exit 0

exit 1

