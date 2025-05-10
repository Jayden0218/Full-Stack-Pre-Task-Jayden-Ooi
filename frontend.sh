#!/bin/bash

# Get the directory of the current script (to handle relative paths)
SCRIPT_DIR=$(dirname "$0")

# Navigate to frontend directory
echo "Navigating to frontend directory..."
cd "$SCRIPT_DIR/frontend" || exit

# Install dependencies
echo "Installing frontend dependencies..."
npm install

# Start the frontend development server
echo "Starting frontend development server..."
npm run dev &
