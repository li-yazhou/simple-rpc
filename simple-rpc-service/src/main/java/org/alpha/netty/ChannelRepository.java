package org.alpha.netty;

import java.nio.channels.Channel;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author liyazhou1
 * @date 2019/12/8
 */
public class ChannelRepository {

    private Map<String, Channel> channelCacheMap = new ConcurrentHashMap<>();

    public ChannelRepository put(String key, Channel channel) {
        this.channelCacheMap.put(key, channel);
        return this;
    }

    public Channel get(String key) {
        return this.channelCacheMap.get(key);
    }

    public Channel remove(String key) {
        return this.channelCacheMap.remove(key);
    }

    public int size() {
        return this.channelCacheMap.size();
    }
}
