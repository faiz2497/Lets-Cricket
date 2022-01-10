package faiz.example1.cricketapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;

public class SignUp extends AppCompatActivity {

    EditText username;
    EditText password;
    Button signupButton;
    TextView loginButton;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        firebaseAuth=FirebaseAuth.getInstance();
        username=findViewById(R.id.username);
        password=findViewById(R.id.password);
        loginButton=findViewById(R.id.login);
        signupButton=findViewById(R.id.button);
    }

    public void loginFunction(View view)
    {
        Intent i=new Intent(this,MainActivity.class);
        startActivity(i);
    }

    public void signupClick(View view)
    {
        String email=username.getText().toString();
        String pswd=password.getText().toString();
        if(email.isEmpty())
        {
            Toast.makeText(this, "Please enter email", Toast.LENGTH_SHORT).show();
        }
        else if(pswd.isEmpty())
        {
            Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show();
        }
        else if(!(email.isEmpty() && pswd.isEmpty())) {
            firebaseAuth.createUserWithEmailAndPassword(email, pswd).addOnCompleteListener(SignUp.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(!task.isSuccessful())
                    {
                        FirebaseAuthException e = (FirebaseAuthException)task.getException();
                        Toast.makeText(SignUp.this, "Failed Registration: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        return;
                    }
                    else {
                        firebaseAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(SignUp.this, "Email verification sent, please follow the link sent on your Email", Toast.LENGTH_SHORT).show();
                                    finish();
                                } else {
                                    Toast.makeText(SignUp.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                }
            });
        }
        else
        {
            Toast.makeText(this, "Please enter email and password", Toast.LENGTH_SHORT).show();
        }
    }

   /* protected void onResume()
    {
        super.onResume();
    }*/
}
