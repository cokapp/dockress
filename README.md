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

docker run -d swarm join --addr=192.168.187.1:2375 token://8808c5dda8589db26b29b876756e411e

docker run -d -p 2376:2375 swarm manage --cors token://8808c5dda8589db26b29b876756e411e

docker run --rm swarm list token://8808c5dda8589db26b29b876756e411e

docker -H 192.168.187.129:2376 info
```