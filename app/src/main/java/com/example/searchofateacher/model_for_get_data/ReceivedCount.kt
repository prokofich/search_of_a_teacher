package com.example.searchofateacher.model_for_get_data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
/*
Количество полученных заявок(для учителя)
 */

@Parcelize
class ReceivedCount (

    var received_count:Int = 0

        ):Parcelable