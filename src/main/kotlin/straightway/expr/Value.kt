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

import straightway.error.Panic

/**
 * A terminal baseValue expression.
 */
open class Value(private val value: Any) : Expr {
    override val arity = 0

    override fun invoke(vararg params: Any) =
            if (params.any())
                throw Panic("Value cannot take parameters on invocation, got: ${params.joinToString()}")
            else
                value

    override fun toString() = when (value) {
        is Array<*> -> value.asList().toString()
        is String -> value
        is Sequence<*> -> value.toList().toString()
        else -> value.toString()
    }
}