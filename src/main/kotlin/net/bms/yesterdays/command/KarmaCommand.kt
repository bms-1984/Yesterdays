package net.bms.yesterdays.command

import net.bms.yesterdays.cap.SoulProvider
import net.minecraft.command.ICommand
import net.minecraft.command.ICommandSender
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.server.MinecraftServer
import net.minecraft.util.math.BlockPos
import net.minecraft.util.text.TextComponentString

class KarmaCommand: ICommand {
    override fun compareTo(other: ICommand?): Int {
        return 0
    }

    override fun getUsage(sender: ICommandSender?): String {
        return "karma"
    }

    override fun getName(): String {
        return "karma"
    }

    override fun getTabCompletions(server: MinecraftServer?, sender: ICommandSender?, args: Array<out String>?, targetPos: BlockPos?): MutableList<String> {
        return mutableListOf("/karma")
    }

    override fun checkPermission(server: MinecraftServer?, sender: ICommandSender?): Boolean {
        return true
    }

    override fun isUsernameIndex(args: Array<out String>?, index: Int): Boolean {
       return false
    }

    override fun getAliases(): MutableList<String> {
        return mutableListOf("kar")
    }

    override fun execute(server: MinecraftServer?, sender: ICommandSender?, args: Array<out String>?) {
        val entity: EntityPlayer = sender?.commandSenderEntity as EntityPlayer
        if (entity.hasCapability(SoulProvider.SOUL_CAPABILITY, null)) {
            val soul = entity.getCapability(SoulProvider.SOUL_CAPABILITY, null)
            if (soul != null) {
                sender.sendMessage(TextComponentString("${sender.name}'s Karma score is ${soul.karmaScore}."))
                sender.sendMessage(TextComponentString("${sender.name}'s is currently level ${soul.soulType}."))
            }
        }
    }
}