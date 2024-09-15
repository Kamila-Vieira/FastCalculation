package br.edu.scl.ifsp.sdm.fastcalculation.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GameResult(
    val points: Float = 0f,
    val settings: Settings = Settings()
): Parcelable
