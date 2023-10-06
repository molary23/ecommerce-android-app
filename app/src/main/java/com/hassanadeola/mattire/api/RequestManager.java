package com.hassanadeola.mattire.api;

import static android.content.ContentValues.TAG;

import static com.hassanadeola.mattire.utils.Utils.navigateToView;
import static com.hassanadeola.mattire.utils.Utils.removeSharedPreferences;
import static com.hassanadeola.mattire.utils.Utils.setSharedPreferences;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.hassanadeola.mattire.listeners.OnFetchProductListener;
import com.hassanadeola.mattire.listeners.PaymentListener;
import com.hassanadeola.mattire.models.CartItem;
import com.hassanadeola.mattire.models.Products;
import com.hassanadeola.mattire.utils.Section;
import com.hassanadeola.mattire.utils.Utils;
import com.hassanadeola.mattire.viewmodels.ConfirmationActivity;

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
import retrofit2.http.HTTP;
import retrofit2.http.POST;
import retrofit2.http.Query;

public class RequestManager {

    private final String BASE_URL = "https://ecommerce-spring.orangerock-dbb80cfc.eastus.azurecontainerapps.io/api/";

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

        @GET("products/search")
        Call<List<Products>> searchProduct(
                @Query("search") String search
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

        @FormUrlEncoded
        @HTTP(method = "DELETE", path = "/orders/remove", hasBody = true)
        Call<String> reduceProductInCart(
                @Field("userId") String userId,
                @Field("productId") String productId);

        @FormUrlEncoded
        @HTTP(method = "DELETE", path = "orders/delete/product", hasBody = true)
        Call<String> removeProductFromCart(
                @Field("userId") String userId,
                @Field("productId") String productId);

        @FormUrlEncoded
        @HTTP(method = "DELETE", path = "orders/delete", hasBody = true)
        Call<String> clearCart(
                @Field("userId") String userId);

        @FormUrlEncoded
        @POST("pay")
        Call<Boolean> pay(
                @Field("userId") String userId,
                @Field("amount") double amount,
                @Field("lastFour") String lastFour);
    }

    public void makePayment(PaymentListener listener, String userId, double amount, String lastFour) {
        CallProductApi callProductApi = retrofit.create(CallProductApi.class);
        Call<Boolean> call = callProductApi.pay(userId, amount, lastFour);

        try {
            call.enqueue(new Callback<Boolean>() {

                @Override
                public void onResponse(@NonNull Call<Boolean> call, @NonNull Response<Boolean> response) {
                    if (!response.isSuccessful()) {
                        Toast.makeText(context, "Unable to process your payment!", Toast.LENGTH_SHORT).show();
                    }
                    if (Boolean.TRUE.equals(response.body())) {
                        listener.onMakePayment(true);
                    } else {
                        Toast.makeText(context, "Payment unsuccessful. Try again later!", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<Boolean> call, @NonNull Throwable t) {
                    Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                    listener.onError(t.getMessage());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void reduceProductInCart(String userId, String productId) {
        CallProductApi callProductApi = retrofit.create(CallProductApi.class);
        Call<String> call = callProductApi.removeProductFromCart(userId, productId);

        try {
            call.enqueue(new Callback<String>() {

                @Override
                public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                    if (!response.isSuccessful()) {
                        Toast.makeText(context, "Items not reduce in cart. Try again later!", Toast.LENGTH_SHORT).show();
                    }
                    if (response.body() != null) {
                        Toast.makeText(context, response.body(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                    Toast.makeText(context, "Items not reduce in cart. Try again later", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void removeProductFromCart(String userId, String productId) {
        CallProductApi callProductApi = retrofit.create(CallProductApi.class);
        Call<String> call = callProductApi.removeProductFromCart(userId, productId);

        try {
            call.enqueue(new Callback<String>() {

                @Override
                public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                    Log.d(TAG, response.toString());
                    if (!response.isSuccessful()) {
                        Toast.makeText(context, "Items not removed from cart. Try again later!", Toast.LENGTH_SHORT).show();
                    }
                    if (response.body() != null) {
                        Toast.makeText(context, response.body(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                    Toast.makeText(context, "Items not removed from cart. Try again later", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void clearCart(String userId) {
        CallProductApi callProductApi = retrofit.create(CallProductApi.class);
        Call<String> call = callProductApi.clearCart(userId);


        try {
            call.enqueue(new Callback<String>() {

                @Override
                public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                    if (!response.isSuccessful()) {
                        Toast.makeText(context, "Cart not Cleared. Try again later!", Toast.LENGTH_SHORT).show();
                    }
                    if (response.body() != null) {
                        Toast.makeText(context, response.body(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                    Toast.makeText(context, "Cart not Cleared. Try again later", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
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
                        setSharedPreferences(context, "CART_ITEMS", Utils.createStringJson(response.body()));
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

    public void getProductLists(OnFetchProductListener<Products> listener, int page, int limit, String search, Section section) {
        CallProductApi callProductApi = retrofit.create(CallProductApi.class);
        Call<List<Products>> call;
        if (section == Section.SEARCH) {
            call = callProductApi.searchProduct(search);
        } else {
            call = callProductApi.getProducts(page, limit);
        }


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
