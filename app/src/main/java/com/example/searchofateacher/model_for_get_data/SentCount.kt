package com.example.searchofateacher.model_for_get_data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
/*
Количество отправленных заявок(нужно для студентов)
 */

@Parcelize
class SentCount (

    var sent_count:Int = 0

        ):Parcelable