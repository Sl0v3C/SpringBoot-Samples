# Spring Boot Redis Sample

### Install Redis Server
#### Mac
    # install
    brew install redis
    # start redis server
    redis-server &
    # CLI
    redis-cli

#### Ubuntu
    # install
    sudo apt-get update
    sudo apt-get install redis-server
    # start redis server
    redis-server
    # CLI
    redis-cli

### Spring Redis Configuration
#### Cluster Config
##### Redis Cluster Settings
* mkdir redis-cluster & create six sub folders in it
* copy all redis bin/* files & redis.conf to these sub folders
* copy redis src redis-trib.rb to redis-cluster folder
* modify each redis.conf of the sub folders  
    ```Notice bellow parameters:```  
    ```port 6379 # change the port to the new one. e.g. 7001 ~ 7006```  
    ```daemonize no # change it to yes to run it as daemon```  
    ```cluster-enabled yes # unmark it to enable cluster```  
    ```IMPORTANT! cluster-config-file nodes-6379.conf # modify the nodes-6379.conf to nodes-xxx.conf. e.g. nodes-7001.conf, or it cannot run more than one redis server since the node.conf already in use.```  
    ```pidfile /var/run/redis_6379.pid # change the 6379 to your port. e.g. 7001, not MUST```   

* run the command ```./redis-server ./redis.conf``` in all 6 sub folders
* then exec the command ```./redis-trib.rb create --replicas 1 IP:PORT1 IP:PORT2 IP:PORT3 IP:PORT4 IP:PORT5 IP:PORT6``` in redis-cluster folder to create the redis cluster, it will show logs.  

##### Spring Redis Cluster Config
1. Cluster Property  
    * add host:port cluster info into application-xxx.yaml
    * create ClusterProperties class to get the config info above

2. RedisClusterConfig
    * Autowired the ClusterProperties to get the whole host:port config info
    * Create clusterConnection Bean which return RedisConnectionFactory

3. RedisClusterTest  
    * At first add the SpringBootTest annotation
    * Then add ContextConfiguration annotation
    * Add ActiveProfiles to choose the application-env.yaml, env like test/dev/prod/beta
    * Autowired RedisConnectionFactory clusterConnection
    * setup the RedisClusterConnection redisClusterConnection & Gson gson
    * Check the redisClusterConnection methods

#### Repositories
Redis Repositories requires at least Redis Server version 2.8.0.
##### Entity
    @RedisHash("people")  
    @NoArgsConstructor
    @Id add id member

##### Redis Config
    @EnableRedisRepositories

##### repository
    extends CrudRepository<Object, String>  

##### converter
    @ReadingConverter
    @WritingConverter
    @Override convert

#### Messaging(Pub/Sub)
##### Receiver
    implement receiveMessage callback

##### Publisher
    init RedisTemplate<?, ?> redisTemplate
    call redisTemplate.convertAndSend(channel, message) to send message

##### MessageListener
    add Receiver bean
    add MessageListenerAdapter bean which construct by Receiver bean and receiveMessage callback
    add RedisMessageListenerContainer bean which construct RedisConnectionFactory instance & MessageListenerAdapter bean
    
