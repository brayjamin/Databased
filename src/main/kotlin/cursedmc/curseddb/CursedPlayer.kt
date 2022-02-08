package cursedmc.curseddb

import cursedmc.curseddb.sql.Databased
import me.vagdedes.mysql.database.MySQL
import me.vagdedes.mysql.database.SQL.exists

class CursedPlayer (private val uuid: String) : Databased {
    private val playerGadgets = mutableMapOf<String, ArrayList<*>>()
    fun getGadgets(): ArrayList<*>? {
        CursedPlugin.instance!!.server.scheduler.runTaskAsynchronously(CursedPlugin.instance!!, Runnable {
            playerGadgets.clear()
            playerGadgets[uuid] = getList("players", "gadgets", "uuid", uuid) as ArrayList<*>
        })
        return playerGadgets[uuid]
    }
    fun hasGadget(gadget: String): Boolean {
        return getGadgets()?.toString()?.contains(gadget) ?: false
    }
    fun addGadget(gadget: String) {
        addString("players", "gadgets", "uuid", gadget, uuid)
    }
    fun removeGadget(gadget: String) {
        removeEntryFromArray("players", "gadgets", "uuid", gadget, uuid)
    }
    init {
        if (!exists("players", "uuid", uuid)) {
            MySQL.query("INSERT INTO players (uuid) VALUES ('$uuid')")
        }
        getGadgets()
    }
}