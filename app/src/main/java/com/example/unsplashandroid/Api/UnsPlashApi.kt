import com.example.unsplashandroid.modal.CategoryModal
import com.example.unsplashandroid.modal.UnPlashResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface UnsPlashApi {

    @GET("photos")
    suspend fun getPhotos(
        @Query("client_id") clientId: String,
        @Query("order_by") orderBy: String,
        @Query("per_page") perPage: String
    ): Response<List<UnPlashResponse>>

    @GET("topics")
    suspend fun getTopic(
        @Query("client_id") clientId: String,
        @Query("per_page") perPage: String
    ): Response<List<CategoryModal>>
}
