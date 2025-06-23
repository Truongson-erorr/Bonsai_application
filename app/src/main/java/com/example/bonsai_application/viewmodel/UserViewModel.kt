package com.example.bonsai_application.viewmodel

import androidx.lifecycle.ViewModel
import com.example.bonsai_application.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class RegisterViewModel : ViewModel() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()

    fun registerUser(
        email: String,
        password: String,
        fullName: String,
        phoneNumber: String,
        onSuccess: (String) -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            auth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener { result ->
                    val user = result.user
                    user?.sendEmailVerification()

                    val newUser = User(
                        uid = user?.uid ?: "",
                        email = email,
                        fullName = fullName,
                        phoneNumber = phoneNumber,
                        role = "user"
                    )

                    db.collection("Users")
                        .document(newUser.uid)
                        .set(newUser)
                        .addOnSuccessListener {
                            onSuccess("Đăng ký thành công. Kiểm tra email xác thực.")
                        }
                        .addOnFailureListener {
                            onError("Tạo tài khoản thành công nhưng lưu thông tin thất bại: ${it.message}")
                        }
                }
                .addOnFailureListener {
                    onError("Lỗi đăng ký: ${it.message}")
                }
        }
    }
}
