#!/bin/bash

#export http_proxy=""

STARTTIME=$(date +%s)
echo "start to build $2 ..."
curl -v -X GET --header "Accept: application/json" "http://$1/bond-integration/api/tasks/$2"
ENDTIME=$(date +%s)
echo ""
echo "complete. $(($ENDTIME - $STARTTIME)) seconds"
