package com.testing.testseucom.ui.edit

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.testing.testseucom.R
import com.testing.testseucom.databinding.ActivityEditBinding
import com.testing.testseucom.response.uid.Data
import com.testing.testseucom.response.uid.ResponseByUid
import com.testing.testseucom.ui.ListActivity
import www.sanju.motiontoast.MotionToast
import www.sanju.motiontoast.MotionToastStyle

class EditActivity : AppCompatActivity(), EditContract.View {
    private lateinit var binding: ActivityEditBinding
    private lateinit var presenter: EditPresenter
    private var loadingDialog: Dialog? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initLoading()
        val data = intent.getStringExtra("data")
        presenter = EditPresenter(this)
        presenter.getList(data!!)



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

    override fun editListSuccess(responseByUid: ResponseByUid) {
        startActivity(Intent(this, ListActivity::class.java))
        MotionToast.darkColorToast(this,
            "Success Update",
            "Terima kasih",
            MotionToastStyle.SUCCESS,
            MotionToast.GRAVITY_BOTTOM,
            MotionToast.LONG_DURATION,
            ResourcesCompat.getFont(this,R.font.helveticabold))
    }

    override fun getListSuccess(responseByUid: ResponseByUid) {

        val nameType = arrayOf("${responseByUid.data.project.locType}", "${responseByUid.data.building.locType}", "${responseByUid.data.floor.locType}", "${responseByUid.data.locType}")
        val arrayType = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, nameType )
        binding.autoLocType.setAdapter(arrayType)
        binding.autoLocType.setOnItemClickListener { adapterView, _, i, _ ->
            binding.tvLocType.text = adapterView.getItemAtPosition(i).toString()
        }

        val nameProjectCode = arrayOf("${responseByUid.data.project.locCode}")
        val arrayProject = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, nameProjectCode )
        binding.autoProjectCode.setAdapter(arrayProject)
        binding.autoProjectCode.setOnItemClickListener { adapterView, _, i, _ ->
            binding.tvProjectCode.text = adapterView.getItemAtPosition(i).toString()
        }

        val nameBuildCode = arrayOf("${responseByUid.data.building.locCode}")
        val arrayBuild = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, nameBuildCode )
        binding.autoBuildCode.setAdapter(arrayBuild)
        binding.autoBuildCode.setOnItemClickListener { adapterView, _, i, _ ->
            binding.tvBuildCode.text = adapterView.getItemAtPosition(i).toString()
        }

        val nameFloor = arrayOf("${responseByUid.data.floor.locCode}")
        val arrayFloor = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, nameFloor )
        binding.autoFloorCode.setAdapter(arrayFloor)
        binding.autoFloorCode.setOnItemClickListener { adapterView, _, i, _ ->
            binding.tvFloorCode.text = adapterView.getItemAtPosition(i).toString()
        }

        val nameRoom = arrayOf("${responseByUid.data.locCode}")
        val arrayRoom = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, nameRoom )
        binding.autoRoomCode.setAdapter(arrayRoom)
        binding.autoRoomCode.setOnItemClickListener { adapterView, _, i, _ ->
            binding.tvRoomCode.text = adapterView.getItemAtPosition(i).toString()
        }

        binding.button.setOnClickListener {
            val locId = responseByUid.data.locID
            val locName = binding.autoLocName.text.toString()
            val tvLocType = binding.tvLocType.text.toString()
            val tvProjectCode = binding.tvProjectCode.text.toString()
            val tvBuildCode = binding.tvBuildCode.text.toString()
            val tvFloorCode = binding.tvFloorCode.text.toString()
            val tvRoomCode = binding.tvRoomCode.text.toString()

            if(locName.isEmpty()) {
                binding.locTypeTextInputLayout.error = "harus di isi"
                binding.locTypeTextInputLayout.requestFocus()
            }else {
                presenter.submitEdit(locId, locName, tvLocType, tvProjectCode, tvBuildCode, tvFloorCode)
            }
        }
    }


    override fun editListFailure(message: String) {
        Toast.makeText(this, "Ada beberapa field masih kosong",Toast.LENGTH_SHORT).show()
    }

    override fun showLoading(loading: Boolean) {
        when(loading) {
            true -> loadingDialog?.show()
            false -> loadingDialog?.dismiss()
        }
    }
}