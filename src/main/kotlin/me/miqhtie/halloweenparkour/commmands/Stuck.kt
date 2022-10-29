package me.miqhtie.halloweenparkour.commmands

import me.miqhtie.halloweenparkour.HalloweenParkour
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class Stuck: CommandExecutor {
  override fun onCommand(sender: CommandSender, p1: Command, p2: String, args: Array<out String>): Boolean {
    if (sender !is Player) return true

    if (!HalloweenParkour.instance.gameStarted) {
      sender.teleport(sender.world.spawnLocation)
      return true
    }

    val checkpoint = HalloweenParkour.instance.checkpointManager.getPlayerCheckpoint(sender)
    sender.teleport(checkpoint.startLocation)
    return false
  }
}