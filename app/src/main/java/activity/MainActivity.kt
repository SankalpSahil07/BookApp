package activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.sankalp.bookapp.R

class MainActivity : AppCompatActivity() {

    lateinit var input_mobile_No : TextInputEditText
    lateinit var input_Password : TextInputEditText
    lateinit var btn_login : Button
    /*val validMobileNo = "0123456789"
    val validPassword = "sahil"*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        input_mobile_No = findViewById(R.id.txtInput_mobile_No)
        input_Password = findViewById(R.id.txtInput_password)
        btn_login = findViewById(R.id.button_login)

        val mobileNumber = input_mobile_No.text.toString()
        val userPassword = input_Password.text.toString()

        btn_login.setOnClickListener{
           /* if ((mobileNumber == validMobileNo) && (userPassword == validPassword)){*/

                val intent = Intent(this@MainActivity, NavigationDemoPage::class.java)
                startActivity(intent)
                Toast.makeText(this@MainActivity,"Login Successful",Toast.LENGTH_LONG).show()
            /*}else{
                Toast.makeText(this@MainActivity,"Invalid Credential",Toast.LENGTH_LONG).show()
            }*/
        }
    }
}