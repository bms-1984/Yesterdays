package net.bms.yesterdays.packet

import net.bms.yesterdays.cap.SoulProvider
import net.minecraft.util.text.TextComponentString
import net.minecraftforge.fml.common.network.simpleimpl.IMessage
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext

class InstantHealMessageHandler: IMessageHandler<InstantHealMessage, IMessage?> {
    override fun onMessage(message: InstantHealMessage?, ctx: MessageContext?): IMessage? {
        val player = ctx?.serverHandler?.player
        player?.serverWorld?.addScheduledTask({
            if (player.hasCapability(SoulProvider.SOUL_CAPABILITY, null) &&
                    player.getCapability(SoulProvider.SOUL_CAPABILITY, null)!!.canHeal) {
                if (player.getCapability(SoulProvider.SOUL_CAPABILITY, null)!!.healCooldown >= 1200)
                    player.heal(player.maxHealth - player.health)
                else
                    player.sendMessage(TextComponentString("Your ability must cool down before its next use."))
            }
        })
        return null
    }
}