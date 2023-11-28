package com.example.mobdeve_mco

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.AppCompatButton
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import com.google.firebase.firestore.ktx.firestore
import java.util.Date

class LoginBottomSheetFragment : BottomSheetDialogFragment() {

    private lateinit var btnLogIn : Button
    private lateinit var btnSignUp : Button
    private lateinit var btnClose : ImageButton
    private lateinit var btnGoogle : AppCompatButton

    private lateinit var googleSignInClient : GoogleSignInClient
    private lateinit var auth : FirebaseAuth


    override fun getTheme(): Int {
        return R.style.LoginBottomSheetDialogTheme
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login_bottom_sheet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnLogIn = view.findViewById(R.id.btnLogIn)
        btnSignUp = view.findViewById(R.id.btnSignUp)
        btnSignUp.paintFlags = btnSignUp.paintFlags or Paint.UNDERLINE_TEXT_FLAG

        btnGoogle = view.findViewById(R.id.btnGoogle)
        btnClose = view.findViewById(R.id.btnClose)

        auth = Firebase.auth

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("844272747520-rp6ntj34ct1oc08krldderghhg8dk8at.apps.googleusercontent.com")
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)

        btnLogIn.setOnClickListener{
            val intent = Intent(activity, LoginActivity::class.java)
            startActivity(intent)
        }

        btnSignUp.setOnClickListener{
            val intent = Intent(activity, SignUpActivity::class.java)
            startActivity(intent)
        }

        btnGoogle.setOnClickListener{
            signInWithGoogle()
        }

        btnClose.setOnClickListener{
            dismiss()
        }
    }

    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult())
    {result ->
        if(result.resultCode == Activity.RESULT_OK){
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            handleResults(task)
        }
    }

    private fun handleResults(task: Task<GoogleSignInAccount>) {
        if(task.isSuccessful){
            val account : GoogleSignInAccount? = task.result
            if(account != null){
                updateUI(account)
            }
        }else{
            Toast.makeText(this.context, "Authentication failed", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateUI(account: GoogleSignInAccount) {
        val googleSignInClient = GoogleSignIn.getClient(requireActivity(), GoogleSignInOptions.DEFAULT_SIGN_IN)

        googleSignInClient.signOut().addOnCompleteListener {
            if (it.isSuccessful) {
                val credential = GoogleAuthProvider.getCredential(account.idToken, null)
                auth.signInWithCredential(credential).addOnCompleteListener { signInTask ->
                    if (signInTask.isSuccessful) {
                        val user = auth.currentUser
                        val userId = user!!.uid

                        val db = com.google.firebase.ktx.Firebase.firestore
                        val usersCollection = db.collection("users")

                        usersCollection.document(userId).get().addOnCompleteListener { snapshotTask ->
                            if (snapshotTask.isSuccessful) {
                                val document = snapshotTask.result
                                if (document.exists()) {
                                    val intent = Intent(requireContext(), MainActivity::class.java)
                                    intent.putExtra("userLoggedIn", true)
                                    startActivity(intent)
                                    requireActivity().finish()
                                } else {
                                    val firstName = user.displayName?.split(" ")?.getOrNull(0) ?: ""
                                    val lastName = user.displayName?.split(" ")?.getOrNull(1) ?: ""
                                    val email = user.email
                                    val photoUrl = user.photoUrl.toString()

                                    val newUser = User(
                                        userId,
                                        firstName,
                                        lastName,
                                        email!!,
                                        "",
                                        Date(),
                                        "",
                                        photoUrl
                                    )

                                    usersCollection.document(userId)
                                        .set(newUser)
                                        .addOnSuccessListener {
                                            Toast.makeText(requireContext(), "Account created successfully", Toast.LENGTH_SHORT).show()

                                            val intent = Intent(requireContext(), MainActivity::class.java)
                                            intent.putExtra("userLoggedIn", true)
                                            startActivity(intent)
                                            requireActivity().finish()
                                        }
                                        .addOnFailureListener { e ->
                                            Toast.makeText(requireContext(), "Failed to store user data in Firestore: $e", Toast.LENGTH_SHORT).show()
                                        }
                                }
                            } else {
                                Toast.makeText(requireContext(), "Failed to check user data in Firestore: ${snapshotTask.exception}", Toast.LENGTH_SHORT).show()
                            }
                        }
                    } else {
                        Toast.makeText(requireContext(), "Authentication failed", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(requireContext(), "Sign out failed", Toast.LENGTH_SHORT).show()
            }
        }
    }




    private fun signInWithGoogle(){
        val signInIntent = googleSignInClient.signInIntent
        launcher.launch(signInIntent)
    }



    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = BottomSheetDialog(requireContext(), theme)
        dialog.setOnShowListener {

            val bottomSheetDialog = it as BottomSheetDialog
            val parentLayout =
                bottomSheetDialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            parentLayout?.let { it ->
                val behaviour = BottomSheetBehavior.from(it)
                setupFullHeight(it)
                behaviour.state = BottomSheetBehavior.STATE_EXPANDED
                //behaviour.isDraggable = false
            }
        }
        return dialog
    }

    private fun setupFullHeight(bottomSheet: View) {
        val layoutParams = bottomSheet.layoutParams
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT
        bottomSheet.layoutParams = layoutParams
    }


}

