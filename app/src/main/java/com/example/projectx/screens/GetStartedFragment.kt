package com.example.projectx.screens

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.projectx.R
import com.example.projectx.daos.StudentDao
import com.example.projectx.daos.TeacherDao
import com.example.projectx.databinding.FragmentGetStartedBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class GetStartedFragment : Fragment() {
    private var _binding: FragmentGetStartedBinding? = null
    private val binding get() = _binding!!
    private val RC_SIGN_IN: Int = 123
    private val TAG: String = "Message"
    private lateinit var googleSignInClient: GoogleSignInClient

    private lateinit var studentDao: StudentDao
    private lateinit var teacherDao: TeacherDao

    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentGetStartedBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this.requireContext(), gso)
        googleSignInClient.revokeAccess()
        auth = Firebase.auth
        studentDao = StudentDao()
        teacherDao = TeacherDao()


        binding.button2.setOnClickListener {
            signIn()
        }
    }

    override fun onStart() {
        super.onStart()
        if (auth.currentUser != null)
            updateUI(auth.currentUser)
    }


    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.id)
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e)
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener() { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCredential:success")
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    updateUI(null)
                }
            }
    }


    private fun updateUI(firebaseUser: FirebaseUser?) {
        if (firebaseUser != null) {
            binding.signupConstraint.visibility = View.INVISIBLE
            binding.loaderConstraint.visibility = View.VISIBLE

            val snapshot = studentDao.getStudentById(auth.currentUser!!.uid)
                .addOnCompleteListener {
                    val temp = it.result.exists()
                    if (!temp) {
                        val snapshot2 =
                            teacherDao.getTeacherById(auth.currentUser!!.uid)
                                .addOnCompleteListener {
                                    var teacherResult = it.result.exists()
                                    if (!teacherResult) {
                                        val action =
                                            GetStartedFragmentDirections.actionGetStartedFragmentToDetailsFragment()
                                        requireView().findNavController().navigate(action)
                                    } else {
                                        val action =
                                            GetStartedFragmentDirections.actionGetStartedFragmentToTeachersFragment()
                                        requireView().findNavController().navigate(action)
                                    }
                        }


                }else{
                    val action =
                        GetStartedFragmentDirections.actionGetStartedFragmentToStudentFragment()
                    requireView().findNavController().navigate(action)
                }
            }
        }
    }
}


