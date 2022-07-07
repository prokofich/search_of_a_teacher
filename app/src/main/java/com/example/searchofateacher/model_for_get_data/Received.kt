package com.example.searchofateacher.model_for_get_data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
/*
Класс для получения данных о предмете и студенте,который отправил заявку учителю
 */

@Parcelize
class Received (

    var id_student:String = "",
    var message:String = "",
    var number_predmet:Int = 0

        ):Parcelable