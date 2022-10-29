package me.miqhtie.halloweenparkour.commmands

import me.miqhtie.halloweenparkour.HalloweenParkour
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.GameMode
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender

class StartGame: CommandExecutor {
  val plugin = HalloweenParkour.instance

  override fun onCommand(sender: CommandSender, p1: Command, p2: String, args: Array<out String>): Boolean {
    if (!sender.isOp) return true

    if (plugin.gameStarted) {
      sender.sendMessage("${ChatColor.RED}Game already started")
      return false
    }

    val checkpointManager = plugin.checkpointManager

    if (checkpointManager.getStart() == null || checkpointManager.getFinish() == null) {
      sender.sendMessage("${ChatColor.RED}Start or finish missing")
      return false
    }

    HalloweenParkour.instance.gameStarted = true

    Bukkit.getOnlinePlayers().forEach {
      if (it.gameMode == GameMode.SPECTATOR) return@forEach
      it.allowFlight = false
      it.gameMode = GameMode.ADVENTURE
      checkpointManager.start()
    }

    plugin.announce("LET THE GAMES BEGIN")
    return false
  }
}