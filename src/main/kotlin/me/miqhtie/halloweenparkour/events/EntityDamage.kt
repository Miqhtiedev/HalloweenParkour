package me.miqhtie.halloweenparkour.events

import me.miqhtie.halloweenparkour.HalloweenParkour
import net.minecraft.world.level.block.BlockCactus
import org.bukkit.attribute.Attribute
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.entity.EntityDamageEvent

class EntityDamage: Listener {
  @EventHandler
  fun entityDamageEntity(event: EntityDamageEvent) {
    if (event.entity !is Player) return

    event.isCancelled = true

    println(HalloweenParkour.instance.gameStarted)
    if (HalloweenParkour.instance.gameStarted) {
      // Allow cacti to perform damage ticks which is part of the parkour
      val allowContactDamage = HalloweenParkour.instance.checkpointManager.getPlayerCheckpoint(event.entity as Player).contactDamage
      if (event.cause == EntityDamageEvent.DamageCause.CONTACT && allowContactDamage) {
        event.isCancelled = false
        event.damage = 0.0
        return
      }
    }
  }
}