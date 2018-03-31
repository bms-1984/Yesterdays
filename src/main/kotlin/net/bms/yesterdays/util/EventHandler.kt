package net.bms.yesterdays.util

import net.bms.yesterdays.Yesterdays
import net.bms.yesterdays.cap.SoulProvider
import net.bms.yesterdays.init.Blocks
import net.bms.yesterdays.init.Items
import net.minecraft.block.Block
import net.minecraft.entity.Entity
import net.minecraft.entity.monster.EntityMob
import net.minecraft.entity.passive.EntityAnimal
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.Item
import net.minecraft.potion.Potion
import net.minecraft.potion.PotionEffect
import net.minecraft.util.ResourceLocation
import net.minecraftforge.client.event.ModelRegistryEvent
import net.minecraftforge.event.AttachCapabilitiesEvent
import net.minecraftforge.event.RegistryEvent
import net.minecraftforge.event.entity.living.AnimalTameEvent
import net.minecraftforge.event.entity.living.BabyEntitySpawnEvent
import net.minecraftforge.event.entity.living.LivingDeathEvent
import net.minecraftforge.event.entity.player.BonemealEvent
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
                if (oldSoul.karmaScore < 16)
                    event.entityPlayer.getCapability(SoulProvider.SOUL_CAPABILITY, null)!!.soulType = 0
                if (oldSoul.karmaScore >= 16)
                    event.entityPlayer.getCapability(SoulProvider.SOUL_CAPABILITY, null)!!.soulType = 1
                if (oldSoul.karmaScore >= 32)
                        event.entityPlayer.getCapability(SoulProvider.SOUL_CAPABILITY, null)!!.soulType = 2
                if (oldSoul.karmaScore >= 64)
                    event.entityPlayer.getCapability(SoulProvider.SOUL_CAPABILITY, null)!!.soulType = 3
                if (oldSoul.karmaScore >= 128)
                    event.entityPlayer.getCapability(SoulProvider.SOUL_CAPABILITY, null)!!.soulType = 4
                event.entityPlayer.getCapability(SoulProvider.SOUL_CAPABILITY, null)?.karmaScore = oldSoul.karmaScore
                event.entityPlayer.getCapability(SoulProvider.SOUL_CAPABILITY, null)!!.livesLived += 1
                event.entityPlayer.getCapability(SoulProvider.SOUL_CAPABILITY, null)?.hasReachedMoksha = oldSoul.karmaScore >= 256
            }
            val soul = event.entityPlayer.getCapability(SoulProvider.SOUL_CAPABILITY, null)
            if (soul != null) {
                if (soul.soulType >= 1)
                    event.entityPlayer?.addPotionEffect(PotionEffect(Potion.getPotionFromResourceLocation("jump_boost")!!, Int.MAX_VALUE))
            }
        }
    }

    @SubscribeEvent
    fun killCreature(event: LivingDeathEvent) {
        if (event.entityLiving is EntityAnimal) {
            val killer = event.source.immediateSource
            if (killer != null && killer is EntityPlayer && killer.hasCapability(SoulProvider.SOUL_CAPABILITY, null)) {
                killer.getCapability(SoulProvider.SOUL_CAPABILITY, null)!!.karmaScore -= 2
            }
        }
        else if (event.entityLiving is EntityMob) {
            val killer = event.source.immediateSource
            if (killer != null && killer is EntityPlayer && killer.hasCapability(SoulProvider.SOUL_CAPABILITY, null)) {
                killer.getCapability(SoulProvider.SOUL_CAPABILITY, null)!!.karmaScore += 2
            }
        }
        else if (event.entityLiving is EntityPlayer) {
            val killer = event.source.immediateSource
            if (killer != null && killer is EntityPlayer && killer.hasCapability(SoulProvider.SOUL_CAPABILITY, null)) {
                killer.getCapability(SoulProvider.SOUL_CAPABILITY, null)!!.karmaScore -= 10
            }
        }
    }

    @SubscribeEvent
    fun tameAnimal(event: AnimalTameEvent) {
        if (event.tamer.hasCapability(SoulProvider.SOUL_CAPABILITY, null))
            event.tamer.getCapability(SoulProvider.SOUL_CAPABILITY, null)!!.karmaScore += 3
    }

    @SubscribeEvent
    fun breedChild(event: BabyEntitySpawnEvent) {
        if (event.causedByPlayer!!.hasCapability(SoulProvider.SOUL_CAPABILITY, null))
            event.causedByPlayer!!.getCapability(SoulProvider.SOUL_CAPABILITY, null)!!.karmaScore += 5
    }

    @SubscribeEvent
    fun boneMeal(event: BonemealEvent) {
        if (event.entityPlayer!!.hasCapability(SoulProvider.SOUL_CAPABILITY, null))
            event.entityPlayer!!.getCapability(SoulProvider.SOUL_CAPABILITY, null)!!.karmaScore += 1
    }
}