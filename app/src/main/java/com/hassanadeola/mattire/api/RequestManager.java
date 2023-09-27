package com.hassanadeola.mattire.api;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.hassanadeola.mattire.listeners.OnFetchDataListener;
import com.hassanadeola.mattire.models.Products;
import com.hassanadeola.mattire.utils.Section;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class RequestManager {

    private final String BASE_URL = "http://10.0.0.151:8080/api/";

    Context context;

    Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
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
    }

    public void getProductLists(OnFetchDataListener listener, int page, int limit, Section section) {
        CallProductApi callProductApi = retrofit.create(CallProductApi.class);
        Call<List<Products>> call = callProductApi.getProducts(page, limit);

        try {
            call.enqueue(new Callback<List<Products>>() {
                @Override
                public void onResponse(Call<List<Products>> call, Response<List<Products>> response) {
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
                public void onFailure(Call<List<Products>> call, Throwable t) {
                    Log.d(TAG, "onFailure: " + t);
                    listener.onError("Request failed");
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
