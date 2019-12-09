package org.alpha.api;

/**
 * @author liyazhou1
 * @date 2019/12/6
 */
public interface Serialization {

    <T> byte[] serialize(T obj);

    <T> T deserialize(byte[] data, Class<T> clazz);
}
