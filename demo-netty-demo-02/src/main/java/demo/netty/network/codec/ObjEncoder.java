/*
 * Copyright (C) GSX Techedu Inc. All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package demo.netty.network.codec;

import demo.netty.network.util.SerializationUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author yinchao
 * @description
 * @team wuhan operational dev.
 * @date 2021/12/21 14:05
 **/
public class ObjEncoder extends MessageToByteEncoder {

    private Class<?> genericClass;

    public ObjEncoder(Class<?> genericClass) {
        this.genericClass = genericClass;
    }

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Object obj, ByteBuf buf)
        throws Exception {
        if (genericClass.isInstance(obj)) {
            byte[] data = SerializationUtil.serialize(obj);
            buf.writeInt(data.length);
            buf.writeBytes(data);
        }
    }
}
