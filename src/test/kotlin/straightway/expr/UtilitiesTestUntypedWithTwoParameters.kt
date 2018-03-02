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

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class UtilitiesTestUntypedWithTwoParameters {

    @Test
    fun returnsLambdaWithAnyParametersAndReturnType() {
        val result = untyped({ i: Int, d: Double -> i * d })
        @Suppress("USELESS_IS_CHECK")
        Assertions.assertTrue(result is (Any?, Any?) -> Any?)
    }

    @Test
    fun returnedLambdaExecutesTypedParameterLambda() {
        val input1: Any = 3
        val input2: Any = Math.PI
        val result = untyped({ i: Int, d: Double -> i * d })
        val output = result(input1, input2)
        Assertions.assertEquals(3 * Math.PI, output)
    }
}
