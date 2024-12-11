package dev.jsinco.recipes.commands.subcommands

import com.dre.brewery.BreweryPlugin
import com.dre.brewery.utility.Logging
import dev.jsinco.recipes.commands.AddonSubCommand
import dev.jsinco.recipes.guis.RecipeGui
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class GuiCommand : AddonSubCommand {

    override fun execute(plugin: BreweryPlugin, sender: CommandSender, args: Array<out String>) {
        if (args.size < 2) {
            Logging.msg(sender, "Usage: /brewery recipes gui <player> (Open the recipe GUI of a player)")
            return
        }

        val player = Bukkit.getPlayerExact(args[1])
        if (player == null) {
            Logging.msg(sender, "&cPlayer not found")
            return
        }

        val recipeGui = RecipeGui(player)
        recipeGui.openRecipeGui(sender as Player)
    }

    override fun tabComplete(plugin: BreweryPlugin, sender: CommandSender, args: Array<out String>): List<String>? {
        return null
    }

    override fun getPermission(): String {
        return "brewery.recipesaddon.cmd.gui"
    }

    override fun playerOnly(): Boolean {
        return true
    }
}
