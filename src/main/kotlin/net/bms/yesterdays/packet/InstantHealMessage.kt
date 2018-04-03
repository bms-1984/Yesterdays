package net.bms.yesterdays.packet

import io.netty.buffer.ByteBuf
import net.minecraftforge.fml.common.network.simpleimpl.IMessage

class InstantHealMessage @JvmOverloads constructor(private var healAttempted: Boolean = false): IMessage {
    override fun fromBytes(buf: ByteBuf?) {
        healAttempted = buf?.readBoolean()!!
    }

    override fun toBytes(buf: ByteBuf?) {
        buf?.writeBoolean(healAttempted)
    }
}