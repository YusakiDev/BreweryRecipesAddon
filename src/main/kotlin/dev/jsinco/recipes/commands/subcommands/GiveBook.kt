package dev.jsinco.recipes.commands.subcommands

import com.dre.brewery.BreweryPlugin
import com.dre.brewery.utility.Logging
import dev.jsinco.recipes.Util.getRecipeBookItem
import dev.jsinco.recipes.Util.giveItem
import dev.jsinco.recipes.commands.AddonSubCommand
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender

class GiveBook : AddonSubCommand {
    override fun execute(plugin: BreweryPlugin, sender: CommandSender, args: Array<out String>) {
        if (args.size < 2) {
            Logging.msg(sender, "Usage: /brewery recipes givebook <player> (Give a player the recipe book)")
            return
        }

        val player = Bukkit.getPlayerExact(args[1])
        if (player != null) {
            giveItem(player, getRecipeBookItem())
        }
    }

    override fun tabComplete(plugin: BreweryPlugin, sender: CommandSender, args: Array<out String>): List<String>? {
        return null
    }

    override fun getPermission(): String {
        return "brewery.recipesaddon.cmd.givebook"
    }

    override fun playerOnly(): Boolean {
        return false
    }
}
