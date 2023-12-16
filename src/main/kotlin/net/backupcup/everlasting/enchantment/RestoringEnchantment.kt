package net.backupcup.everlasting.enchantment

import net.backupcup.everlasting.Everlasting.restoringSlots
import net.minecraft.enchantment.Enchantment
import net.minecraft.enchantment.EnchantmentHelper
import net.minecraft.enchantment.EnchantmentTarget
import net.minecraft.enchantment.Enchantments
import net.minecraft.entity.EquipmentSlot
import net.minecraft.item.ItemStack
import net.minecraft.server.network.ServerPlayerEntity

class RestoringEnchantment(weight: Rarity?, target: EnchantmentTarget?, slotTypes: Array<out EquipmentSlot>?) : Enchantment(weight, target,
    slotTypes) {

    override fun canAccept(other: Enchantment?): Boolean {
        if(other == Enchantments.MENDING) return false
        return super.canAccept(other)
    }

    override fun isTreasure(): Boolean {
        return true
    }

    override fun isAvailableForEnchantedBookOffer(): Boolean {
        return false
    }

    companion object {
        @JvmField
        val INSTANCE: RestoringEnchantment = RestoringEnchantment(Rarity.COMMON, EnchantmentTarget.BREAKABLE, restoringSlots)

        fun shouldPreventDamage(itemStack: ItemStack, amount: Int, player: ServerPlayerEntity): Boolean {
            return EnchantmentHelper.getLevel(INSTANCE, itemStack) > 0 && player.totalExperience >= amount
        }
    }

    override fun getMaxLevel(): Int {
        return 1
    }

    override fun getMinPower(level: Int): Int {
        return level * 30
    }

    override fun getMaxPower(level: Int): Int {
        return getMinPower(level) + 60
    }
}