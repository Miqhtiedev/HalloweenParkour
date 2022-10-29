package me.miqhtie.halloweenparkour.events

import me.miqhtie.halloweenparkour.HalloweenParkour
import me.miqhtie.halloweenparkour.util.BlocksList
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerMoveEvent
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import org.bukkit.util.Vector
import kotlin.math.floor

class PlayerMove: Listener {
  private val plugin = HalloweenParkour.instance

  @EventHandler
  fun moveEvent(event: PlayerMoveEvent) {
    if (!plugin.gameStarted) {
      if (event.player.location.y < 20) event.player.teleport(event.player.world.spawnLocation)
      return
    }

    val currentCheckpoint = plugin.checkpointManager.getPlayerCheckpoint(event.player)
    if (event.player.location.y < currentCheckpoint.yFloor) {
      event.player.teleport(currentCheckpoint.startLocation)
      return
    }

    if (event.player.location.block.type == Material.LAVA) {
      event.player.teleport(currentCheckpoint.startLocation)
      event.player.fireTicks = 0
      return
    }

   val blockStandingOn = event.player.world.getBlockData(event.player.location.subtract(Vector(0, 1, 0))).material

    if (BlocksList.decorationBlocks.contains(blockStandingOn)) {
      event.player.teleport(currentCheckpoint.startLocation)
      return
    }

    if (BlocksList.checkpointBlocks.contains(blockStandingOn)) {
      val checkpoint = plugin.checkpointManager.nextCheckpoint(currentCheckpoint, floor(event.player.location.x).toInt())
      if (checkpoint != null) {
        plugin.checkpointManager.playerCheckpoints[event.player] = checkpoint

        if (!checkpoint.applyDarkness) event.player.removePotionEffect(PotionEffectType.DARKNESS)
        else event.player.addPotionEffect(PotionEffect(PotionEffectType.DARKNESS, 1000000, 1, true, false))

        if (!checkpoint.isFinish) {
          plugin.sendMessage(event.player, "New Checkpoint Reached\n${checkpoint.name} by: ${checkpoint.credits}")
          event.player.sendTitle("${ChatColor.GOLD}${checkpoint.name}", "${ChatColor.DARK_PURPLE}${checkpoint.credits}", 0, 40, 0)
          event.player.playSound(event.player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, 1f)
        } else {
          plugin.announce("${event.player.name} has completed the parkour!")
          plugin.sendMessage(event.player, "You can now fly")
          event.player.allowFlight = true
          event.player.playSound(event.player, Sound.ENTITY_PLAYER_LEVELUP, 2f, 1f)
        }
      }
    }
  }
}