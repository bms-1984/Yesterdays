package net.bms.yesterdays.util

import net.bms.yesterdays.Yesterdays
import net.bms.yesterdays.cap.SoulProvider
import net.bms.yesterdays.init.Blocks
import net.bms.yesterdays.init.Items
import net.minecraft.block.Block
import net.minecraft.entity.Entity
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.Item
import net.minecraft.util.ResourceLocation
import net.minecraftforge.client.event.ModelRegistryEvent
import net.minecraftforge.event.AttachCapabilitiesEvent
import net.minecraftforge.event.RegistryEvent
import net.minecraftforge.event.entity.player.PlayerEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent

class EventHandler {
    @SubscribeEvent
    fun registerBlocks(event: RegistryEvent.Register<Block>) {
        Blocks.init(event.registry)
    }

    @SubscribeEvent
    fun registerItems(event: RegistryEvent.Register<Item>) {
        Items.init(event.registry)
    }

    @SubscribeEvent
    fun registerModels(event: ModelRegistryEvent) {
        Blocks.initModels()
        Items.initModels()
    }

    @SubscribeEvent
    fun attachSoulCapability(event: AttachCapabilitiesEvent<Entity>) {
        if (event.`object` is EntityPlayer) {
            event.addCapability(ResourceLocation(Yesterdays.MODID, "soul_capability"), SoulProvider())
        }
    }

    @SubscribeEvent
    fun reincarnatePlayer(event: PlayerEvent.Clone) {
        if (event.isWasDeath && event.original.hasCapability(SoulProvider.SOUL_CAPABILITY, null) &&
                event.entityPlayer.hasCapability(SoulProvider.SOUL_CAPABILITY, null)) {
            val oldSoul = event.original.getCapability(SoulProvider.SOUL_CAPABILITY, null)
            if (oldSoul != null) {
                event.entityPlayer.getCapability(SoulProvider.SOUL_CAPABILITY, null)?.soulType = oldSoul.soulType
                if (oldSoul.karmaScore >= 4)
                    event.entityPlayer.getCapability(SoulProvider.SOUL_CAPABILITY, null)!!.soulType += 1
                event.entityPlayer.getCapability(SoulProvider.SOUL_CAPABILITY, null)?.karmaScore = oldSoul.karmaScore
                event.entityPlayer.getCapability(SoulProvider.SOUL_CAPABILITY, null)?.killCount = 0
                event.entityPlayer.getCapability(SoulProvider.SOUL_CAPABILITY, null)!!.livesLived += 1
                event.entityPlayer.getCapability(SoulProvider.SOUL_CAPABILITY, null)?.hasReachedMoksha = oldSoul.karmaScore >= 64
            }
        }
    }
}