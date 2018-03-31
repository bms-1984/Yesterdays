package net.bms.yesterdays.cap

import net.minecraft.nbt.NBTBase
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.EnumFacing
import net.minecraftforge.common.capabilities.Capability

class SoulStorage: Capability.IStorage<ISoul> {
    override fun writeNBT(capability: Capability<ISoul>?, instance: ISoul?, side: EnumFacing?): NBTBase? {
        val nbt = NBTTagCompound()
        nbt.setBoolean("hasReachedMoksha", instance!!.hasReachedMoksha)
        nbt.setInteger("livesLived", instance.livesLived)
        nbt.setInteger("killCount", instance.killCount)
        nbt.setInteger("karmaScore", instance.karmaScore)
        nbt.setInteger("soulType", instance.soulType)
        return nbt
    }

    override fun readNBT(capability: Capability<ISoul>?, instance: ISoul?, side: EnumFacing?, nbt: NBTBase?) {
        val compound = nbt as NBTTagCompound
        instance?.hasReachedMoksha = compound.getBoolean("hasReachedMoksha")
        instance?.livesLived = compound.getInteger("livedLived")
        instance?.killCount = compound.getInteger("killCount")
        instance?.karmaScore = compound.getInteger("karmaScore")
        instance?.soulType = compound.getInteger("soulType")
    }

}