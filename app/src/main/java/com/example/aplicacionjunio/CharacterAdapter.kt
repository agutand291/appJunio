package com.example.aplicacionjunio

import android.view.LayoutInflater
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CharacterAdapter(
    private val characterList: List<Character>,
    private val itemClickListener: ItemClickListener
) : RecyclerView.Adapter<CharacterAdapter.CharacterViewHolder>() {

    inner class CharacterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.textViewName)
        val race: TextView = itemView.findViewById(R.id.textViewRace)
        val charClass: TextView = itemView.findViewById(R.id.textViewClass)
        val level: TextView = itemView.findViewById(R.id.textViewLevel)

        init {
            itemView.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val character = characterList[position]
                    itemClickListener.onItemClick(character)
                }
            }

            itemView.setOnLongClickListener { view ->
                showPopupMenu(view, bindingAdapterPosition)
                true
            }
        }

        private fun showPopupMenu(view: View, position: Int) {
            val popup = PopupMenu(view.context, view)
            val inflater: MenuInflater = popup.menuInflater
            inflater.inflate(R.menu.char_menu, popup.menu)
            popup.setOnMenuItemClickListener { menuItem ->
                holdMenu(menuItem, position)
            }
            popup.show()
        }

        private fun holdMenu(item: MenuItem, position: Int): Boolean {
            val character = characterList[position]
            return when (item.itemId) {
                R.id.action_edit -> {
                    itemClickListener.onEditClick(character)
                    true
                }
                R.id.action_delete -> {
                    itemClickListener.onDeleteClick(character)
                    true
                }
                else -> false
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_character, parent, false)
        return CharacterViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val currentCharacter = characterList[position]
        holder.name.text = currentCharacter.name
        holder.race.text = currentCharacter.race
        holder.charClass.text = currentCharacter.characterClass
        holder.level.text = currentCharacter.level.toString()
    }

    override fun getItemCount() = characterList.size
}
