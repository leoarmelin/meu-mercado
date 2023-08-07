package com.leoarmelin.scrapper.models

import com.leoarmelin.sharedmodels.Unity

internal data class ScrapperProduct(
    var name: String = "",
    var quantity: Double = 0.0,
    var unity: Unity = Unity.UN,
    var unityValue: Double = 0.0,
    var totalValue: Double = 0.0
)
