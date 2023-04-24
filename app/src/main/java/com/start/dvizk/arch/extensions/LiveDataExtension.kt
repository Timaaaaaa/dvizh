package com.start.dvizk.arch.extensions

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Observer
import androidx.annotation.NonNull
import com.start.dvizk.arch.SingleObserver

/**
 * Метод для подписки на Nonnull объект
 */
fun <T> LiveData<T>.observe(
        @NonNull owner: LifecycleOwner,
        observer: (t: T) -> Unit
) {
    this.observe(owner, Observer { it?.let(observer) })
}

/**
 * Метод для подписки на Nonnull объект
 */
fun <T> LiveData<T>.observeNonNull(
        @NonNull owner: LifecycleOwner,
        observer: (t: T) -> Unit
) {
    this.observe(owner, Observer { it?.let(observer) })
}

/**
 * Метод для подписки на Nullable объект
 */
fun <T> LiveData<T?>.observeNullable(
        @NonNull owner: LifecycleOwner,
        observer: (t: T?) -> Unit
) {
    this.observe(owner, Observer { observer.invoke(it) })
}

/**
 * Метод для одноразовой подписки на Nonnull объект, single event
 */
fun <T> LiveData<T>.observeOnce(
        @NonNull owner: LifecycleOwner,
        observer: (t: T) -> Unit
) {
    this.observe(owner, object: SingleObserver<T> {

        override fun onChanged(value: T) {
            value?.let {
                observer(value)
            }
        }
    })
}

/**
 * Метод для одноразовой подписки на Nullable объект, single event
 */
fun <T> LiveData<T?>.observeOnceNullable(
        @NonNull owner: LifecycleOwner,
        observer: (t: T?) -> Unit
) {
    this.observe(owner, object: SingleObserver<T?> {

        override fun onChanged(t: T?) {
            observer(t)
        }
    })
}

/**
 * Sets the value to the result of a function that is called when both `LiveData`s have data
 * or when they receive updates after that.
 */
fun <T, A, B> LiveData<A>.combineAndCompute(
        other: LiveData<B>,
        onChange: (A, B) -> T
): MediatorLiveData<T> {

    var firstSourceEmitted = false
    var secondSourceEmitted = false

    val result = MediatorLiveData<T>()

    val mergeF = {
        val firstSourceValue = this.value
        val secondSourceValue = other.value

        if (firstSourceEmitted && secondSourceEmitted) {
            result.value = onChange.invoke(firstSourceValue!!, secondSourceValue!! )
        }
    }

    result.addSource(this) {
        firstSourceEmitted = true
        mergeF.invoke()
    }
    result.addSource(other) {
        secondSourceEmitted = true
        mergeF.invoke()
    }

    return result
}