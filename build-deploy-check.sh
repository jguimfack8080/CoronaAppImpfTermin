#!/bin/bash
./build.sh && ./deploy.sh && ./check.sh || { echo "something failed" >&2; exit 1; } 

