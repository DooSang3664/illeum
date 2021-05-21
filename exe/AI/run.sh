#!/bin/bash
  
# turn on bash's job control
set -m

# Start the helper process and put it in the background
python ./models/TTLStatusCounter.py &

# wait helper process socket open...
sleep 5

# Start the main process
uvicorn main:app --workers=4 --no-access-log --http=httptools --host=0.0.0.0 --port=8000