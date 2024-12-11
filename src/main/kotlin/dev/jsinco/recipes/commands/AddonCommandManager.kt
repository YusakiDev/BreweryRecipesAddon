package dev.jsinco.recipes.commands

import com.dre.brewery.BreweryPlugin
import com.dre.brewery.api.addons.AddonCommand
import com.dre.brewery.configuration.files.Lang
import com.dre.brewery.utility.Logging
import dev.jsinco.recipes.commands.subcommands.GiveBook
import dev.jsinco.recipes.commands.subcommands.GiveRecipeItem
import dev.jsinco.recipes.commands.subcommands.GuiCommand
import dev.jsinco.recipes.commands.subcommands.OpenRecipeBookCommand
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class AddonCommandManager(val plugin: BreweryPlugin) : AddonCommand {

    private val commands: Map<String, AddonSubCommand> = mapOf(
        "give" to GiveRecipeItem(),
        "givebook" to GiveBook(),
        "gui" to GuiCommand(),
        "openrecipebook" to OpenRecipeBookCommand()
    )


    override fun execute(plugin: BreweryPlugin, lang: Lang, sender: CommandSender, label: String, args: Array<out String>) {
        val argsAsMutable = args.toMutableList()
        argsAsMutable.removeAt(0)
        val finalArgs = argsAsMutable.toList().toTypedArray()
        executeSubcommand(finalArgs, sender)
    }

    override fun tabComplete(plugin: BreweryPlugin, sender: CommandSender, label: String, args: Array<out String>): List<String> {
        val argsAsMutable = args.toMutableList()
        argsAsMutable.removeAt(0)
        val finalArgs = argsAsMutable.toList().toTypedArray()
        return tabCompleteSubcommand(finalArgs, sender)
    }

    override fun permission(): String {
        return "brewery.cmd.recipes"
    }

    override fun playerOnly(): Boolean {
        return false
    }


    private fun executeSubcommand(args: Array<out String>, sender: CommandSender): Boolean {
        if (args.isEmpty()) {
            Logging.msg(sender, "&cUsage: /brewery recipes <subcommand>")
            return true
        }

        val subCommand = commands[args[0]] ?: return false
        if (!sender.hasPermission(subCommand.getPermission())) {
            Logging.msg(sender, "&cYou do not have permission to execute this command.")
            return true
        } else if (subCommand.playerOnly() && sender !is Player) {
            Logging.msg(sender, "&cThis command can only be executed by players.")
            return true
        }
        subCommand.execute(plugin, sender, args)
        return true
    }

    private fun tabCompleteSubcommand(args: Array<out String>, sender: CommandSender): MutableList<String> {
        if (args.size == 1) return commands.keys.toMutableList()
        val subCommand = commands[args[0]] ?: return Bukkit.getOnlinePlayers().map { it.name }.toMutableList()
        return subCommand.tabComplete(plugin, sender, args)?.toMutableList() ?: return Bukkit.getOnlinePlayers().map { it.name }.toMutableList()
    }
}