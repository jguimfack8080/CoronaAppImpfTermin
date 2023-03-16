#!/bin/bash
baseurl=$(cat baseurl.txt)
manager="$(cat manager.txt)"
echo curl --netrc-file $HOME/.netrc  "$baseurl/$manager/text/list"
curl --netrc-file $HOME/.netrc  "$baseurl/$manager/text/list"

