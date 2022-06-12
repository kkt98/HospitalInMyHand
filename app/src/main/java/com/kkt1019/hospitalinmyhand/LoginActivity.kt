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
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient
import com.kakao.util.maps.helper.Utility
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

        binding.kakaoLogin.setOnClickListener { kakaoLogin() }

        auth = FirebaseAuth.getInstance()

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN) //기본 로그인 방식 사용
            .requestIdToken("1084194424820-55a06q11ov703tpimbh70meunjh5epmd.apps.googleusercontent.com")
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)


        //구글 로그인 액티비티를 실행하는 Intnent 객체 얻어오기
        val intent = GoogleSignIn.getClient(this, gso).signInIntent

        resultLauncher.launch(intent)

        //카카오 로그인 키해시값 얻어오기
        val keyHash = Utility.getKeyHash(this)
        Log.i("keyHash", keyHash)

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

    fun kakaoLogin(){

        //카카오 로그인 성공했을때 반응하는 callback 객체 생성
        val callback : (OAuthToken?, Throwable?)->Unit = { token, error ->
            if (error != null) {
                Toast.makeText(this, "카카오 로그인 실패😥", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this, "카카오 로그인 성공😊", Toast.LENGTH_SHORT).show()

                //사용자의 정보 요청
                UserApiClient.instance.me { user, error ->
                    if (user != null){
                        var nickname:String = user.kakaoAccount?.profile?.nickname.toString()
//                        var email:String = user.kakaoAccount?.email ?:"" //앨비스 연산자
                        var profile = user.kakaoAccount?.profile?.thumbnailImageUrl

                        G.profileUrl = profile
                        G.nickname = nickname
//                        G.nickname = email

                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    }
                }
            }
        }

        //카카오톡이 설치되어 있으면 카카오톡로그인, 없으면 카카오계정 로그인
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(this)){
            UserApiClient.instance.loginWithKakaoTalk(this, callback = callback)
        }else{
            UserApiClient.instance.loginWithKakaoAccount(this, callback = callback)
        }

    }

}