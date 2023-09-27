package com.hassanadeola.mattire.listeners;

import com.hassanadeola.mattire.models.Products;
import com.hassanadeola.mattire.utils.Section;

import java.util.List;

public interface OnFetchDataListener<Products> {

    void onFetchData(List<Products> products, String message, Section section);
    void onError(String message);
}
