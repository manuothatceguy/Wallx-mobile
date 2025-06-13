package ar.edu.itba.hci.wallx.model.payment

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class PagingData(

    @SerialName("page") var page: Int? = null,
    @SerialName("pageSize") var pageSize: Int? = null,
    @SerialName("pageCount") var pageCount: Int? = null,
    @SerialName("totalCount") var totalCount: Int? = null

)