package cursedmc.curseddb.sql

import me.vagdedes.mysql.basic.Config
import me.vagdedes.mysql.database.MySQL

open class Connection(config: SQLConfig) {
    var config: SQLConfig? = config
    fun connect() {
        MySQL.connect()
        if (MySQL.isConnected()) {
            println("Successfully connected to the database!")
        } else {
            println("Failed to connect to the database!")
        }
    }
    fun disconnect() {
        MySQL.disconnect()
    }

    init {
        Config.setHost(config.host)
        Config.setUser(config.username)
        Config.setPassword(config.password)
        Config.setDatabase(config.database)
        Config.setDriver(config.driver)
        Config.reload()
        this.config = config
    }
}