package net.bms.yesterdays.command

import net.bms.yesterdays.cap.SoulProvider
import net.minecraft.command.ICommand
import net.minecraft.command.ICommandSender
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.server.MinecraftServer
import net.minecraft.util.math.BlockPos
import net.minecraft.util.text.TextComponentString

class AddKarmaCommand: ICommand {
    override fun getUsage(sender: ICommandSender?): String {
        return "addkarma <amount>"
    }

    override fun getName(): String {
        return "addkarma"
    }

    override fun getTabCompletions(server: MinecraftServer?, sender: ICommandSender?, args: Array<out String>?, targetPos: BlockPos?): MutableList<String> {
        return mutableListOf("/addkarma")
    }

    override fun compareTo(other: ICommand?): Int {
        return 0
    }

    override fun checkPermission(server: MinecraftServer?, sender: ICommandSender?): Boolean {
        return sender?.canUseCommand(3, "addkarma")!!
    }

    override fun isUsernameIndex(args: Array<out String>?, index: Int): Boolean {
        return false
    }

    override fun getAliases(): MutableList<String> {
        return mutableListOf("addk")
    }

    override fun execute(server: MinecraftServer?, sender: ICommandSender?, args: Array<out String>?) {
        if (args!!.size != 1)
            sender?.sendMessage(TextComponentString("/addkarma must have one argument."))
        else {
            val player = sender?.commandSenderEntity as? EntityPlayer
            if (player == null) {
                sender?.sendMessage(TextComponentString("That player does not exist on this server."))
            }
            else if(player.hasCapability(SoulProvider.SOUL_CAPABILITY, null)) {
                player.getCapability(SoulProvider.SOUL_CAPABILITY, null)!!.karmaScore += args[0].toInt()
                sender.sendMessage(TextComponentString("Karma value modified."))
            }
        }
    }
}