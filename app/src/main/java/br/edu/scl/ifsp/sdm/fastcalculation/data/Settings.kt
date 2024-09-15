package br.edu.scl.ifsp.sdm.fastcalculation.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Settings(
    val playerName: String = "",
    val rounds: Int = 0,
    val roundsInterval: Long = 0L,
    var isFromRestart: Boolean = false
): Parcelable
