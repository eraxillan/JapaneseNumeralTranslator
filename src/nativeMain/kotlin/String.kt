package ktn.jnt

import kotlinx.cinterop.getBytes
import kotlinx.cinterop.utf8

fun String.getUtf8Codes(): String {
    var result = ""
    for (c in this) {
        var hex = c.toInt().toString(16)
        hex = hex.toUpperCase()
        hex = hex.padStart(4, '0')
        result += hex
        result += ' '
    }
    return result.dropLast(1)
}

fun String.getUtf8Bytes(): String {
    var result = ""
    for (c in this.utf8.getBytes().dropLast(1)) {
        // result += "%02X".format(c.toUByte()) // No String.format() function in Kotlin/Native
        var hex = c.toUByte().toString(16)
        hex = hex.toUpperCase()
        hex = hex.padStart(2, '0')
        result += hex
        result += ' '
    }
    return result.dropLast(1)
}
