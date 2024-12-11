package dev.jsinco.recipes.commands.subcommands

import com.dre.brewery.BreweryPlugin
import com.dre.brewery.utility.Logging
import dev.jsinco.recipes.Util.giveItem
import dev.jsinco.recipes.commands.AddonSubCommand
import dev.jsinco.recipes.recipe.Recipe
import dev.jsinco.recipes.recipe.RecipeItem
import dev.jsinco.recipes.recipe.RecipeUtil.getAllRecipes
import dev.jsinco.recipes.recipe.RecipeUtil.getRecipeFromKey
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class GiveRecipeItem : AddonSubCommand {
    override fun execute(plugin: BreweryPlugin, sender: CommandSender, args: Array<out String>) {
        val player = if (sender is Player) {
            sender
        } else if (args.size >= 3) {
            Bukkit.getPlayerExact(args[2]) ?: run {
                Logging.msg(sender, "Player not found")
                return
            }
        } else {
            Logging.msg(sender, "Specify a player to give the item to /brewery recipes give <recipe> <player>")
            return
        }


        val recipe = getRecipeFromKey(args[1])
        if (recipe == null) {
            Logging.msg(sender, "Recipe not found")
            return
        }
        val recipeItem = RecipeItem(recipe).item

        giveItem(player, recipeItem)
    }

    override fun tabComplete(plugin: BreweryPlugin, sender: CommandSender, args: Array<out String>): List<String>? {
        if (args.size == 2) {
            return getAllRecipes().stream().map(Recipe::recipeKey).toList()
        }
        return null
    }

    override fun getPermission(): String {
        return "brewery.recipesaddon.cmd.give"
    }

    override fun playerOnly(): Boolean {
        return false
    }
}
