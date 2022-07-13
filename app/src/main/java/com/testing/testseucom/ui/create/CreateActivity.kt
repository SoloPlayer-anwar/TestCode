package com.testing.testseucom.ui.create

import android.app.Dialog
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.testing.testseucom.R
import com.testing.testseucom.databinding.ActivityCreateBinding
import com.testing.testseucom.response.uid.ResponseByUid

class CreateActivity : AppCompatActivity(), CreateContract.View {
    private lateinit var binding: ActivityCreateBinding
    private lateinit var presenter: CreatePresenter
    private var loadingDialog: Dialog? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initLoading()
        presenter = CreatePresenter(this)

        initView()
    }

    private fun initView() {

        val nameProject = arrayOf("PR")
        val arrayProject = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, nameProject)
        binding.autoCompleteType.setAdapter(arrayProject)
        binding.autoCompleteType.setOnItemClickListener {
            adapterView,_,i,_, ->
            binding.tvLocType.text = adapterView.getItemAtPosition(i).toString()
        }

        binding.btnSave.setOnClickListener {
            val locName = binding.etLocName.text.toString()
            val locType = binding.tvLocType.text.toString()

            when {
                locName.isEmpty() -> {
                    binding.textInputLayout.error = "nama harus di isi"
                    binding.textInputLayout.requestFocus()
                }

                locType.isEmpty() -> {
                    binding.locTypeTextInputLayout.error = "tidak boleh kosong"
                    binding.locTypeTextInputLayout.requestFocus()
                }
                else -> {
                    presenter.submit(
                        locName,
                        locType,
                        "",
                        "",
                        ""
                    )
                }
            }
        }
    }

    private fun initLoading() {
        loadingDialog = Dialog(this)
        val dialogView = layoutInflater.inflate(R.layout.loading_dialog, null)

        loadingDialog?.let {
            it.setContentView(dialogView)
            it.setCancelable(false)
            it.window?.setBackgroundDrawableResource(R.color.tsp)
        }
    }

    override fun saveSuccess(responseByUid: ResponseByUid) {
        Toast.makeText(this, "Success Create", Toast.LENGTH_SHORT).show()
    }

    override fun saveFailure(message: String) {
        Toast.makeText(this, message,Toast.LENGTH_SHORT).show()
    }

    override fun showLoading(loading: Boolean) {
        when(loading) {
            true -> loadingDialog?.show()
            false -> loadingDialog?.dismiss()
        }
    }

}