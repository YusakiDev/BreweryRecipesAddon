package dev.jsinco.recipes.permissions

import org.bukkit.entity.Player

interface PermissionManager {
    fun setPermission(permission: String, player: Player, value: Boolean)

    fun removePermission(permission: String, player: Player)
}
