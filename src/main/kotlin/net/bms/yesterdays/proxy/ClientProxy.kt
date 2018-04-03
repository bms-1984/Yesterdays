package net.bms.yesterdays.proxy

import net.minecraft.client.settings.KeyBinding
import net.minecraftforge.fml.client.registry.ClientRegistry
import net.minecraftforge.fml.common.event.FMLInitializationEvent
import org.lwjgl.input.Keyboard

class ClientProxy: CommonProxy() {
    companion object {
        val KeyBindingInstantHeal = KeyBinding("key.heal.description", Keyboard.KEY_H, "key.yesterdays.category")
    }

    override fun init(event: FMLInitializationEvent) {
        if (event.side.isClient)
            ClientRegistry.registerKeyBinding(KeyBindingInstantHeal)
        super.init(event)
    }
}