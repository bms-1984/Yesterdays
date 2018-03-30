package net.bms.yesterdays.item.tab

import net.minecraft.creativetab.CreativeTabs
import net.minecraft.init.Blocks
import net.minecraft.item.ItemStack

class YesterdaysCreativeTab: CreativeTabs("yesterdays") {
    override fun getTabIconItem(): ItemStack {
        return ItemStack(Blocks.NOTEBLOCK)
    }
}