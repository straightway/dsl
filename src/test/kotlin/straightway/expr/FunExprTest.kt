/*
 * Copyright 2016 github.com/straightway
 *
 *  Licensed under the Apache License, Version 2.0 (the &quot;License&quot;);
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an &quot;AS IS&quot; BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package straightway.expr

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import straightway.error.Panic

class FunExprTest {

    @BeforeEach
    fun setup() {
        calls = 0
        sut = defaultSut
    }

    @Test
    fun arity() = assertEquals(1, sut.arity)

    @Test
    fun name() = assertEquals("name", sut.name)

    @Test
    fun functor() = assertEquals(-1, testInvoke(1))

    @Test
    fun `functor with too many arguments panics`() {
        assertThrows<Panic>(Panic::class.java) { sut(1, 2) }
    }

    @Test
    fun construction_withPrimaryConstructor() =
            ConstructionTest(FunExpr(1, "name") { testFunctor(it[0]) })
                    .withParameters(2)
                    .expecting(2)
                    .testFromFunction()

    @Test
    fun construction_fromFunction0() =
            ConstructionTest(DerivedFun0("name") { testFunctor(83) })
                    .expecting(83)
                    .testFromFunction()

    @Test
    fun construction_fromTypedFunction0() =
            ConstructionTest(DerivedFun0("name") { testFunctor(83) as Int })
                    .expecting(83)
                    .testFromFunction()

    @Test
    fun construction_fromUntypedFunction1() =
            ConstructionTest(DerivedFun1("name") { a: Any? -> testFunctor(a) })
                    .withParameters(2)
                    .expecting(2)
                    .testFromFunction()

    @Test
    fun construction_fromTypedFunction1() =
            ConstructionTest(FunExpr("name") { a: Int -> testFunctor(a) })
                    .withParameters(2)
                    .expecting(2)
                    .testFromTypedFunction()

    @Test
    fun construction_fromTypedFunction2() =
            ConstructionTest(FunExpr("name") { a: Int, _: Int -> testFunctor(a + 2) })
                    .withParameters(1, 2)
                    .expecting(3)
                    .testFromTypedFunction()

    @Test
    fun construction_fromUntypedFunction2() =
            ConstructionTest(DerivedFun2("name") { a: Any?, _: Any? -> testFunctor(a as Int + 2) })
                    .withParameters(1, 2)
                    .expecting(3)
                    .testFromFunction()

    @Test
    fun `construction with arity less than 0 panics`() {
        assertThrows<Panic>(Panic::class.java) { FunExpr(-1, "name") { it } }
    }

    @Test
    fun toString_yieldsName() = assertEquals(sut.name, sut.toString())

    private class DerivedFun0(name: String, functor: () -> Any?) : FunExpr(name, functor)
    private class DerivedFun1(name: String, functor: (Any?) -> Any?) : FunExpr(name, functor)
    private class DerivedFun2(name: String, functor: (Any?, Any?) -> Any?) : FunExpr(name, functor)

    private inner class ConstructionTest(val toTest: FunExpr) {

        fun expecting(result: Any): ConstructionTest {
            expectedResult = result
            return this
        }

        fun withParameters(vararg params: Any): ConstructionTest {
            invokeParams = params
            return this
        }

        fun testFromTypedFunction() {
            testFromFunction()
            for (paramIndex in invokeParams.indices) {
                assertThrows<ClassCastException>(ClassCastException::class.java) {
                    testInvoke(*(Array(invokeParams.size) {
                        if (it == paramIndex) invokeParams[it].toString()
                        else invokeParams[paramIndex]
                    }))
                }
            }
        }

        fun testFromFunction() {
            sut = toTest
            assertEquals(invokeParams.size, sut.arity)
            assertEquals("name", sut.name)
            assertEquals(expectedResult, testInvoke(*invokeParams))
        }

        private var invokeParams: Array<out Any> = arrayOf()
        private var expectedResult = Any()
    }

    private fun testInvoke(vararg params: Any?): Any? {
        calls = 0
        val result = sut(*params)
        assertEquals(1, calls, "Invalid number of functor calls")
        return result
    }

    private fun testFunctor(result: Any?): Any? {
        calls++
        return result
    }

    private val defaultSut = FunExpr(1, "name") { testFunctor(-(it[0] as Int)) }
    private var calls = 0
    private var sut = defaultSut
}
