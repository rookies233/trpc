package indi.tt.ttrpc.protocol;

/**
 * 协议常量
 *
 * @author tt
 */
public interface ProtocolConstant {

    /**
     * 消息头长度
     */
    int MESSAGE_HEADER_LENGTH = 17;

    /**
     * 协议魔数
     */
    byte PROTOCOL_MAGIC = 0x1;

    /**
     * 协议版本号
     */
    byte MESSAGE_TYPE_REQUEST = 0x1;
}
