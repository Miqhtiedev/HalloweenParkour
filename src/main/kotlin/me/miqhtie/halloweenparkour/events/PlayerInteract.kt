package me.miqhtie.halloweenparkour.events

import me.miqhtie.halloweenparkour.HalloweenParkour
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent

class PlayerInteract: Listener {
  @EventHandler
  fun playerInteract(event: PlayerInteractEvent) {
    if (!HalloweenParkour.instance.gameStarted && event.player.isOp) return

    event.isCancelled = true

    if (event.action == Action.RIGHT_CLICK_BLOCK) {
      if (event.clickedBlock?.type == Material.BLACK_SHULKER_BOX || event.clickedBlock?.type == Material.DARK_OAK_BUTTON || event.clickedBlock?.type == Material.JUKEBOX) {
        event.isCancelled = false
      }
    }
  }
}