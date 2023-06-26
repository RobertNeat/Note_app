package com.example.note_app;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Date;

public class NoteEditorActivity extends AppCompatActivity {


    Button CancelBTN;
    Button SaveBTN;


    EditText note_title_EditText;
    EditText note_description_EditText;


    //elementy formularza
//    EditText ManufacturerET;
//    EditText ModelET;
//    EditText AndroidVersionET;
//    EditText WebSiteET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_editor);

        //przypisanie wartości z widoku do zmiennych (formularz)
        note_title_EditText = findViewById(R.id.note_title_EditText);
        note_description_EditText = findViewById(R.id.note_description_EditText);

        //załadowanie wartości z intencji jeżeli to jest edycja
        Bundle bundle = getIntent().getExtras();
        if( bundle!=null && !bundle.isEmpty()) {//jeżeli nie jest puste to pola ustawić na wartości z extras
            note_title_EditText.setText(bundle.getString("title"));
            note_description_EditText.setText(bundle.getString("description"));
        }

        //obsługa anulowania aktualnego widoku na podstawie "parentActivityName" ustawionego w manifeście dla aktualnego widoku
        CancelBTN = findViewById(R.id.CancelBTN);
        CancelBTN.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                finish();
            }
        });



        //obsługa zapisania aktualnego formularza jako rekordu do bazy danych
        SaveBTN = findViewById(R.id.SaveBTN);
        SaveBTN.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //zczytanie wartości z formularza
                String title = note_title_EditText.getText().toString();
                String description = note_description_EditText.getText().toString();



                //room database - jest abstrakcyjnym singletonem (więc raczej sobie tego nie poużywam)
                //repozytorium - jest źródłem danych dla aplikacji

                try {

                    ElementRepository elementRepository = new ElementRepository(getApplication());//albo spróbować "new application"

                    Element element = new Element(title, new Date(), description);


                    if(bundle!=null && !bundle.isEmpty() ) {//obsługa aktualizacji danych jeżeli intencja nie jest pusta
                        long parsed_id = Long.parseLong(bundle.getString("id"));
                        Element element_update = new Element(parsed_id,title, new Date(), description);//customowy inicjalizator, żeby można było aktualizować element na podstawie id
                        elementRepository.update(element_update);

                    }else {//dane były wpisane przez użytkownika więc dodajemy nową pozycję
                        elementRepository.insert(element);
                    }
                    finish(); //powrót do pierwszej aktywności
                }catch(NumberFormatException e){
                    Toast.makeText(NoteEditorActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        });


        //walidacja nazwy
        note_title_EditText.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus && note_title_EditText.getText().toString().trim().length() == 0) {
                    note_title_EditText.setError("Title can't be empty");
                    Toast.makeText(NoteEditorActivity.this,"Title can't be empty",Toast.LENGTH_SHORT).show();
                }
            }
        });

        //walidacja opisu
        note_description_EditText.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus && note_description_EditText.getText().toString().trim().length() == 0) {
                    note_description_EditText.setError("Description can't be empty");
                    Toast.makeText(NoteEditorActivity.this,"Description can't be empty",Toast.LENGTH_SHORT).show();
                }
            }
        });




    }
}