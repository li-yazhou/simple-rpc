package org.alpha.coder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.alpha.api.Serialization;

import java.util.List;

/**
 * @author liyazhou1
 * @date 2019/12/6
 */
public class RpcDecoder extends ByteToMessageDecoder {

    private Class<?> clazz;

    private Serialization serialization;


    public RpcDecoder(Class<?> clazz, Serialization serialization) {
        this.clazz = clazz;
        this.serialization = serialization;
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {

        if (in.readableBytes() < 4) {
            return;
        }

        in.markReaderIndex();
        int dataLen = in.readInt();
        if (in.readableBytes() < dataLen) {
            in.resetReaderIndex();
            return;
        }

        byte[] data = new byte[dataLen];
        in.readBytes(data);

        Object obj = serialization.deserialize(data, clazz);
        out.add(obj);
    }
}
