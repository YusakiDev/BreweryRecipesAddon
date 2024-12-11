package dev.jsinco.recipes.permissions

import org.bukkit.Bukkit

enum class PermissionAPI(private val pluginName: String) {
    LUCKPERMS("LuckPerms");

    fun checkIfPermissionPluginExists(): Boolean {
        return Bukkit.getPluginManager().getPlugin(pluginName) != null
    }
}
