package kg.kench.pixabay

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PixaApi {

    @GET("api/")
    fun searchImage(
        @Query("q") searchWord: String,
        @Query("key") key: String = "25007027-7418deb977c638792f4bfb99f",
        @Query("per-page") perPage: Int = 3,
        @Query("page") page: Int
    ): Call<PixaModel>
}