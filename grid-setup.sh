#!/bin/bash

# Constants
COMPOSE_FILE="docker-compose-v3-dynamic-grid.yml"

# Functions
start_grid() {
  echo "Starting Selenium Grid..."
  docker-compose -f $COMPOSE_FILE up -d
  echo "Selenium Grid started. Accessible at http://localhost:4444"
}

stop_grid() {
  echo "Stopping Selenium Grid..."
  docker-compose -f $COMPOSE_FILE down
  echo "Selenium Grid stopped."
}

scale_nodes() {
  NODE_TYPE=$1
  COUNT=$2
  echo "Scaling $NODE_TYPE to $COUNT nodes..."
  docker-compose -f $COMPOSE_FILE up --scale $NODE_TYPE=$COUNT -d
  echo "Scaled $NODE_TYPE to $COUNT nodes."
}

usage() {
  echo "Usage: $0 {start|stop|scale}"
  echo "Commands:"
  echo "  start          Start the Selenium Grid"
  echo "  stop           Stop the Selenium Grid"
  echo "  scale <node> <count>  Scale the specified node type (e.g., chrome-node) to the given count"
  exit 1
}

# Command Handling
case $1 in
  start)
    start_grid
    ;;
  stop)
    stop_grid
    ;;
  scale)
    if [[ -z $2 || -z $3 ]]; then
      usage
    else
      scale_nodes $2 $3
    fi
    ;;
  *)
    usage
    ;;
esac
