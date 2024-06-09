package com.example.aplicacionjunio

interface ItemClickListener {
    fun onItemClick(character: Character)
    fun onEditClick(character: Character)
    fun onDeleteClick(character: Character)
}
