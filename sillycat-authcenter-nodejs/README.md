### some pages

http://localhost:8021/api/v1/ping
http://localhost:8021/api-docs/


### commands

```
docker rmi $(docker images | grep "<none>" | awk "{print \$3}")
```
