package ru.brz.tictactoe.model

import android.graphics.drawable.Drawable

data class Player (
    var name: String,
    var iconId: Drawable,
    var signId: Int,
    var score: Int
){
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Player) return false

        if (name != other.name) return false
        if (signId != other.signId) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + signId
        return result
    }
}