## Init containers
```
docker compose -f seaweedfs-dev-compose.yml up --build -d
```

## Start containers
```
docker compose -f seaweedfs-dev-compose.yml start
```

## Down all containers
```
docker compose -f seaweedfs-dev-compose.yml down
```

## Refer:
https://github.com/seaweedfs/seaweedfs

https://hub.docker.com/r/confluentinc/cp-zookeeper

https://zookeeper.apache.org/doc/r3.6.3/index.html

https://github.com/seaweedfs/seaweedfs/wiki/s3cmd-with-SeaweedFS


## s3cmd setup:
```
sudo apt install python3-pip
sudo pip3 install s3cmd
```