package ar.edu.itba.hci.wallx.data

class DataSourceException(
    var code: Int,
    message: String,
) : Exception(message)