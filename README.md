#dockress

#Docker配置【ubuntu】

##/lib/systemd/system/docker.service
```
[Service]
Type=notify
EnvironmentFile=-/etc/default/docker
ExecStart=/usr/bin/docker daemon -H fd:// -H tcp://0.0.0.0:2375 $OPTIONS
```

##/etc/default/docker
```
OPTIONS=--registry-mirror=https://lpe2ci8q.mirror.aliyuncs.com --api-enable-cors --api-cors-header=*
```

##swarm配置
```
docker run -d swarm join --addr=192.168.187.129:2375 token://8808c5dda8589db26b29b876756e411e

docker run -d swarm join --addr=192.168.99.100:2376 token://8808c5dda8589db26b29b876756e411e

docker run -d -p 2376:2375 swarm manage --cors token://8808c5dda8589db26b29b876756e411e

docker run --rm swarm list token://8808c5dda8589db26b29b876756e411e

docker -H 192.168.187.129:2376 info
```



#docker-machine配置swarm

##新建docker manager
```
docker-machine create \
    -d virtualbox \
    --swarm \
    --swarm-master \
    --swarm-discovery token://ecbf757e72f2f28458c9ce0bf42eaff8 \
    --engine-registry-mirror=https://lpe2ci8q.mirror.aliyuncs.com \
    swarm-manager
```
##新建docker agent
```
docker-machine create \
    -d virtualbox \
    --swarm \
    --swarm-discovery token://ecbf757e72f2f28458c9ce0bf42eaff8 \
    --engine-registry-mirror=https://lpe2ci8q.mirror.aliyuncs.com \
    swarm-agent1

docker-machine create \
    -d virtualbox \
    --swarm \
    --swarm-discovery token://ecbf757e72f2f28458c9ce0bf42eaff8 \
    --engine-registry-mirror=https://lpe2ci8q.mirror.aliyuncs.com \
    swarm-agent2
```

#swarm管理端口3376
docker run -d swarm manage -H 0.0.0.0:4000 --cors token://ecbf757e72f2f28458c9ce0bf42eaff8
docker run -d swarm join --addr=192.168.99.100:2376 token://ecbf757e72f2f28458c9ce0bf42eaff8

#swarm node1
docker run -d swarm join --addr=192.168.99.101:2376 token://ecbf757e72f2f28458c9ce0bf42eaff8

#swarm node2
docker run -d swarm join --addr=192.168.99.102:2376 token://ecbf757e72f2f28458c9ce0bf42eaff8


#list
docker run --rm swarm list token://ecbf757e72f2f28458c9ce0bf42eaff8

docker -H 192.168.99.100:3376 info

```


docker run -p 180:80 -v /mnt/hgfs/dockress/client:/root/dockress/client -d dockress

docker run -p 980:80 dockress:v9







