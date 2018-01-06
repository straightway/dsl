/****************************************************************************
Copyright 2016 github.com/straightway

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 ****************************************************************************/
package straightway.dsl

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class CombinableExprTest {

    @Test fun combinedExpressionKeepsState() {
        val result = testExpr - testExpr.inState<Int>()
        @Suppress("USELESS_IS_CHECK")
        assertTrue(result is StateExpr<Int>)
    }

    @Test fun combineStatelessExpressions() {
        val result = testExpr - testExpr
        assertEquals(1, result(1))
    }

    private object testExpr : CombinableExpr, Expr by FunExpr("neg", untyped<Int, Int> { -it })
}
