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


import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class UtilitiesTest_untypedOpWithTwoParameters {

    @Test
    fun returnsLambdaWithAnyParametersAndReturnType() {
        val result = untypedOp<Int>({ i: Int, d: Int -> i * d})
        @Suppress("USELESS_IS_CHECK")
        Assertions.assertTrue(result is (Any, Any) -> Any)
    }

    @Test
    fun returnedLambdaExecutesTypedParameterLambda() {
        val input1: Any = 3
        val input2: Any = 5
        val result = untypedOp<Int>({ a, b -> a - b })
        val output = result(input1, input2)
        Assertions.assertEquals(-2, output)
    }
}
