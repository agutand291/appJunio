package com.example.aplicacionjunio

import android.os.Parcel
import android.os.Parcelable


data class Character(
    val id: String = "",
    val name: String = "",
    val race: String = "",
    val characterClass: String = "",
    val level: Int = 0,
    val strength: Int = 0,
    val dexterity: Int = 0,
    val intelligence: Int = 0,
    val wisdom: Int = 0,
    val constitution: Int = 0,
    val charisma: Int = 0
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(name)
        parcel.writeString(race)
        parcel.writeString(characterClass)
        parcel.writeInt(level)
        parcel.writeInt(strength)
        parcel.writeInt(dexterity)
        parcel.writeInt(intelligence)
        parcel.writeInt(wisdom)
        parcel.writeInt(constitution)
        parcel.writeInt(charisma)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Character> {
        override fun createFromParcel(parcel: Parcel): Character {
            return Character(parcel)
        }

        override fun newArray(size: Int): Array<Character?> {
            return arrayOfNulls(size)
        }
    }
}