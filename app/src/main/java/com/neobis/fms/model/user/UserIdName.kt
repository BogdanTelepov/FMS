package com.neobis.fms.model.user

class UserIdName(val id: Int, val name: String) {

    override fun toString(): String {
        return name
    }

    override fun equals(other: Any?): Boolean {
        if (other is UserIdName) {
            if (other.id == id && other.name == name) return true
        }
        return false
    }

    override fun hashCode(): Int {
        return id * 31 + name.hashCode()
    }
}