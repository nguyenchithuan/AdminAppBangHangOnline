package edu.wkd.adminappbanghangonline.data.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import edu.wkd.adminappbanghangonline.model.obj.Order;
import edu.wkd.adminappbanghangonline.model.response.OrderResponse;
import edu.wkd.adminappbanghangonline.model.response.ProductResponse;
import edu.wkd.adminappbanghangonline.model.response.RevenueResponse;
import edu.wkd.adminappbanghangonline.model.response.ServerResponse;
import edu.wkd.adminappbanghangonline.model.response.UserResponse;
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

    @GET("get_top_product.php")
    Call<ProductResponse> getTopProduct();

    @FormUrlEncoded
    @POST("get_revenue_day.php")
    Call<RevenueResponse> getRevenueDay(@Field("day_a") String dayA, @Field("day_b") String dayB); @FormUrlEncoded

    @POST("get_revenue_month.php")
    Call<RevenueResponse> getRevenueMonth(@Field("month") String month);

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

    @FormUrlEncoded
    @POST("get_all_orders_by_status.php")
    Call<OrderResponse> getAllOrderByStatus(@Field("status") int status);

    @FormUrlEncoded
    @POST("update_order_status.php")
    Call<Order> updateStatusOrder(@Field("id_order") int idOrder, @Field("status") int status);

    @FormUrlEncoded
    @POST("login.php")
    Call <UserResponse> loginUser(@Field("email") String email, @Field("password") String password);

    @FormUrlEncoded
    @POST("search_product.php")
    Call<ProductResponse> getProductSearch(@Field("product_name") String product_name);

    @FormUrlEncoded
    @POST("update_token_admin.php")
    Call<UserResponse> updateTokenAdmin(@Field("token_admin") String token, @Field("user_id") int userId);
}
