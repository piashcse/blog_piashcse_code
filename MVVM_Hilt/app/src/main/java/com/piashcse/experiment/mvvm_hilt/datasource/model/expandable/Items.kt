package com.piashcse.experiment.mvvm_hilt.datasource.model.expandable

interface Item {
    fun getItemType(): Int
}

const val PARENT = 0
const val CHILD = 1

data class Parent(val parentItem: String) : Item {
    var parent  = parentItem
    val childItems = ArrayList<Child>()
    var isExpanded = false
    var selectedChild: Child? = null

    override fun getItemType() = PARENT
}

data class Child(
        val parent: Parent,
        val id: Int,
        val title: String,
        var price: Int = 0) : Item {
    override fun getItemType() = CHILD
    var isSelected: Boolean = false
}

data class Node(val id: Long, val parent: Node?) {

    var isExpanded = false
    val childList = ArrayList<Node>()
    var nestingLevel = 0

    init {
        calculateNestingLevel()
    }

    private fun calculateNestingLevel() {
        var current = parent
        while (current != null) {
            nestingLevel++
            current = current.parent
        }
    }
}