(function() {
    'use strict';

    ngApp.controller('pullImageCtrl', function($scope, $filter, $element, $websocket, CONF, imageSvr) {
        'ngInject';

        $scope.images = [{ "official": true, "star_count": 2726, "is_official": true, "name": "centos", "description": "The official build of CentOS." }, { "official": false, "star_count": 1, "is_official": false, "name": "blacklabelops/centos", "description": "CentOS Base Image! Built and Updates Daily!" }, { "official": false, "star_count": 1, "is_official": false, "name": "darksheer/centos", "description": "Base Centos Image -- Updated hourly" }, { "official": false, "star_count": 0, "is_official": false, "name": "ustclug/centos", "description": " USTC centos" }, { "official": false, "star_count": 39, "is_official": false, "name": "jdeathe/centos-ssh", "description": "CentOS-6 6.8 x86_64 / CentOS-7 7.2.1511 x86_64 - SCL/EPEL/IUS Repos / Supervisor / OpenSSH." }, { "official": false, "star_count": 4, "is_official": false, "name": "nathonfowlie/centos-jre", "description": "Latest CentOS image with the JRE pre-installed." }, { "official": false, "star_count": 16, "is_official": false, "name": "nimmis/java-centos", "description": "This is docker images of CentOS 7 with different versions of java" }, { "official": false, "star_count": 8, "is_official": false, "name": "torusware/speedus-centos", "description": "Always updated official CentOS docker image with Torusware Speedus acceleration software" }, { "official": false, "star_count": 1, "is_official": false, "name": "harisekhon/centos-scala", "description": "Scala + CentOS (OpenJDK tags 2.10-jre7 - 2.11-jre8)" }, { "official": false, "star_count": 12, "is_official": false, "name": "million12/centos-supervisor", "description": "Base CentOS-7 with supervisord launcher, highly extensible." }, { "official": false, "star_count": 3, "is_official": false, "name": "centos/mariadb55-centos7", "description": "" }, { "official": false, "star_count": 0, "is_official": false, "name": "smartentry/centos", "description": "centos with smartentry" }, { "official": false, "star_count": 0, "is_official": false, "name": "januswel/centos", "description": "yum update-ed CentOS image" }, { "official": false, "star_count": 1, "is_official": false, "name": "timhughes/centos", "description": "Centos with systemd installed and running" }, { "official": false, "star_count": 1, "is_official": false, "name": "harisekhon/centos-java", "description": "Java on CentOS (OpenJDK, tags jre/jdk7-8)" }, { "official": false, "star_count": 21, "is_official": false, "name": "jdeathe/centos-ssh-apache-php", "description": "CentOS-6 6.8 x86_64 / Apache / PHP / PHP Memcached / PHP APC." }, { "official": false, "star_count": 0, "is_official": false, "name": "repositoryjp/centos", "description": "Docker Image for CentOS." }, { "official": false, "star_count": 0, "is_official": false, "name": "grayzone/centos", "description": "auto build for centos." }, { "official": false, "star_count": 7, "is_official": false, "name": "nickistre/centos-lamp", "description": "LAMP on centos setup" }, { "official": false, "star_count": 12, "is_official": false, "name": "gluster/gluster-centos", "description": "Official GlusterFS Image [ CentOS7 +  GlusterFS 3.7( latest ) ]" }, { "official": false, "star_count": 2, "is_official": false, "name": "consol/sakuli-centos-xfce", "description": "Sakuli end-2-end testing and monitoring container based on CentOS and Xfce4." }, { "official": false, "star_count": 0, "is_official": false, "name": "kz8s/centos", "description": "Official CentOS plus epel-release" }, { "official": false, "star_count": 89, "is_official": false, "name": "ansible/centos7-ansible", "description": "Ansible on Centos7" }, { "official": false, "star_count": 9, "is_official": false, "name": "jdeathe/centos-ssh-mysql", "description": "CentOS-6 6.8 x86_64 / MySQL." }, { "official": false, "star_count": 2, "is_official": false, "name": "centos/tools", "description": "Docker image that has systems administration tools used on CentOS Atomic host" }];



        $scope.isSearching = false;

        var wsUrl = CONF.SVR_URL.ws;
        var pullManager = getPullManager();

        //拉取镜像
        $scope.pull = function() {
            pullManager.pull($scope.repository);
        };

        $scope.search = function() {
            $scope.isSearching = true;
            imageSvr.search($scope.repository, function(rsp) {
                $scope.images = rsp.data;
                $scope.isSearching = false;
            });
        };

        function getPullManager() {
            var ws = $websocket(wsUrl + '/pull');
            ws.onMessage(function(message) {
                console.log(message);
            });

            return {
                pull: function(repository) {
                    console.log('pull' + repository);
                    ws.send(repository);
                }
            };
        }


    });

})();
