#!/bin/bash
SCRIPT_DIR=$(dirname "$(realpath "$0")")

CANDIDATE_NAMES=$1

truffle exec "$SCRIPT_DIR/2_deploy_vote.js" --candidate-names "$CANDIDATE_NAMES" --network ssafy
