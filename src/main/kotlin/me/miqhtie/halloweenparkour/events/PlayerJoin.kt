package me.miqhtie.halloweenparkour.events

import me.miqhtie.halloweenparkour.HalloweenParkour
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

class PlayerJoin: Listener {
  @EventHandler
  fun joinEvent(event: PlayerJoinEvent) {
    if (HalloweenParkour.instance.gameStarted) {
      val checkpoint = HalloweenParkour.instance.checkpointManager.getPlayerCheckpoint(event.player)
      event.player.teleport(checkpoint.startLocation)
      HalloweenParkour.instance.sendMessage(event.player, "Spawned you in at your last checkpoint")
      return
    }

    event.player.teleport(event.player.world.spawnLocation)
    HalloweenParkour.instance.sendMessage(event.player, "Welcome, please enjoy the scenery in the lobby while we wait for the game to begin")
  }
}