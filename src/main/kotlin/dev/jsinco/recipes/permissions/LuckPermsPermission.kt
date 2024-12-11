package dev.jsinco.recipes.permissions

import net.luckperms.api.LuckPerms
import net.luckperms.api.node.Node
import org.bukkit.Bukkit
import org.bukkit.entity.Player

class LuckPermsPermission : PermissionManager {


    private val luckPermsInstance: LuckPerms = run {
        val rsp = Bukkit.getServicesManager().getRegistration(LuckPerms::class.java)
        checkNotNull(rsp) { "LuckPerms is not installed on this server!" }
        rsp.provider
    }

    override fun setPermission(permission: String, player: Player, value: Boolean) {
        val node: Node = Node.builder(permission).value(value).build()
        val user = checkNotNull(luckPermsInstance.userManager.getUser(player.uniqueId))
        user.data().add(node)
        luckPermsInstance.userManager.saveUser(user)
    }

    override fun removePermission(permission: String, player: Player) {
        val user = checkNotNull(luckPermsInstance.userManager.getUser(player.uniqueId))
        user.data().remove(Node.builder(permission).build())
        luckPermsInstance.userManager.saveUser(user)
    }


}
