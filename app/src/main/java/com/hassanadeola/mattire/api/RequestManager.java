package com.hassanadeola.mattire.api;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.hassanadeola.mattire.listeners.OnFetchProductListener;
import com.hassanadeola.mattire.models.CartItem;
import com.hassanadeola.mattire.models.Products;
import com.hassanadeola.mattire.utils.Section;
import com.hassanadeola.mattire.utils.Utils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public class RequestManager {

    private final String BASE_URL = "http://10.0.0.151:8080/api/";

    Context context;

  /*  Gson gson = new GsonBuilder()
            .setLenient()
            .create();*/

    Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory
                    .create()).build();

    public RequestManager(Context context) {
        this.context = context;
    }

    public interface CallProductApi {
        @GET("products")
        Call<List<Products>> getProducts(
                @Query("page") int page,
                @Query("limit") int limit
        );

        @GET("orders/user/products")
        Call<List<CartItem>> getCartItems(
                @Query("userId") String userId
        );

        //  @Headers("Content-Type: application/json")
        @FormUrlEncoded
        @POST("orders/add")
        Call<String> addToCart(
                @Field("userId") String userId,
                @Field("productId") String productId);
    }

    public void addToCart(String userId, String productId) {
        CallProductApi callProductApi = retrofit.create(CallProductApi.class);
        Call<String> call = callProductApi.addToCart(userId, productId);


        try {
            call.enqueue(new Callback<String>() {

                @Override
                public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                    if (!response.isSuccessful()) {
                        Toast.makeText(context, "Items not added to cart. Try again later!", Toast.LENGTH_SHORT).show();
                    }
                    if (response.body() != null) {
                        Toast.makeText(context, response.body(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                    Log.e(TAG, t.getMessage());
                    Toast.makeText(context, "Items not added to cart. Try again later", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getCartItemList(String userId) {
        CallProductApi callProductApi = retrofit.create(CallProductApi.class);
        Call<List<CartItem>> call = callProductApi.getCartItems(userId);

        try {
            call.enqueue(new Callback<List<CartItem>>() {

                @Override
                public void onResponse(@NonNull Call<List<CartItem>> call, @NonNull Response<List<CartItem>> response) {
                    if (!response.isSuccessful()) {
                        Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                    }
                    if (response.body() != null) {
                        Utils.setSharedPreferences(context, "CART_ITEMS", Utils.createStringJson(response.body()));
                    }
                }

                @Override
                public void onFailure(@NonNull Call<List<CartItem>> call, @NonNull Throwable t) {
                    Toast.makeText(context, "Error getting CartRequest Items", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void getProductLists(OnFetchProductListener<Products> listener, int page, int limit, Section section) {
        CallProductApi callProductApi = retrofit.create(CallProductApi.class);
        Call<List<Products>> call = callProductApi.getProducts(page, limit);

        try {
            call.enqueue(new Callback<List<Products>>() {
                @Override
                public void onResponse(@NonNull Call<List<Products>> call, @NonNull Response<List<Products>> response) {
                    if (!response.isSuccessful()) {
                        Log.d(TAG, String.valueOf(response));
                        Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                    }
                    if (response.body() != null) {
                        //    List<Products> products = response.body().toArray();
                        listener.onFetchData(response.body(), response.message(), section);
                    }
                }

                @Override
                public void onFailure(@NonNull Call<List<Products>> call, @NonNull Throwable t) {
                    listener.onError("Request failed");
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
