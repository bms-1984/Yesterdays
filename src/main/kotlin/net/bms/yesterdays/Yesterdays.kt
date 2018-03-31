package net.bms.yesterdays

import net.bms.yesterdays.command.KarmaCommand
import net.bms.yesterdays.item.tab.YesterdaysCreativeTab
import net.bms.yesterdays.proxy.CommonProxy
import net.minecraft.creativetab.CreativeTabs
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.SidedProxy
import net.minecraftforge.fml.common.event.FMLInitializationEvent
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent
import net.minecraftforge.fml.common.event.FMLServerStartingEvent

@Mod(modid = Yesterdays.MODID, name = Yesterdays.MOD_NAME, version = Yesterdays.MOD_VERSION,
        acceptedMinecraftVersions = Yesterdays.ACCEPTED_MC_VERSIONS, dependencies = Yesterdays.DEPENDENCIES,
        modLanguage = "kotlin", modLanguageAdapter = "net.shadowfacts.forgelin.KotlinAdapter",
        certificateFingerprint = "@FINGERPRINT@")
object Yesterdays {
    const val MODID: String = "yesterdays"
    const val MOD_NAME: String = "Yesterdays"
    const val MOD_VERSION: String = "@VERSION@"
    const val ACCEPTED_MC_VERSIONS: String = "[1.12,)"
    const val DEPENDENCIES: String = "required-after:forgelin"

    lateinit var tabBaseSciencesCreativeTab: CreativeTabs

    @Mod.Instance
    lateinit var instance: Yesterdays

    @SidedProxy(clientSide = "net.bms.yesterdays.proxy.ClientProxy", serverSide = "net.bms.yesterdays.proxy.CommonProxy")
    lateinit var proxy: CommonProxy

    @Mod.EventHandler
    fun preInit(event: FMLPreInitializationEvent) {
        tabBaseSciencesCreativeTab = YesterdaysCreativeTab()
        proxy.preInit(event)
    }

    @Mod.EventHandler
    fun init(event: FMLInitializationEvent) {
        proxy.init(event)
    }

    @Mod.EventHandler
    fun postInit(event: FMLPostInitializationEvent) {
        proxy.postInit(event)
    }

    @Mod.EventHandler
    fun registerCommands(event: FMLServerStartingEvent) {
        event.registerServerCommand(KarmaCommand())
    }
}