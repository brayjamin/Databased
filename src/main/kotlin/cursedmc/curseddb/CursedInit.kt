package cursedmc.curseddb

import cursedmc.curseddb.sql.Databased
import cursedmc.curseddb.sql.SQLConfig

class CursedInit (sqlConfig: SQLConfig) {
    val databased = Databased(sqlConfig)
}