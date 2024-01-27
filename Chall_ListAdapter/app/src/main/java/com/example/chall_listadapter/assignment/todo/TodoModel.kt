package com.example.chall_listadapter.assignment.todo

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.UUID

@Parcelize
data class TodoModel(
    val id: UUID = UUID.randomUUID(),
    val title: String? = null,
    val content: String? = null,
) : Parcelable