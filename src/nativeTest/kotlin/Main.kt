package ktn.jnt

import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

// About testing private API: https://stanislavkozlovski.medium.com/kotlin-unit-testing-classes-without-leaking-public-api-871468695447

class TranslateTest {
    private fun grouped(vararg groups: String) = groups.joinToString(CurrentLocale.thousandsSeparator())

    @BeforeTest
    fun init() = CurrentLocale.initLocale()

    // TODO: add tr() tests

    @Test
    fun testGrouped() {
        assertEquals(grouped("1", "234"), "1" + CurrentLocale.thousandsSeparator() + "234")
    }

    @Test
    fun testToQuads() {
        // NOTE: we don't know user locale in advance, so use helper `grouped` function
        assertEquals(JapanNumeral.toQuads("1"), listOf(1))
        assertEquals(JapanNumeral.toQuads("12"), listOf(12))
        assertEquals(JapanNumeral.toQuads("123"), listOf(123))
        assertEquals(JapanNumeral.toQuads(grouped("1", "234")), listOf(1234))
        assertEquals(JapanNumeral.toQuads(grouped("12", "345")), listOf(1, 2345))
        assertEquals(JapanNumeral.toQuads(grouped("123", "456")), listOf(12, 3456))
        assertEquals(JapanNumeral.toQuads(grouped("1", "234", "567")), listOf(123, 4567))
        assertEquals(JapanNumeral.toQuads(grouped("12", "345", "678")), listOf(1234, 5678))
        assertEquals(JapanNumeral.toQuads(grouped("123", "456", "789")), listOf(1, 2345, 6789))
        assertEquals(JapanNumeral.toQuads(grouped("18", "446", "744", "073", "709", "551", "615")), listOf(1844, 6744, 737, 955, 1615))
    }

    @Test
    fun testQuad() {
        assertEquals(JapanNumeral.quad(1), listOf("いち"))
        assertEquals(JapanNumeral.quad(12), listOf("じゅう に"))
        assertEquals(JapanNumeral.quad(123), listOf("ひゃく にじゅう さん"))
        assertEquals(JapanNumeral.quad(1234), listOf("いっせん にひゃく さんじゅう よん"))
    }

    @Test
    fun testCardinalTranslate() {
        assertEquals(JapanNumeral.cardinal("0"), listOf("れい"))
        assertEquals(JapanNumeral.cardinal("1"), listOf("いち"))
        assertEquals(JapanNumeral.cardinal("10"), listOf("じゅう"))
        assertEquals(JapanNumeral.cardinal("12"), listOf("じゅう に"))
        assertEquals(JapanNumeral.cardinal("123"), listOf("ひゃく にじゅう さん"))
        assertEquals(JapanNumeral.cardinal(grouped("1", "234")), listOf("いっせん にひゃく さんじゅう よん"))
        assertEquals(JapanNumeral.cardinal(grouped("12", "345")), listOf("いち まん にせん さんびゃく よんじゅう ご"))
        assertEquals(JapanNumeral.cardinal(grouped("123", "456")), listOf("じゅう に まん さんぜん よんひゃく ごじゅう ろく"))
        assertEquals(JapanNumeral.cardinal(grouped("1", "234", "567")), listOf("ひゃく にじゅう さん まん よんせん ごひゃく ろくじゅう しち"))
        assertEquals(JapanNumeral.cardinal(grouped("12", "345", "678")), listOf("いっせん にひゃく さんじゅう よん まん ごせん ろっぴゃく ななじゅう はち"))
        assertEquals(JapanNumeral.cardinal(grouped("123", "456", "789")), listOf("いち おく にせん さんびゃく よんじゅう ご まん ろくせん ななひゃく はちじゅう きゅう"))
        assertEquals(JapanNumeral.cardinal(grouped("18", "446", "744", "073" ,"709", "551", "615")), listOf("いっせん はっぴゃく よんじゅう よん けい ろくせん ななひゃく よんじゅう よん ちょう ななひゃく さんじゅう しち おく きゅうひゃく ごじゅう ご まん いっせん ろっぴゃく じゅう ご"))
    }
}
