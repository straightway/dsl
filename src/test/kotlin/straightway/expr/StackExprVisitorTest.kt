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
import org.junit.jupiter.api.Test

class StackExprVisitorTest {

    @Test
    fun initiallyEmpty() {
        val sut = StackExprVisitor()
        assertEquals(0, sut.stack.size)
    }

    @Test
    fun visitedExpression_pushedToStack() {
        val sut = StackExprVisitor()
        val visitedExpressions = listOf(Value(83), FunExpr<Int>("op") { -it })
        for (e in visitedExpressions) sut.visit(e)
        assertEquals(visitedExpressions, sut.stack)
    }
}