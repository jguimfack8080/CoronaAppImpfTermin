#!/bin/bash
export REDISCLI_AUTH=foobared
redis-cli --csv -h redis-a subscribe stream | pv --line-mode >/dev/null
