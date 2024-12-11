package cn.edu.xmu.javaee.order.config;

import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.ReadPreference;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
public class MongoConfig extends AbstractMongoClientConfiguration {

    @Value("${spring.data.mongodb.hosts}")
    private String hosts;

    @Value("${spring.data.mongodb.replica-set}")
    private String replicaSet;

    @Value("${spring.data.mongodb.database}")
    private String database;

    @Value("${spring.data.mongodb.username}")
    private String username;

    @Value("${spring.data.mongodb.password}")
    private String password;


    @Override
    protected String getDatabaseName() {
        return this.database;
    }

    @Override
    public MongoClient mongoClient() {

        // 解析主机列表
        List<ServerAddress> serverAddresses = Arrays.stream(hosts.split(","))
                .map(host -> {
                    String[] hostPort = host.split(":");
                    return new ServerAddress(hostPort[0], Integer.parseInt(hostPort[1]));
                })
                .collect(Collectors.toList());

        // 创建认证信息
        MongoCredential credential = MongoCredential.createCredential(username, database, password.toCharArray());


        // 构建客户端设置
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyToClusterSettings(builder -> builder.hosts(serverAddresses)
                        .requiredReplicaSetName(replicaSet))
                .credential(credential)
                .build();

        return MongoClients.create(settings);
    }
}
