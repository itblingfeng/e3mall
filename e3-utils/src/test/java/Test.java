import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;

import java.util.HashSet;
import java.util.Set;

public class Test {
    public static void main(String[] args) {
        Set set = new HashSet();
        set.add(new HostAndPort("192.168.25.114", 7001));
        set.add(new HostAndPort("192.168.25.114", 7002));
        set.add(new HostAndPort("192.168.25.114", 7003));
        set.add(new HostAndPort("192.168.25.114", 7004));
        set.add(new HostAndPort("192.168.25.114", 7005));
        set.add(new HostAndPort("192.168.25.114", 7006));

        JedisCluster jedisCluster = new JedisCluster(set);
        jedisCluster.set("aaa","aaa");
        System.out.println(jedisCluster.get("aaa"));

    }
}
