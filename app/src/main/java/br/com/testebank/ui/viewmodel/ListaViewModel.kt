package br.com.testebank.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import br.com.testebank.data.RepositorioService
import br.com.testebank.data.ServiceRepositorio
import br.com.testebank.model.ListaRepositorio
import br.com.testebank.model.ListaRepositorioResposta
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListaViewModel(application: Application) : AndroidViewModel(application) {

    private lateinit var mutableLiveLista: MutableLiveData<ListaRepositorioResposta<ListaRepositorio>>

    fun getListaObservable(): MutableLiveData<ListaRepositorioResposta<ListaRepositorio>> {
        if (!::mutableLiveLista.isInitialized) {
            mutableLiveLista = MutableLiveData()
        }
        return mutableLiveLista
    }

    fun getDados(page: Long) {
        val agendaService = ServiceRepositorio.createService(RepositorioService::class.java)
        val call = agendaService.listarAgenda()

        call.enqueue(object : Callback<ListaRepositorio> {
            override fun onResponse(
                call: Call<ListaRepositorio>,
                response: Response<ListaRepositorio>
            ) {
                try {
                    if (response.isSuccessful) {
                        val agendaResposta  = ListaRepositorioResposta<ListaRepositorio>()
                        agendaResposta.data = response.body()
                        agendaResposta.message = response.message()
                        agendaResposta.resultCode = response.code()

                        mutableLiveLista.value = agendaResposta
                    } else {
                        val agendaResponse: ListaRepositorioResposta<ListaRepositorio> =
                            ListaRepositorioResposta(
                                null,
                                response.code(),
                                ""
                            )

                        mutableLiveLista.value = agendaResponse
                    }
                } catch (e: Exception) {
                    val agendaResponse: ListaRepositorioResposta<ListaRepositorio> =
                        ListaRepositorioResposta(
                            null,
                            0,
                            ""
                        )

                    mutableLiveLista.value = agendaResponse
                }
            }

            override fun onFailure(call: Call<ListaRepositorio>, t: Throwable) {
                val agendaResponse: ListaRepositorioResposta<ListaRepositorio> =
                    ListaRepositorioResposta(
                        null, 503, ""
                    )

                mutableLiveLista.value = agendaResponse
            }
        })
    }
}