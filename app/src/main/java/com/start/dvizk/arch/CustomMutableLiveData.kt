package com.start.dvizk.arch

import android.annotation.SuppressLint
import androidx.lifecycle.GenericLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.annotation.MainThread
import java.util.Collections
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicBoolean

class CustomMutableLiveData<T>: MutableLiveData<T>() {

    private val observers = ConcurrentHashMap<LifecycleOwner, MutableSet<SingleObserverWrapper>>()

    @MainThread
    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        if (observer !is SingleObserver) {
            super.observe(owner, observer)

            return
        }
        val wrapper = SingleObserverWrapper(owner, observer)
        val set = observers[owner]
        set?.apply {
            add(wrapper)
        } ?: run {
            val tempObserversSet = Collections.newSetFromMap(ConcurrentHashMap<SingleObserverWrapper, Boolean>())
            tempObserversSet.add(wrapper)
            observers[owner] = tempObserversSet
        }
    }

    override fun removeObservers(owner: LifecycleOwner) {
        observers.remove(owner)
        super.removeObservers(owner)
    }

    override fun removeObserver(observer: Observer<in T>) {
        if (observer !is SingleObserverWrapper) {
            super.removeObserver(observer)

            return
        }
        observers.forEach {
            val lifecycleObservers: MutableSet<*> = it.value
            val isRemoveSuccess = lifecycleObservers.remove(observer)
            if (isRemoveSuccess) {
                if (it.value.isEmpty()) {
                    observers.remove(it.key)
                }

                return@forEach
            }
        }
    }

    @MainThread
    override fun setValue(value: T?) {
        super.setValue(value)
        observers.forEach {
            it.value.forEach {
                it.resetPending()
                if (it.shouldBeActive()) {
                    value?.let { it1 -> it.onChanged(it1) }
                }
            }
        }
    }

    private fun dispatchValueIfActive(initiator: SingleObserverWrapper) {
        if (!initiator.shouldBeActive()) {

            return
        }
        value?.let { initiator.onChanged(it) }
    }

    @SuppressLint("RestrictedApi")
    private inner class SingleObserverWrapper(
            private var owner: LifecycleOwner,
            private val observer: Observer<in T>
    ): Observer<T>, GenericLifecycleObserver {

        private val pending = AtomicBoolean(false)

        init {
            owner.lifecycle.addObserver(this)
        }

        override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
            if (owner.lifecycle.currentState == Lifecycle.State.DESTROYED) {
                owner.lifecycle.removeObserver(this)
                removeObserver(this@SingleObserverWrapper)

                return
            }
            dispatchValueIfActive(this)
        }

        fun shouldBeActive(): Boolean
                = owner.lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)

        fun resetPending() {
            pending.set(true)
        }

        override fun onChanged(value: T) {
            if (!pending.compareAndSet(true, false)) {

                return
            }
            observer.onChanged(value)
        }
    }
}