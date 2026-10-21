package id.ac.polbeng.triwahyuningsih.utsmobileii.response

import com.google.gson.annotations.SerializedName
import id.ac.polbeng.triwahyuningsih.utsmobileii.model.ProductItem

data class ProductResponse(
    @field:SerializedName("products")
    val products: List<ProductItem>,

    @field:SerializedName("total")
    val total: Int,

    @field:SerializedName("skip")
    val skip: Int,

    @field:SerializedName("limit")
    val limit: Int
)