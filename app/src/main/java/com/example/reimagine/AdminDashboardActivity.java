package com.example.reimagine;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.graphics.Rect;
import android.media.Image;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.Camera;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageProxy;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import android.Manifest;
import android.util.Log;
import android.util.Size;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.mlkit.vision.barcode.BarcodeScanner;
import com.google.mlkit.vision.barcode.BarcodeScannerOptions;
import com.google.mlkit.vision.barcode.BarcodeScanning;
import com.google.mlkit.vision.barcode.common.Barcode;
import com.google.mlkit.vision.common.InputImage;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AdminDashboardActivity extends AppCompatActivity {

    private ListenableFuture cameraProviderFuture;
    private ExecutorService cameraExecutor;
    private PreviewView previewView;
    private MyImagineAnalyzer analyzer;
    private boolean barcodeProcessed =false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);
        previewView = findViewById(R.id.previewView);
        this.getWindow().setFlags(1024,1024);

        //background job
        cameraExecutor = Executors.newSingleThreadExecutor();
        cameraProviderFuture = ProcessCameraProvider.getInstance(this);
        analyzer = new MyImagineAnalyzer(getSupportFragmentManager());

        cameraProviderFuture.addListener(new Runnable() {
            @Override
            public void run() {
                try {
                    if(ActivityCompat.checkSelfPermission(AdminDashboardActivity.this,
                            Manifest.permission.CAMERA) != (PackageManager.PERMISSION_GRANTED)){
                        ActivityCompat.requestPermissions(AdminDashboardActivity.this,new String[]{Manifest.permission.CAMERA},101);

                    }else{
                        ProcessCameraProvider processCameraProvider = (ProcessCameraProvider) cameraProviderFuture.get();
                        bindpreview(processCameraProvider);
                    }
                }catch(ExecutionException e){
                    e.printStackTrace();
                }catch(InterruptedException e){
                    e.printStackTrace();

                }
            }
        }, ContextCompat.getMainExecutor(this));

    }
    // Requesting permissions from the user

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == 101 && grantResults.length >0){
            ProcessCameraProvider processCameraProvider = null;
            try{
                processCameraProvider =(ProcessCameraProvider) cameraProviderFuture.get();
            }catch(ExecutionException e){
                e.printStackTrace();
            }catch(InterruptedException e){
                e.printStackTrace();
            }
            bindpreview(processCameraProvider);
        }
    }

    private void bindpreview(ProcessCameraProvider processCameraProvider) {
        Preview preview = new Preview.Builder().build();
        CameraSelector cameraSelector = new CameraSelector.Builder().requireLensFacing(
                CameraSelector.LENS_FACING_BACK).build();
        preview.setSurfaceProvider(previewView.getSurfaceProvider());
        ImageCapture imageCapture = new ImageCapture.Builder().build();
        ImageAnalysis imageAnalysis = new ImageAnalysis.Builder()
                .setTargetResolution(new Size(1280,720))
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST).build();

        imageAnalysis.setAnalyzer(cameraExecutor,analyzer);
        processCameraProvider.unbindAll();
        processCameraProvider.bindToLifecycle(this,cameraSelector,preview,imageCapture,imageAnalysis);

    }

    public class MyImagineAnalyzer implements  ImageAnalysis.Analyzer{
        private FragmentManager fragmentManager;
        //private BottomDialog bottomDialog;
        private boolean barcodeProcessed = false;
        public MyImagineAnalyzer(FragmentManager supportFragmentManager) {
            this.fragmentManager = supportFragmentManager;
            //bottomDialog = new BottomDialog();
        }

        @Override
        public void analyze(@NonNull ImageProxy image) {
            if (!barcodeProcessed) {
                ScanBarCode(image);
            }
        }

        private void ScanBarCode(ImageProxy image) {
            @SuppressLint("UnsafeOptInUsageError") Image image1 = image.getImage();

            assert  image1 != null;
            InputImage inputImage = InputImage.fromMediaImage(image1,image.getImageInfo().getRotationDegrees());
            BarcodeScannerOptions scannerOptions = new BarcodeScannerOptions.Builder()
                    .setBarcodeFormats(Barcode.FORMAT_QR_CODE,Barcode.FORMAT_AZTEC).build();

            BarcodeScanner scanner = BarcodeScanning.getClient(scannerOptions);
            Task<List<Barcode>> result = scanner.process(inputImage)
                    .addOnSuccessListener(new OnSuccessListener<List<Barcode>>() {
                        @Override
                        public void onSuccess(List<Barcode> barcodes) {
                            ReaderBarCodeData(barcodes);
                            image.close();

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            //Failed to read the Qr code
                            Toast.makeText(AdminDashboardActivity.this, "Failed to Read Code", Toast.LENGTH_SHORT).show();
                            image.close();
                        }
                    }).addOnCompleteListener(new OnCompleteListener<List<Barcode>>() {
                        @Override
                        public void onComplete(@NonNull Task<List<Barcode>> task) {
                            image.close();
                        }
                    });
        }
        private void ReaderBarCodeData(List<Barcode> barcodes) {
            for (Barcode barcode : barcodes) {
                if (!barcodeProcessed) { // Process only if barcode has not been processed yet
                    Rect bounds = barcode.getBoundingBox();
                    Point[] corners = barcode.getCornerPoints();
                    String rawValues = barcode.getRawValue();
                    Log.i("TAG", rawValues);

                    BarcodeResultHandler.handleBarcodeResult(AdminDashboardActivity.this, rawValues);
                    barcodeProcessed = true; // Set flag to true after processing the barcode
                    break;// Exit the loop after processing the first barcode
                }
            }

        }

    };
    @Override
    protected void onResume() {
        super.onResume();
        barcodeProcessed = false; // Reset the flag when the activity is resumed
        recreateAnalyzer(); // Recreate the MyImagineAnalyzer instance
    }

    private void recreateAnalyzer() {
        analyzer = new MyImagineAnalyzer(getSupportFragmentManager());
        // Re-bind the analyzer to the ImageAnalysis use case
        if (cameraProviderFuture != null && cameraProviderFuture.isDone()) {
            try {
                ProcessCameraProvider processCameraProvider = (ProcessCameraProvider) cameraProviderFuture.get();
                bindpreview(processCameraProvider);
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
