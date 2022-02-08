package cursedmc.curseddb

import cursedmc.curseddb.sql.Databased
import cursedmc.curseddb.sql.SQLConfig
import org.bukkit.plugin.java.JavaPlugin

class CursedPlugin: JavaPlugin() {
    companion object {
        var instance: CursedPlugin? = null
        var sqlConfig: SQLConfig? = null
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
        CursedInit(sqlConfig!!)

    }
}