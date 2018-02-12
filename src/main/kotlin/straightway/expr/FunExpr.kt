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
 * An expression who's value is computed by a given functor.
 */
open class FunExpr(
        final override val arity: Int,
        val name: String,
        private val functor: (Array<out Any>) -> Any)
    : Expr {

    constructor(name: String, functor: () -> Any) :
            this(0, name, { _ -> functor() })
    constructor(name: String, functor: (Any) -> Any) :
            this(1, name, { args -> functor(args[0]) })
    constructor(name: String, functor: (Any, Any) -> Any) :
            this(2, name, { args -> functor(args[0], args[1]) })

    override operator fun invoke(vararg params: Any): Any {
        if (params.size != arity)
            throw Panic("Invalid number of parameters. Expected: $arity, got: ${params.size}")
        return functor(params)
    }

    override fun toString() = name

    companion object {
        inline operator fun <reified TArg> invoke(name: String, noinline functor: (TArg) -> Any) =
                FunExpr(name, untyped(functor))

        inline operator fun <reified TArg1, reified TArg2>
                invoke(name: String, noinline functor: (TArg1, TArg2) -> Any) =
                FunExpr(name, untyped(functor))
    }
    init {
        if (arity < 0)
            throw Panic("Expressions must have non-negative arity (arity: $arity)")
    }
}