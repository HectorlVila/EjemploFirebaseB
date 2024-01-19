package hector.lagunas.ejemplofirebaseb;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.Firebase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import hector.lagunas.ejemplofirebaseb.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private DatabaseReference myPersona;
    private ArrayList<Persona> listaPersonas;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        database = FirebaseDatabase.getInstance("https://ejemplofirebaseb-default-rtdb.europe-west1.firebasedatabase.app/");
        myRef = database.getReference("frase");
        myPersona = database.getReference("persona");

        binding.btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myRef.setValue(binding.txtFrase.getText().toString());
                int edad = (int)(Math.random()*100);
                Persona p = new Persona(binding.txtFrase.getText().toString(), edad);
                myPersona.setValue(p);
            }
        });




        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String texto = snapshot.getValue(String.class);
                binding.lbFrase.setText(texto);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this, error.toException().toString(), Toast.LENGTH_SHORT).show();
            }
        });

        myPersona.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Persona p = snapshot.getValue(Persona.class);
                binding.lbFrase.setText(p.toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this, error.toException().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}