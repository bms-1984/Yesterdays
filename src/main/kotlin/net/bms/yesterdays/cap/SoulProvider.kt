package net.bms.yesterdays.cap

import net.minecraft.nbt.NBTBase
import net.minecraft.util.EnumFacing
import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.common.capabilities.CapabilityInject
import net.minecraftforge.common.capabilities.ICapabilitySerializable

class SoulProvider: ICapabilitySerializable<NBTBase> {
    companion object {
        @CapabilityInject(ISoul::class)
        internal lateinit var SOUL_CAPABILITY: Capability<ISoul>
    }

    private val cap = SOUL_CAPABILITY.defaultInstance

    override fun <T : Any?> getCapability(capability: Capability<T>, facing: EnumFacing?): T? {
        return if(capability == SOUL_CAPABILITY) SOUL_CAPABILITY.cast(cap) else null
    }

    override fun deserializeNBT(nbt: NBTBase?) {
        SOUL_CAPABILITY.storage.readNBT(SOUL_CAPABILITY, cap, null, nbt)
    }

    override fun serializeNBT(): NBTBase {
        return SOUL_CAPABILITY.storage.writeNBT(SOUL_CAPABILITY, cap, null)!!
    }

    override fun hasCapability(capability: Capability<*>, facing: EnumFacing?): Boolean {
        return capability == SOUL_CAPABILITY
    }
}