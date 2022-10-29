package me.miqhtie.halloweenparkour.checkpoint

import com.mojang.datafixers.types.templates.Check
import me.miqhtie.halloweenparkour.HalloweenParkour
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import kotlin.math.floor

class CheckpointManager {
  private var checkpoints: MutableList<Checkpoint> = mutableListOf()
  private val data = HalloweenParkour.instance.config

  var playerCheckpoints: HashMap<Player, Checkpoint> = HashMap()

  init {
    registerCheckpoints()
    Bukkit.getLogger().info(if (checkpoints.isEmpty()) "No checkpoints registered" else "Registered checkpoints")
  }


  fun createCheckpoint(checkpoint: Checkpoint) {
    val prefix = "checkpoints.${checkpoint.name}"
    data.config?.set("${prefix}.credits", checkpoint.credits)
    data.config?.set("${prefix}.startLocation", checkpoint.startLocation)
    data.config?.set("${prefix}.contactDamage", checkpoint.contactDamage)
    data.config?.set("${prefix}.applyDarkness", checkpoint.applyDarkness)
    data.config?.set("${prefix}.isFinish", checkpoint.isFinish)
    data.config?.set("${prefix}.isStart", checkpoint.isStart)
    data.config?.set("${prefix}.yFloor", checkpoint.yFloor)
    data.save()

    checkpoints.add(checkpoint)
  }

  fun getPlayerCheckpoint(player: Player): Checkpoint = playerCheckpoints[player] ?: getStart()!!
  fun getCheckpoint(name: String): Checkpoint? = checkpoints.find { it.name.equals(name, true) }
  fun getFinish(): Checkpoint? = checkpoints.find { it.isFinish }
  fun getStart(): Checkpoint? = checkpoints.find { it.isStart }

  fun start()  {
    val start = getStart() ?: return

    Bukkit.getOnlinePlayers().forEach {
      it.teleport(start.startLocation)
    }
  }

  fun nextCheckpoint(currentCheckpoint: Checkpoint, positionX: Int): Checkpoint? {
    var newCheckpoint: Checkpoint? = null
    checkpoints.forEach {
      if (it.startLocation.x <= currentCheckpoint.startLocation.x) return@forEach
      if (floor(it.startLocation.x) > positionX) return@forEach

      if (newCheckpoint != null && it.startLocation.x <= newCheckpoint!!.startLocation.x) return@forEach
      newCheckpoint = it
    }
    return newCheckpoint
  }

  private fun registerCheckpoints() {
    val section = data.config?.getConfigurationSection("checkpoints")
    section?.getKeys(false)?.forEach {
      val startLocation = data.config?.getLocation("checkpoints.${it}.startLocation")
      val credits = data.config?.getString("checkpoints.${it}.credits")
      val contactDamage = data.config?.getBoolean("checkpoints.${it}.contactDamage")
      val darkness = data.config?.getBoolean("checkpoints.${it}.applyDarkness")
      val isFinish = data.config?.getBoolean("checkpoints.${it}.isFinish")
      val isStart = data.config?.getBoolean("checkpoints.${it}.isStart")
      val yFloor = data.config?.getInt("checkpoints.${it}.yFloor")

      if (startLocation == null || credits == null) return@forEach

      checkpoints.add(Checkpoint(it, credits, startLocation, contactDamage ?: false, yFloor ?: 20, darkness ?: false, isFinish ?: false, isStart ?: false))
    }
  }
}