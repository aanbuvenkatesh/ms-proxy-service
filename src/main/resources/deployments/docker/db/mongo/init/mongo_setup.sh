#!/bin/bash
echo "sleeping for 10 before initiating  replica set"
sleep 10

echo mongo_setup.sh time now: `date +"%T" `
mongo --host ms-proxy-mongo-node1:27017 <<EOF  
  var cfg = {
    "_id": "ms-mongo-set",
    "version": 1,
    "members": [
      {
        "_id": 0,
        "host": "ms-proxy-mongo-node1:27017",
        "priority": 2
      },
      {
        "_id": 1,
        "host": "ms-proxy-mongo-node2:27017",
        "priority": 0
      },
      {
        "_id": 2,
        "host": "ms-proxy-mongo-node3:27017",
        "priority": 0
      }
    ]
  };
  rs.initiate(cfg);
EOF

echo "sleeping for 30 seconds before creating database and collection"
sleep 30

mongo --host ms-proxy-mongo-node1:27017 <<EOF
  rs.status();
  use ms_proxy
  db.createCollection("client");
EOF

echo "sleeping 10 seconds before inserting data in to collection"
sleep 10 
