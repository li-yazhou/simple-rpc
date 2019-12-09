package org.alpha.coder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.alpha.api.Serialization;

/**
 * @author liyazhou1
 * @date 2019/12/6
 */
public class RpcEncoder extends MessageToByteEncoder {

    private Class<?> clazz;

    private Serialization serialization;


    public RpcEncoder(Class<?> clazz, Serialization serialization) {
        this.clazz = clazz;
        this.serialization = serialization;
    }


    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) {

        if (clazz != null) {
            byte[] bytes = serialization.serialize(msg);
            out.writeInt(bytes.length);
            out.writeBytes(bytes);
        }
    }
}
