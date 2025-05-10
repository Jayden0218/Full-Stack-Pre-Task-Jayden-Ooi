#!/bin/bash

# Get the directory of the current script (to handle relative paths)
SCRIPT_DIR=$(dirname "$0")

# Navigate to frontend directory and start development server
echo "Starting frontend development server..."
cd "$SCRIPT_DIR/frontend" || exit
npm run dev &
