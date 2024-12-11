package dev.jsinco.recipes.permissions

import com.dre.brewery.BreweryPlugin
import dev.jsinco.recipes.Recipes
import dev.jsinco.recipes.configuration.RecipesConfig
import org.bukkit.Bukkit
import org.bukkit.entity.Player

class CommandPermission : PermissionManager {

    private val config: RecipesConfig = Recipes.configManager.getConfig(RecipesConfig::class.java)

    override fun setPermission(permission: String, player: Player, value: Boolean) {
        BreweryPlugin.getScheduler().runTask {
            Bukkit.dispatchCommand(
                Bukkit.getConsoleSender(), config.permissionCommand
                    .replace("%player%", player.name).replace("%permission%", permission)
                    .replace("%boolean%", value.toString())
            )
        }
    }

    override fun removePermission(permission: String, player: Player) {
        BreweryPlugin.getScheduler().runTask {
            Bukkit.dispatchCommand(
                Bukkit.getConsoleSender(), config.permissionUnsetCommand
                    .replace("%player%", player.name).replace("%permission%", permission)
                    .replace("%boolean%", "false")
            )
        }
    }
}
