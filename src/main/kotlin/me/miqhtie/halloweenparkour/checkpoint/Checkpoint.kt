package me.miqhtie.halloweenparkour.checkpoint

import org.bukkit.Location

data class Checkpoint(val name: String, val credits: String, val startLocation: Location, val contactDamage: Boolean = false, val yFloor: Int = 20, val applyDarkness: Boolean = false, val isFinish: Boolean = false, val isStart: Boolean = false)
