#!/bin/bash

# #########################################################################
# This script is initializing the PostgreSQL DB.
# It assumes that:
#    - psql tool is already installed and specified in the PATH
#    - 'sapient/sapient' login role with admin rights and 'sapient' database already exist
# #########################################################################

display_usage() { 
	echo "\tNote: \t*psql* tool needs to be already installed and specified in PATH";
   echo "\t\t 'sapient/sapient' login role with admin rights and 'sapient' database already exist";
   echo "\tUsage: $0 [db-properties-file] \n";
}

function get_property {
    PROP_FILE=$1
    PROP_KEY=$2
    PROP_VALUE=`cat $PROP_FILE | grep "$PROP_KEY" | cut -d'=' -f2`

    echo $PROP_VALUE
}

# check whether user had supplied -h or --help . If yes display usage.
if [[ ( $* == "--help") ||  $* == "-h" ]] ; then 
   display_usage;
	exit 0;
fi 

# if less than one argument supplied, taking the db.properties file as default.
if [  $# -le 0 ] ; then
   EXEC_LOCATION=`dirname $0`
   DB_PROPERTIES_FILE="$EXEC_LOCATION/db.properties";
else
   DB_PROPERTIES_FILE=$1;
fi 

PROP_DB_URL="db.url";
PROP_DB_PORT="db.port";
PROP_DB_NAME="db.name";
PROP_DB_USERNAME="db.username";
PROP_DB_PASSWORD="db.password";

# Make sure the DB_PROPERTIES_FILE exists
if [[ ! -f $DB_PROPERTIES_FILE ]] ; then
   echo "**ERROR: DB properties file named [$DB_PROPERTIES_FILE] hasn't been found";
   exit 1;
fi

DB_URL=`get_property $DB_PROPERTIES_FILE $PROP_DB_URL`
DB_PORT=`get_property $DB_PROPERTIES_FILE $PROP_DB_PORT`
DB_NAME=`get_property $DB_PROPERTIES_FILE $PROP_DB_NAME`
DB_USERNAME=`get_property $DB_PROPERTIES_FILE $PROP_DB_USERNAME`
DB_PASSWORD=`get_property $DB_PROPERTIES_FILE $PROP_DB_PASSWORD`

# User 'sapient/sapient' is the default admin role used to manage the DB
export PGUSER="sapient"
export PGPASSWORD="sapient"

psql -U $PGUSER -c "create user \"$DB_USERNAME\" with password '$DB_PASSWORD'"
psql -U $PGUSER -c "alter user \"$DB_USERNAME\" with superuser"
psql -U $PGUSER -c "create database \"$DB_NAME\" with owner \"$DB_USERNAME\" encoding='utf8' template template0"
