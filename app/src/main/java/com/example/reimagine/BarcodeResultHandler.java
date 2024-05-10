package com.example.reimagine;

import android.content.Context;
import android.content.Intent;

public class BarcodeResultHandler {

    public static final String BARCODE_DATA_EXTRA = "barcode_data";

    public static void handleBarcodeResult(Context context, String rawValue) {
        Intent intent = new Intent(context, ResultActivity.class);
        intent.putExtra(BARCODE_DATA_EXTRA, rawValue);
        context.startActivity(intent);
    }
}