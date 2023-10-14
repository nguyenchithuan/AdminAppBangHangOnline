package edu.wkd.adminappbanghangonline.data.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import edu.wkd.adminappbanghangonline.model.response.OrderResponse;
import edu.wkd.adminappbanghangonline.model.response.ProductResponse;
import edu.wkd.adminappbanghangonline.model.response.ServerResponse;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiService {
    String URL_MAIN = "https://guyinterns2003.000webhostapp.com/";
    Gson gson = new GsonBuilder().setDateFormat("dd-MM-yyyy").create();
    ApiService apiService = new Retrofit.Builder()
            .baseUrl(URL_MAIN)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiService.class);

    @GET("get_product.php")
    Call<ProductResponse> getListProduct();

    @Multipart
    @POST("add_product.php")
    Call<ProductResponse> addProduct(@Part MultipartBody.Part file,
                      @Part("name") String name,
                      @Part("price") int price,
                      @Part("description") String description,
                      @Part("product_type_id") int product_type_id);

    @Multipart
    @POST("update_product.php")
    Call<ProductResponse> updateProduct(@Part MultipartBody.Part file,
                                        @Part("id_product") int id_product,
                                     @Part("name") String name,
                                     @Part("price") int price,
                                     @Part("description") String description,
                                     @Part("product_type_id") int product_type_id,
                                        @Part("image") String image);

    @FormUrlEncoded
    @POST("delete_product.php")
    Call<ServerResponse> deleteProduct(@Field("id_product") int idProduct);

    //Lấy tất cả đơn hàng
    @GET("get_all_orders.php")
    Call<OrderResponse> getAllOrder();

}
