package com.example.searchofateacher.model_for_get_data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
/*
Класс для получения данных о предмете в своем расписании
 */

@Parcelize
class TimePredmet (

    var predmet :String = "",
    var time: String = ""

        ):Parcelable