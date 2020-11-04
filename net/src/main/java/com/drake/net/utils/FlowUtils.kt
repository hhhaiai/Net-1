/*
 * Copyright (C) 2018 Drake, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.drake.net.utils

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import com.drake.net.scope.AndroidScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged

/**
 * 收集Flow结果并过滤重复结果
 */
fun <T> Flow<List<T>>.listen(
    lifecycleOwner: LifecycleOwner? = null,
    lifeEvent: Lifecycle.Event = Lifecycle.Event.ON_DESTROY,
    dispatcher: CoroutineDispatcher = Dispatchers.Main,
    block: (List<T>) -> Unit
): AndroidScope {
    return AndroidScope(lifecycleOwner, lifeEvent, dispatcher).launch {
        distinctUntilChanged().collect { data ->
            block(data)
        }
    }
}

