package ktn.jnt

import kotlin.math.max


class JapanNumeral {
    companion object Translator {
        private val mCardinals: Map<Long, List<String>>
            get() {
                return mapOf(
                    // Japanese string for "zero"
                    0L to listOf(tr("rei")),
                    // Japanese string for "one"
                    1L to listOf(tr("ichi")),
                    // Japanese string for "two"
                    2L to listOf(tr("ni")),
                    // Japanese string for "three"
                    3L to listOf(tr("san")),
                    // Japanese string for "four"
                    4L to listOf(tr("yon")),
                    // Japanese string for "five"
                    5L to listOf(tr("go")),
                    // Japanese string for "six"
                    6L to listOf(tr("roku")),
                    // Japanese string for "seven"
                    7L to listOf(tr("shichi")),
                    // Japanese string for "eight"
                    8L to listOf(tr("hachi")),
                    // Japanese string for "nine"
                    9L to listOf(tr("kyuu")),
                    // Japanese string for "ten"
                    10L to listOf(tr("juu")),
                    // Japanese string for "eleven"
                    11L to listOf(tr("juuichi")),
                    // Japanese string for "twelve"
                    12L to listOf(tr("juuni")),
                    // Japanese string for "thirteen"
                    13L to listOf(tr("juusan")),
                    // Japanese string for "fourteen"
                    14L to listOf(tr("juuyon")),
                    // Japanese string for "fifteen"
                    15L to listOf(tr("juugo")),
                    // Japanese string for "sixteen"
                    16L to listOf(tr("juuroku")),
                    // Japanese string for "seventeen"
                    17L to listOf(tr("juunana")),
                    // Japanese string for "eighteen"
                    18L to listOf(tr("juuhachi")),
                    // Japanese string for "nineteen"
                    19L to listOf(tr("juukyuu")),
                    // Japanese string for "twenty"
                    20L to listOf(tr("nijuu")),
                    // Japanese string for "thirty"
                    30L to listOf(tr("sanjuu")),
                    // Japanese string for "forty"
                    40L to listOf(tr("yonjuu")),
                    // Japanese string for "fifty"
                    50L to listOf(tr("gojuu")),
                    // Japanese string for "sixty"
                    60L to listOf(tr("rokujuu")),
                    // Japanese string for "seventy"
                    70L to listOf(tr("nanajuu")),
                    // Japanese string for "eighty"
                    80L to listOf(tr("hachijuu")),
                    // Japanese string for "ninety"
                    90L to listOf(tr("kyuujuu")),
                    // Japanese string for "one hundred"
                    100L to listOf(tr("hyaku")),
                    // Japanese string for "two hundred"
                    200L to listOf(tr("nihyaku")),
                    // Japanese string for "three hundred"
                    300L to listOf(tr("sanbyaku")),
                    // Japanese string for "four hundred"
                    400L to listOf(tr("yonhyaku")),
                    // Japanese string for "five hundred"
                    500L to listOf(tr("gohyaku")),
                    // Japanese string for "six hundred"
                    600L to listOf(tr("roppyaku")),
                    // Japanese string for "seven hundred"
                    700L to listOf(tr("nanahyaku")),
                    // Japanese string for "eight hundred"
                    800L to listOf(tr("happyaku")),
                    // Japanese string for "nine hundred"
                    900L to listOf(tr("kyuuhyaku")),
                    // Japanese string for "one thousand" (10^3)
                    1_000L to listOf(tr("issen")),
                    // Japanese string for "two thousand"
                    2_000L to listOf(tr("nisen")),
                    // Japanese string for "three thousand"
                    3_000L to listOf(tr("sanzen")),
                    // Japanese string for "four thousand"
                    4_000L to listOf(tr("yonsen")),
                    // Japanese string for "five thousand"
                    5_000L to listOf(tr("gosen")),
                    // Japanese string for "six thousand"
                    6_000L to listOf(tr("rokusen")),
                    // Japanese string for "seven thousand"
                    7_000L to listOf(tr("nanasen")),
                    // Japanese string for "eight thousand"
                    8_000L to listOf(tr("hassen")),
                    // Japanese string for "nine thousand"
                    9_000L to listOf(tr("kyuusen")),
                    // Japanese string for "ten thousand" (10^4)
                    10_000L to listOf(tr("ichiman")),
                    // Japanese string for "one hundred thousand" (10^5)
                    100_000L to listOf(tr("juuman")),
                    // Japanese string for "one million" (10^6)
                    1_000_000L to listOf(tr("hyakuman")),
                    // Japanese string for "ten million" (10^7)
                    10_000_000L to listOf(tr("senman")),
                    // Japanese string for "one hundred million" (10^8)
                    100_000_000L to listOf(tr("ichioku")),
                    // Japanese string for "one billion" (10^9)
                    1_000_000_000L to listOf(tr("juuoku")),
                    // Japanese string for "ten billion" (10^10)
                    10_000_000_000L to listOf(tr("hyakuoku")),
                    // Japanese string for "one hundred billion" (10^11)
                    100_000_000_000L to listOf(tr("senoku")),
                    // Japanese string for "one trillion" (10^12)
                    1_000_000_000_000L to listOf(tr("itchoo")),
                    // Japanese string for "ten trillion" (10^13)
                    10_000_000_000_000L to listOf(tr("juuchoo")),
                    // Japanese string for "one hundred trillion" (10^14)
                    100_000_000_000_000L to listOf(tr("hyakuchoo")),
                    // Japanese string for "one quadrillion" (10^15)
                    1_000_000_000_000_000L to listOf(tr("senchoo")),
                    // Japanese string for "ten quadrillion" (10^16)
                    10_000_000_000_000_000L to listOf(tr("ichikei"))
                )
            }

        /*private*/ fun quad(value: Int): List<String> {
            // E.g.: 1234 = 1*1000 + 2*100 + 3*10 + 4
            val isZero = value == 0
            val thousands: Long = value / 1000L
            val hundreds: Long = (value - thousands * 1000L) / 100L
            val dozens: Long = (value - thousands * 1000L - hundreds * 100L) / 10L
            val units: Long = value - thousands * 1000L - hundreds * 100L - dozens * 10L
            //println("quad($value): $thousands + $hundreds + $dozens + $units")

            // Validation: assert that map contains required items
            check((thousands == 0L) || (thousands != 0L && mCardinals.containsKey(thousands * 1000L)))
            check((hundreds == 0L) || (hundreds != 0L && mCardinals.containsKey(hundreds * 100L)))
            check((dozens == 0L) || (dozens != 0L && mCardinals.containsKey(dozens * 10L)))
            check((units == 0L) || (units != 0L && mCardinals.containsKey(units)))

            val thousandsStr = if (thousands != 0L) listOf(mCardinals[thousands * 1000L]!![0]) else emptyList()
            val hundredsStr = if (hundreds != 0L) listOf(mCardinals[hundreds * 100L]!![0]) else emptyList()
            val dozensStr = if (dozens != 0L) listOf(mCardinals[dozens * 10L]!![0]) else emptyList()
            val unitsStr = if (units != 0L || isZero) listOf(mCardinals[units]!![0]) else emptyList()

            //println("quad($value): ${thousandsStr} + ${hundredsStr} + ${dozensStr} + ${unitsStr}")
            return listOf(listOf(thousandsStr, hundredsStr, dozensStr, unitsStr).flatten().joinToString(" "))
        }

        /*private*/ fun toQuads(value: String): List<Int> {
            val rawValue = value.replace(CurrentLocale.thousandsSeparator(), "")
            val result = mutableListOf<Int>()
            for (i in rawValue.length downTo 1 step 4) {
                val quadStr = rawValue.substring(max(0, i - 4), i)
                var quadInt: Int
                try {
                    quadInt = quadStr.toInt()
                } catch (exc: NumberFormatException) {
                    // Invalid natural number string
                    println(tr("ERROR: invalid natural number ") + "'$rawValue'")
                    return emptyList()
                }
                result.add(quadInt)
            }

            return result.asReversed()
        }

        private fun quadSuffix(power: Int, value: List<Int>): String {
            return when (power) {
                5 -> {
                    val str = if (value.size - 5 >= 0) quad(value[value.size - 5]) else return ""
                    check(str.size == 1) { "10^16: only one translation item implemented yet" }
                    // Japanese string for "ten quadrillion" (10^16) suffix
                    return str[0] + " " + tr("kei") + " "
                }
                4 -> {
                    val str = if (value.size - 4 >= 0) quad(value[value.size - 4]) else return ""
                    check(str.size == 1) { "10^12: only one translation item implemented yet" }
                    // Japanese string for "trillion" (10^12) suffix
                    str[0] + " " + tr("choo") + " "
                }
                3 -> {
                    val str = if (value.size - 3 >= 0) quad(value[value.size - 3]) else return ""
                    check(str.size == 1) { "10^8: only one translation item implemented yet" }
                    // Japanese string for "one hundred million" (10^8) suffix
                    str[0] + " " + tr("oku") + " "
                }
                2 -> {
                    val str = if (value.size - 2 >= 0) quad(value[value.size - 2]) else return ""
                    check(str.size == 1) { "10^4: only one translation item implemented yet" }
                    // Japanese string for "ten thousand" (10^4) suffix
                    str[0] + " " + tr("man") + " "
                }
                1 -> {
                    val str = if (value.size - 1 >= 0) quad(value[value.size - 1]) else return ""
                    check(str.size == 1) { "10^1: only one translation item implemented yet" }
                    str[0]
                }
                else -> {
                    check(false)
                    ""
                }
            }
        }

        fun cardinal(value: String): List<String> {
            var result = ""
            val quads = toQuads(value)
            result += quadSuffix(5, quads)
            result += quadSuffix(4, quads)
            result += quadSuffix(3, quads)
            result += quadSuffix(2, quads)
            result += quadSuffix(1, quads)
            return listOf(result)
        }
    }
}
