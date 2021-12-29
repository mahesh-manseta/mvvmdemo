package com.maheshmanseta.mvvm.ui.dashboard

import java.util.LinkedList

import java.util.LinkedHashSet


internal class MyCaches(capacity: Int) {
    var cache: MutableSet<Int>
    var capacity: Int

    operator fun get(key: Int): Boolean {
        if (!cache.contains(key)) return false
        cache.remove(key)
        cache.add(key)
        return true
    }

    fun refer(key: Int) {
        if (get(key) == false) put(key)
    }

    fun display() {
        val list = LinkedList(cache)
        val itr = list.descendingIterator()
        while (itr.hasNext()) print(itr.next().toString() + " ")
    }

    fun put(key: Int) {
        if (cache.size == capacity) {
            val firstKey = cache.iterator().next()
            cache.remove(firstKey)
        }
        cache.add(key)
    }

//    companion object {
//        @JvmStatic
//        fun main(args: Array<String>) {
//            val ca = MyCaches(4)
//            ca.refer(1)
//            ca.refer(2)
//            ca.refer(3)
//            ca.refer(1)
//            ca.refer(4)
//            ca.refer(5)
//            ca.display()
//        }
//    }

    init {
        cache = LinkedHashSet(capacity)
        this.capacity = capacity
    }
}