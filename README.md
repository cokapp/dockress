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