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
import org.junit.jupiter.api.Assertions.assertSame
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import straightway.error.Panic

class ValueTest {

    @Test
    fun invocation_returnsValue() {
        val sut = Value(TestValue)
        assertSame(TestValue, sut())
    }

    @Test
    fun `invocation with parameters panics`() {
        val sut = Value(TestValue)
        assertThrows<Panic>(Panic::class.java) { sut(1) }
    }

    @Test
    fun toString_returnsWrappedValueStringRepresentation() {
        val sut = Value(83)
        assertEquals("83", sut.toString())
    }

    @Test
    fun toString_returnsArrayElements() {
        val sut = Value(arrayOf(1, 2, 3))
        assertEquals("[1, 2, 3]", sut.toString())
    }

    @Test
    fun toString_returnsPlainString() {
        val sut = Value("123")
        assertEquals("123", sut.toString())
    }

    @Test
    fun toString_returnsSequenceElements() {
        val sut = Value("123".asSequence())
        assertEquals("[1, 2, 3]", sut.toString())
    }

    @Test
    fun toString_returnsCharSequenceElements() {
        val sut = Value(sequenceOf('1', '2', '3'))
        assertEquals("[1, 2, 3]", sut.toString())
    }

    @Test
    fun hasArity0() {
        val sut = Value(83)
        assertEquals(0, sut.arity)
    }

    @Test
    fun isDirectlyVisited() {
        val sut = Value(83)
        val visitor = StackExprVisitor()
        sut.accept { visitor.visit(it) }
        assertEquals(listOf(sut), visitor.stack)
    }

    private object TestValue
}