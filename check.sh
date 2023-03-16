#!/bin/bash
webapp="$(cat webapp.txt)"
manager="$(cat manager.txt)"
baseurl=$(cat baseurl.txt)

curl -s $baseurl/$webapp/loginVrai
#curl -s $baseurl/$webapp/logout
#curl -s $baseurl/$webapp/registerVrai
#curl -s $baseurl/$webapp/termin
#curl -s $baseurl/$webapp/qrcode
#curl -s $baseurl/$webapp/deleteVrai
#curl -s $baseurl/$webapp/confirmation
#curl -s $baseurl/$webapp/centralVrai
