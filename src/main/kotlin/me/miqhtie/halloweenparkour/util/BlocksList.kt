package me.miqhtie.halloweenparkour.util

import org.bukkit.Material

class BlocksList {
  companion object {
    val checkpointBlocks: Array<Material> = arrayOf(
      Material.YELLOW_STAINED_GLASS,
      Material.RED_STAINED_GLASS,
      Material.BLACK_STAINED_GLASS,
      Material.TINTED_GLASS,
      Material.LIGHT_GRAY_STAINED_GLASS,
      Material.SHROOMLIGHT,
      Material.VERDANT_FROGLIGHT,
      Material.OCHRE_FROGLIGHT,
      Material.PEARLESCENT_FROGLIGHT,
      Material.LIGHT_WEIGHTED_PRESSURE_PLATE,
      Material.GLOWSTONE,
      Material.RED_CONCRETE
    )

    val decorationBlocks: Array<Material> = arrayOf(
      Material.MUD,
      Material.BASALT,
      Material.SMOOTH_BASALT,
      Material.BLACKSTONE
    )
  }
}