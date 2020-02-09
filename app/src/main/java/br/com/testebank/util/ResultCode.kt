package br.com.testebank.util

enum class ResultCode(val value: Int) {
    SUCESS(200),
    UNAUTHORIZED(503),
    BADREQUEST(400),
    NOCONTENT(204),
    INTERNALSERVER(500),
    CONFLICT(409),
    NOTFOUND(404),
}