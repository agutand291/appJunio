package com.example.aplicacionjunio

data class CharacterSheetObj(
    val charName: String,
    val clase: String,
    var strength: Int,
    var dexterity: Int,
    var constitution: Int,
    var intelligence: Int,
    var wisdom: Int,
    var charisma: Int,
    var items: List<ItemObj>)