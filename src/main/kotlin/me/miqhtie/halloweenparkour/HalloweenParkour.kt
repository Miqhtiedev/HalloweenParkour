package me.miqhtie.halloweenparkour

import me.miqhtie.halloweenparkour.checkpoint.CheckpointManager
import me.miqhtie.halloweenparkour.commmands.NewCheckpoint
import me.miqhtie.halloweenparkour.commmands.StartGame
import me.miqhtie.halloweenparkour.commmands.Stuck
import me.miqhtie.halloweenparkour.events.EntityDamage
import me.miqhtie.halloweenparkour.events.PlayerInteract
import me.miqhtie.halloweenparkour.events.PlayerJoin
import me.miqhtie.halloweenparkour.events.PlayerMove
import me.miqhtie.halloweenparkour.util.ConfigManager
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin

class HalloweenParkour: JavaPlugin() {
  companion object {
    lateinit var instance: HalloweenParkour
  }

  lateinit var config: ConfigManager
  lateinit var checkpointManager: CheckpointManager
  var gameStarted = false

  override fun onEnable() {
    instance = this

    config = ConfigManager(dataFolder.absolutePath, "data")
    checkpointManager = CheckpointManager()

    registerEvents()
    registerCommands()

    logger.info("Halloween Parkour enabled")
  }

  fun announce(message: String) {
    Bukkit.broadcastMessage("§5[Halloween Parkour]§6 $message")
  }

  fun sendMessage(player: Player, message: String) {
    player.sendMessage("§5[Halloween Parkour]§6 $message")
  }

  private fun registerEvents() {
    val pluginManager = server.pluginManager

    pluginManager.registerEvents(PlayerJoin(), this)
    pluginManager.registerEvents(EntityDamage(), this)
    pluginManager.registerEvents(PlayerMove(), this)
    pluginManager.registerEvents(PlayerInteract(), this)

    logger.info("Registered all events")
  }

  private fun registerCommands() {
    getCommand("newcheckpoint")?.setExecutor(NewCheckpoint())
    getCommand("startgame")?.setExecutor(StartGame())
    getCommand("stuck")?.setExecutor(Stuck())
  }

}