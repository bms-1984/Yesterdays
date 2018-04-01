package net.bms.yesterdays.command

import net.bms.yesterdays.cap.SoulProvider
import net.minecraft.command.ICommand
import net.minecraft.command.ICommandSender
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.server.MinecraftServer
import net.minecraft.util.math.BlockPos
import net.minecraft.util.text.TextComponentString

class SetLevelCommand: ICommand {
    override fun getUsage(sender: ICommandSender?): String {
        return "setlevel <level>"
    }

    override fun getName(): String {
        return "setlevel"
    }

    override fun getTabCompletions(server: MinecraftServer?, sender: ICommandSender?, args: Array<out String>?, targetPos: BlockPos?): MutableList<String> {
        return mutableListOf("/setlevel")
    }

    override fun compareTo(other: ICommand?): Int {
        return 0
    }

    override fun checkPermission(server: MinecraftServer?, sender: ICommandSender?): Boolean {
        return sender?.canUseCommand(3, "setlevel")!!
    }

    override fun isUsernameIndex(args: Array<out String>?, index: Int): Boolean {
        return false
    }

    override fun getAliases(): MutableList<String> {
        return mutableListOf("setl")
    }

    override fun execute(server: MinecraftServer?, sender: ICommandSender?, args: Array<out String>?) {
        if (args!!.size != 1)
            sender?.sendMessage(TextComponentString("/setlevel must have one argument."))
        else if (args[0].toInt() > 4 || args[0].toInt() < -4) {
            sender?.sendMessage(TextComponentString("Level must be an integer between -4 and 5."))
        }
        else {
            val player = sender?.commandSenderEntity as? EntityPlayer
            if (player == null) {
                sender?.sendMessage(TextComponentString("That player does not exist on this server."))
            }
            else if(player.hasCapability(SoulProvider.SOUL_CAPABILITY, null)) {
                if (args[0].toInt() == 5) {
                    player.getCapability(SoulProvider.SOUL_CAPABILITY, null)?.hasReachedMoksha = true
                }
                else {
                    player.getCapability(SoulProvider.SOUL_CAPABILITY, null)?.soulType = args[0].toInt()
                }
                sender.sendMessage(TextComponentString("Soul level set."))
            }
        }
    }
}