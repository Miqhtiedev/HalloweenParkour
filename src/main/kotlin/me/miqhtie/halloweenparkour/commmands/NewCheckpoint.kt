package me.miqhtie.halloweenparkour.commmands

import me.miqhtie.halloweenparkour.HalloweenParkour
import me.miqhtie.halloweenparkour.checkpoint.Checkpoint
import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.util.Vector
import java.util.*

class NewCheckpoint: CommandExecutor {
  private val plugin = HalloweenParkour.instance

  override fun onCommand(sender: CommandSender, p1: Command, p2: String, args: Array<out String>): Boolean {
    if (sender !is Player) return true
    if (!sender.isOp) return true

    if (args.size < 2) {
      plugin.sendMessage(sender, "${ChatColor.RED}You must provide a name and credits to create a checkpoint. Ex. /newcheckpoint (name) (credits)")
      return true;
    }

    val name = args[0]
    if (plugin.checkpointManager.getCheckpoint(name) != null) {
      plugin.sendMessage(sender, "${ChatColor.RED}Checkpoint already exists with that name")
      return true;
    }

    val credits = args.drop(1).joinToString(" ")
    val location = sender.location
    location.yaw = 0f
    location.pitch = 0f
    location.direction = Vector(1, 0, 0)

    val checkpoint = Checkpoint(name, credits, location)
    plugin.checkpointManager.createCheckpoint(checkpoint)

    plugin.sendMessage(sender, "${ChatColor.GREEN}Created")
    return false
  }
}