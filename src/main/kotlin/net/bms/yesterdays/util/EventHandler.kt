package net.bms.yesterdays.util

import net.bms.yesterdays.Yesterdays
import net.bms.yesterdays.cap.SoulProvider
import net.bms.yesterdays.init.Blocks
import net.bms.yesterdays.init.Items
import net.minecraft.block.Block
import net.minecraft.entity.Entity
import net.minecraft.entity.EntityLiving
import net.minecraft.item.Item
import net.minecraft.util.ResourceLocation
import net.minecraftforge.client.event.ModelRegistryEvent
import net.minecraftforge.event.AttachCapabilitiesEvent
import net.minecraftforge.event.RegistryEvent
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
        if (event.`object` is EntityLiving) {
            event.addCapability(ResourceLocation(Yesterdays.MODID, "soul_capability"), SoulProvider())
        }
    }
}