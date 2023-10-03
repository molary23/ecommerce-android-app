package com.hassanadeola.mattire.listeners;

public interface PaymentListener {
    void onMakePayment(Boolean status);
    void onError(String message);
}
