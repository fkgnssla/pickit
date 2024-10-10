#!/bin/bash
#SCRIPT_DIR=$(dirname "$(realpath "$0")")

CANDIDATE_NAMES=$1
START_TIME=$2
END_TIME=$3

#truffle exec "$SCRIPT_DIR/2_deploy_vote.js" --candidate-names="$CANDIDATE_NAMES" --network ssafy
truffle exec "2_deploy_vote.js" --candidate-names="$CANDIDATE_NAMES" --start-time="$START_TIME" --end-time="$END_TIME" --network ssafy
