package com.example.searchofateacher.model_for_get_data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
/*
Класс для получения данных о предмете учителя
 */

@Parcelize
class PredmetUser(

    var predmet:String = "",
    var price:String = "",
    var text:String = ""

):Parcelable