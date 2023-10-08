package edu.wkd.adminappbanghangonline.data.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import edu.wkd.adminappbanghangonline.model.response.ProductResponse;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

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
}
