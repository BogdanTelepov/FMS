package com.neobis.fms.model.category

class CategoryIdName(val id: Int, val name: String) {

    override fun toString(): String {
        return name
    }

    override fun equals(other: Any?): Boolean {
        if (other is CategoryIdName) {
            if (other.id == id && other.name == name) return true
        }

        return false
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * id + name.hashCode()
        return result
    }
}