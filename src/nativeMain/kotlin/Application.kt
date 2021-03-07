package ktn.jnt

import kotlinx.cinterop.*
import platform.posix.*

class Application {
    companion object {
        fun filePath(): String {
            // NOTE: https://kotlinlang.org/docs/mapping-strings-from-c.html
            val buf = ByteArray(1024)
            val bufSize : size_t = buf.size.convert<size_t>() - 1u
            buf.usePinned { pinned ->
                if (readlink("/proc/${getpid()}/exe", pinned.addressOf(0), bufSize) < 0L) {
                    println("ERROR: readlink failed with errno: $errno and errno string: ${strerror(errno)}")
                    return ""
                }
            }
            return buf.toKString()
        }

        fun dirPath(): String {
            val result = filePath()
            val index = result.lastIndexOf('/')
            if (index < 0) return ""
            return result.substring(0, index)
        }
    }
}
