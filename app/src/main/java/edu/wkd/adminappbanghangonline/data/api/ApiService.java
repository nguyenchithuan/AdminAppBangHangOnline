package edu.wkd.adminappbanghangonline.data.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import edu.wkd.adminappbanghangonline.model.response.ProductResponse;
import edu.wkd.adminappbanghangonline.model.response.RevenueResponse;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

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
}
