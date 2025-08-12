package com.standard.util.json

object JsonUtil {

    fun jsonStrToMap(jsonStr: String): Map<String, Any> {
        return jsonStr
            .removeSurrounding("{", "}")
            .split(",")
            .mapNotNull {
                val (key, value) = it.split(":", limit = 2).map(String::trim).takeIf { it.size == 2 }
                    ?: return@mapNotNull null

                val cleanedKey = key.removeSurrounding("\"")

                val cleanedValue = if (value.startsWith("\"") && value.endsWith("\"")) {
                    value.removeSurrounding("\"")
                } else {
                    value.toInt()
                }

                cleanedKey to cleanedValue
            }.toMap()
    }

    fun toString(mapList: List<Map<String, Any?>>): String {
        return mapList.joinToString(
            prefix = "[\n", separator = ",\n", postfix = "\n]"
        ) { map -> toString(map).prependIndent("    ") }
    }

    fun toString(map: Map<String, Any?>): String {
        return map.entries.joinToString(
            prefix = "{\n", separator = ",\n", postfix = "\n}"
        ) { (key, value) ->
            val formattedKey = "\"$key\""
            val formattedValue = when (value) {
                is String -> "\"$value\""
                else -> value
            }
            "    $formattedKey: $formattedValue"
        }
    }
}