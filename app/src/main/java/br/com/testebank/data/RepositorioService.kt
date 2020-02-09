package br.com.testebank.data

import br.com.testebank.model.ListaRepositorio
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers

interface RepositorioService {

    @Headers("Content-Type: application/json")
    @GET("search/repositories?q=language:kotlin&sort=stars")
    fun listarAgenda(
//        @Path("page") page: Long
        ): Call<ListaRepositorio>
}