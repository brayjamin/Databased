package org.cursedmc

import org.cursedmc.sql.Databased
import org.cursedmc.sql.SQLConfig
import org.bukkit.plugin.java.JavaPlugin
import org.cursedmc.sql.Connection

class CursedPlugin: JavaPlugin() {
    companion object {
        var instance: CursedPlugin? = null
        var sqlConfig: SQLConfig? = null
        var connection: Connection? = null
            private set
    }
    override fun onEnable() {
        instance = this
        sqlConfig = SQLConfig(
                host = config.getString("database.host"),
                port = config.getString("database.port"),
                username = config.getString("database.username"),
                password = config.getString("database.password"),
                driver = config.getString("database.driver"))
        connection = Connection(sqlConfig!!)
        connection!!.connect()

    }

    override fun onDisable() {
        connection?.disconnect()
        this.logger.info("Disconnected from database")
    }
}