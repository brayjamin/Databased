package org.cursedmc

import org.cursedmc.sql.Databased
import me.vagdedes.mysql.database.MySQL
import me.vagdedes.mysql.database.SQL.exists
import org.bukkit.Server

class CursedPlayer (private val uuid: String) : Databased {
    private val playerGadgets = mutableMapOf<String, ArrayList<*>>()
    private val onlinePlayers = mutableSetOf<String>()
    private val servers = mutableMapOf<String, String>()
    private val server: Server = CursedPlugin.instance!!.server
    private fun runAsync(runnable: () -> Unit) {
        CursedPlugin.instance!!.server.scheduler.runTaskAsynchronously(CursedPlugin.instance!!, runnable)
    }
    fun getGadgets(): ArrayList<*>? {
        runAsync { Runnable {
            playerGadgets.clear()
            playerGadgets[uuid] = getList("players", "gadgets", "uuid", uuid) as ArrayList<*>
        }; }
        return playerGadgets[uuid]
    }
    fun hasGadget(gadget: String): Boolean {
        return getGadgets()!!.contains(gadget)
    }
    fun addGadget(gadget: String) {
        runAsync { Runnable {
            addString("players", "gadgets", "uuid", gadget, uuid)
        }
    }
    fun removeGadget(gadget: String) {
        runAsync { Runnable {
            removeEntryFromArray("players", "gadgets", "uuid", gadget, uuid)
        }; }
        }
    }
    fun getServer (): String? {
        runAsync { Runnable {
            val server = getString("players", "server", "uuid", uuid)
            if (server != null) servers[uuid] = server
        } }
        return servers[uuid]
    }
    fun isOnline (): Boolean {
        if (server.getPlayer(uuid)?.isOnline == true) return onlinePlayers.add(uuid)
        runAsync { Runnable {
            if (getBoolean("players", "online", "uuid", uuid) == true) onlinePlayers.add(uuid)
            else onlinePlayers.remove(uuid)
        } }
        return onlinePlayers.contains(uuid)
    }
    init {
        if (!MySQL.isConnected()) throw IllegalStateException("MySQL is not connected!")
        if (!exists("players", "uuid", uuid)) {
            try {
                MySQL.query("INSERT INTO players (uuid) VALUES ('$uuid')")
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        try {
            getGadgets()
            getServer()
            isOnline()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}