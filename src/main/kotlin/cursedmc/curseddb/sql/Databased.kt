package cursedmc.curseddb.sql

import me.vagdedes.mysql.database.MySQL
import me.vagdedes.mysql.database.SQL
import java.sql.ResultSet
import java.util.ArrayList

interface Databased {
    private fun query (table: String, key: String, value: String): ResultSet? {
        return MySQL.query("SELECT * FROM $table WHERE $key = '$value'")
    }
    fun getList (table: String, column: String, key: String, value: String): ArrayList<Any>? {
        return SQL.listGet("$column", arrayOf("$key = $value", "enabled = 1"), table)
    }
    fun getString (table: String, column: String, key: String, value: String): String? {
        return query(table, key, value)?.getString(column)
    }
    fun setString (table: String, column: String, key: String, value: String, newValue: String) {
        MySQL.update("UPDATE $table SET $column = '$newValue' WHERE $key = '$value'")
    }
    fun addString (table: String, column: String, key: String, columnValue: String, keyValue: String) {
        MySQL.update("INSERT INTO $table ($column, $key) VALUES ('$columnValue', '$keyValue')")
    }
    fun removeColumn (table: String, column: String, key: String, value: String) {
        MySQL.update("DELETE FROM $table WHERE $column = '$value' AND $key = '$value'")
    }
    fun removeColumnsByKey (table: String, column: String, key: String) {
        MySQL.update("DELETE FROM $table WHERE $column = '$key'")
    }
    fun removeEntryFromArray (table: String, column: String, key: String, value: String, entry: String) {
        MySQL.update("UPDATE $table SET $column = array_remove($column, '$entry') WHERE $key = '$value'")
    }

}
