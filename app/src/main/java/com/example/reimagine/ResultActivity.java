package com.example.reimagine;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ResultActivity extends AppCompatActivity {
    String userId;
    private TableLayout tableLayout;
    private FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(BarcodeResultHandler.BARCODE_DATA_EXTRA)) {
             userId = intent.getStringExtra(BarcodeResultHandler.BARCODE_DATA_EXTRA);
        }
        tableLayout = findViewById(R.id.tableLayout);
        db = FirebaseFirestore.getInstance();
        fetchBarcodeData();
    }
    private void fetchBarcodeData() {
        db.collection(userId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            // List to store document names and corresponding field values
                            List<DocumentData> documentDataList = new ArrayList<>();

                            for (DocumentSnapshot document : task.getResult()) {
                                // Get document name (ID)
                                String documentName = document.getId();

                                // Get specific field value from the document (change "name" to your desired field name)
                                String fieldValue = document.getString("name");
                                boolean isEntered = document.getBoolean("isEntered");

                                // Create DocumentData object with document name and field value
                                DocumentData documentData = new DocumentData(documentName, fieldValue,isEntered);

                                // Add DocumentData object to the list
                                documentDataList.add(documentData);
                            }

                            // Populate the table with document data
                            populateTable(documentDataList);
                        } else {
                            // Handle errors
                        }
                    }
                });
    }
    private void populateTable(List<DocumentData> documentDataList) {
        // Create table header dynamically
        TableRow headerRow = new TableRow(this);
        TableRow.LayoutParams headerLayoutParams = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
        headerRow.setLayoutParams(headerLayoutParams);

        // Add column headers
        TextView isEnteredHeaderTextView = new TextView(this);
        isEnteredHeaderTextView.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1));
        isEnteredHeaderTextView.setGravity(Gravity.CENTER);
        isEnteredHeaderTextView.setText("isEntered");
        headerRow.addView(isEnteredHeaderTextView);

        TextView teamNamesHeaderTextView = new TextView(this);
        teamNamesHeaderTextView.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1));
        teamNamesHeaderTextView.setGravity(Gravity.CENTER);
        teamNamesHeaderTextView.setText("Team Names");
        headerRow.addView(teamNamesHeaderTextView);

        // Add header row to the table layout
        tableLayout.addView(headerRow);

        // Create table rows for document data
        for (DocumentData documentData : documentDataList) {
            TableRow row = new TableRow(this);
            TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
            row.setLayoutParams(layoutParams);

            // Add isEntered status to the first column (as CheckBox)
            CheckBox checkBox = new CheckBox(this);
            checkBox.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1));
            checkBox.setGravity(Gravity.CENTER);
            checkBox.setChecked(documentData.isEntered());
            row.addView(checkBox);
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    // Get the document name associated with this row
                    String documentName = documentData.getDocumentName();

                    // Update the Firestore document's 'isEntered' field based on the CheckBox state
                    updateIsEnteredField(documentName, isChecked);
                }
            });

            // Add Team Names to the second column (as TextView)
            TextView fieldValueTextView = new TextView(this);
            fieldValueTextView.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1));
            fieldValueTextView.setGravity(Gravity.CENTER);
            fieldValueTextView.setText(documentData.getFieldValue());
            row.addView(fieldValueTextView);

            // Add row to the table layout
            tableLayout.addView(row);

        }
    }

    private void updateIsEnteredField(String documentName, boolean isChecked) {
        db.collection(userId).document(documentName)
                .update("isEntered", isChecked)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Update successful
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Handle errors
                    }
                });
    }


}
class DocumentData {
    private String documentName;
    private String fieldValue;
    private boolean isEntered;

    public DocumentData(String documentName, String fieldValue,boolean isEntered) {
        this.documentName = documentName;
        this.fieldValue = fieldValue;
        this.isEntered = isEntered;
    }

    public String getDocumentName() {
        return documentName;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    public void setFieldValue(String fieldValue) {
        this.fieldValue = fieldValue;
    }

    public boolean isEntered() {
        return isEntered;
    }

    public void setEntered(boolean entered) {
        isEntered = entered;
    }

    public String getFieldValue() {
        return fieldValue;
    }
}