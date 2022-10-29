package me.miqhtie.halloweenparkour.util

import me.miqhtie.halloweenparkour.HalloweenParkour
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.configuration.file.FileConfiguration
import java.io.File
import java.lang.Exception

class ConfigManager(private val path: String, private var name: String)  {
  private val plugin = HalloweenParkour.instance

  var config: FileConfiguration? = null
  private var file: File? = null

  init {
    this.name = "$name.yml"
    create()
  }

  fun save() {
    try {
      config!!.save(file!!)
    } catch (exc: Exception) {
      exc.printStackTrace()
    }
  }

  private fun reloadFile(): File? {
    file = File(path, name)
    return file
  }

  private fun reloadConfig(): FileConfiguration? {
    config = YamlConfiguration.loadConfiguration(file!!)
    return config
  }

  fun reload() {
    reloadFile()
    reloadConfig()
  }

  private fun create() {
    if (file == null) {
      reloadFile()
    }

    file!!.parentFile.mkdirs()

    plugin.saveResource(name, false)

    if (config == null) {
      reloadConfig()
    }
  }
}