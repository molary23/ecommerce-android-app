package com.hassanadeola.mattire.listeners;

import com.hassanadeola.mattire.utils.Section;

import java.util.List;

public interface OnFetchProductListener<Products> {

    void onFetchData(List<Products> products, String message, Section section);
    void onError(String message);


}
