package com.example.reimagine;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.pdf.PdfRenderer;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.ParcelFileDescriptor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AboutHackathonFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AboutHackathonFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private ImageView imageView;
    private PdfRenderer pdfRenderer;
    private ImageView btnPreviousPage;
    private ImageView btnNextPage;
    private int currentPage = 0;
    private static final String PDF_FILE_NAME = "REIMAGINE.pdf";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AboutHackathonFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AboutHackathonFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AboutHackathonFragment newInstance(String param1, String param2) {
        AboutHackathonFragment fragment = new AboutHackathonFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    public static AboutHackathonFragment newInstance() {
        return new AboutHackathonFragment();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_about_hackathon, container, false);
        imageView = view.findViewById(R.id.imageView);
        btnPreviousPage = view.findViewById(R.id.imgLeftArrow);
        btnNextPage = view.findViewById(R.id.imgRightArrow);
        btnPreviousPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPage(currentPage - 1);
            }
        });
        btnNextPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPage(currentPage + 1);
            }
        });
        displayPDF();
        return view;
    }
    private void displayPDF() {
        try {
            // Copy PDF from assets folder to internal storage
            File file = copyFileFromAssetsToInternalStorage(PDF_FILE_NAME);
            if (file != null) {
                // Open PDF using ParcelFileDescriptor
                ParcelFileDescriptor parcelFileDescriptor = ParcelFileDescriptor.open(file, ParcelFileDescriptor.MODE_READ_ONLY);

                // Open PDF using PdfRenderer
                pdfRenderer = new PdfRenderer(parcelFileDescriptor);

                // Display first page
                showPage(0);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private File copyFileFromAssetsToInternalStorage(String filename) throws IOException {
        Context context = requireContext();
        File file = new File(context.getFilesDir(), filename);
        if (!file.exists()) {
            InputStream inputStream = context.getAssets().open(filename);
            FileOutputStream outputStream = new FileOutputStream(file);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }
            outputStream.close();
            inputStream.close();
        }
        return file;
    }
    private void showPage(int index) {
        if (index != 7){
        if (pdfRenderer == null || index < 0 || index >= pdfRenderer.getPageCount()) {
            return;
        }

        // Close previous page
        if (imageView.getDrawable() != null) {
            ((BitmapDrawable) imageView.getDrawable()).getBitmap().recycle();
        }

        // Open specified page
        PdfRenderer.Page page = pdfRenderer.openPage(index);

        // Render page to Bitmap
        Bitmap bitmap = Bitmap.createBitmap(page.getWidth(), page.getHeight(), Bitmap.Config.ARGB_8888);
        page.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY);

        // Display Bitmap in ImageView
        imageView.setImageBitmap(bitmap);

        // Close current page
        page.close();
        currentPage = index;
    }}




}