package com.kkt1019.hospitalinmyhand

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.kkt1019.hospitalinmyhand.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    val binding:ActivityLoginBinding by lazy { ActivityLoginBinding.inflate(layoutInflater) }

    var auth : FirebaseAuth ? = null
    var googleSignInClient : GoogleSignInClient ? = null
    var GOOGLE_LOGIN_CODE = 9001

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.gest.setOnClickListener { gest() }

        binding.signInButton.setOnClickListener { googleLogin() }

        auth = FirebaseAuth.getInstance()

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN) //기본 로그인 방식 사용
            .requestIdToken("1084194424820-55a06q11ov703tpimbh70meunjh5epmd.apps.googleusercontent.com")
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)


        //구글 로그인 액티비티를 실행하는 Intnent 객체 얻어오기
        val intent = GoogleSignIn.getClient(this, gso).signInIntent

        resultLauncher.launch(intent)


        setContentView(binding.root)
    }

    var resultLauncher = registerForActivityResult(
        StartActivityForResult()
    ) { result -> //로그인 결과를 가져온 Intent 객체 소환
        val intent = result.data
        //Intent로 부터 구글 계정 정보를 가져오는 작업 객체 생성
        val task = GoogleSignIn.getSignedInAccountFromIntent(intent)
        val account = task.result
        val email = account.email

        if (email != null) {
            G.nickname = email
        }

        val profile = account.photoUrl
        if (profile != null){
            G.profileUrl = profile.toString()
        }

    }

//    public override fun onStart() {
//        super.onStart()
//        val account = GoogleSignIn.getLastSignedInAccount(this)
//        if(account!==null){ // 이미 로그인 되어있을시 바로 메인 액티비티로 이동
//            val intent = Intent(this, MainActivity::class.java)
//            Toast.makeText(this, "환영합니다" + G.nickname + "님", Toast.LENGTH_SHORT).show()
//            startActivity(intent)
//        }
//    } //onStart End

    fun gest(){

        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)

    }

    //*********************구글로그인*******************8*
    fun googleLogin() {
        var signInIntent = googleSignInClient?.signInIntent
        startActivityForResult(signInIntent, GOOGLE_LOGIN_CODE)

    } // googleLogin

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == GOOGLE_LOGIN_CODE) {
            var result = Auth.GoogleSignInApi.getSignInResultFromIntent(data!!)
                if(result?.isSuccess == true) {
                    Toast.makeText(this, "로그인 성공", Toast.LENGTH_SHORT).show()
                    startActivity(Intent (this, MainActivity::class.java))
                }else{
                    Toast.makeText(this, "로그인 실패", Toast.LENGTH_SHORT).show()
                }

        } //if
    } // onActivityResult
    //**********************구글로그인 끝**************************

}