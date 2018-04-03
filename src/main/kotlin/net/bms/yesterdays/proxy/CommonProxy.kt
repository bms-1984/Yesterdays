package net.bms.yesterdays.proxy

import net.bms.yesterdays.Yesterdays
import net.bms.yesterdays.cap.DefaultSoul
import net.bms.yesterdays.cap.ISoul
import net.bms.yesterdays.cap.SoulStorage
import net.bms.yesterdays.packet.InstantHealMessage
import net.bms.yesterdays.packet.InstantHealMessageHandler
import net.bms.yesterdays.util.EventHandler
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.common.capabilities.CapabilityManager
import net.minecraftforge.fml.common.event.FMLInitializationEvent
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent
import net.minecraftforge.fml.relauncher.Side

open class CommonProxy {
    open fun preInit(event: FMLPreInitializationEvent) {
        CapabilityManager.INSTANCE.register(ISoul::class.java, SoulStorage(), { DefaultSoul() })
        Yesterdays.SIMPLEIMPL.registerMessage(InstantHealMessageHandler::class.java, InstantHealMessage::class.java,
                0, Side.SERVER)
        MinecraftForge.EVENT_BUS.register(EventHandler())
    }

    open fun init(event: FMLInitializationEvent) {

    }

    open fun postInit(event: FMLPostInitializationEvent) {

    }
}