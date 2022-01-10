package faiz.example1.cricketapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;

public class MainActivity extends AppCompatActivity {

    EditText username;
    EditText password;
    Button loginButton;
    TextView signupButton;
    FirebaseAuth firebaseAuth;
    TextView forgotPassword;
    private FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        username=findViewById(R.id.username);
        password=findViewById(R.id.password);
        loginButton=findViewById(R.id.button);
        signupButton=findViewById(R.id.signup);
        forgotPassword=findViewById(R.id.forgotPassword);

        firebaseAuth=FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser()!=null && firebaseAuth.getCurrentUser().isEmailVerified())
        {
            startActivity(new Intent(MainActivity.this,HomeActivity.class));
            finish();
        }

    }

    public void signUpFunction(View view)
    {
        username.setText(null);
        password.setText(null);
        Intent i=new Intent(this,SignUp.class);
        startActivity(i);
    }

    public void loginClick(View view)
    {
        String email=username.getText().toString();
        String pswd=password.getText().toString();

        if(!email.isEmpty() && !pswd.isEmpty()) {
            firebaseAuth.signInWithEmailAndPassword(email,pswd).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful())
                    {
                        if(firebaseAuth.getCurrentUser().isEmailVerified())
                        {
                            Intent i=new Intent(MainActivity.this,HomeActivity.class);
                            startActivity(i);
                            finish();
                        }
                        else
                        {
                            Toast.makeText(MainActivity.this, "Verify your email with the link sent to you", Toast.LENGTH_SHORT).show();
                        }

                    }
                    else
                    {
                        FirebaseAuthException e = (FirebaseAuthException)task.getException();
                        Toast.makeText(MainActivity.this, "Failed Registration: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        else if(email.isEmpty() && pswd.isEmpty())
        {
            Toast.makeText(this, "Please enter email and password", Toast.LENGTH_SHORT).show();
        }
        else if(pswd.isEmpty())
        {
            Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show();
        }
        else if(email.isEmpty())
        {
            Toast.makeText(this, "Please enter email", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(this, "Please enter email and password", Toast.LENGTH_SHORT).show();
        }
    }

    public void forgotPswd(View view)
    {
        Intent i=new Intent(MainActivity.this,ResetPasswordActivity.class);
        startActivity(i);
    }

}
