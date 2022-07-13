package com.testing.testseucom.ui

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.testing.testseucom.R
import com.testing.testseucom.adapter.AdapterList
import com.testing.testseucom.databinding.ActivityListBinding
import com.testing.testseucom.response.Data
import com.testing.testseucom.response.ResponseList
import com.testing.testseucom.response.uid.ResponseByUid
import com.testing.testseucom.ui.create.CreateActivity
import com.testing.testseucom.ui.create.CreateContract
import com.testing.testseucom.ui.create.CreatePresenter
import com.testing.testseucom.ui.edit.EditActivity
import www.sanju.motiontoast.MotionToast
import www.sanju.motiontoast.MotionToastStyle

class ListActivity : AppCompatActivity(), ListContract.View, AdapterList.ItemAdapterCallback , CreateContract.View {
    private lateinit var presenter: ListPresenter
    private lateinit var createPresenter: CreatePresenter
    private var loadingDialog: Dialog? = null
    private lateinit var binding: ActivityListBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initLoading()
        presenter = ListPresenter(this)
        createPresenter = CreatePresenter(this)
        presenter.getList()

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

    override fun getListSuccess(responseList: ResponseList) {
        val adapterList = AdapterList(responseList.data, this, this)
        binding.rvList.layoutManager = LinearLayoutManager(this)
        binding.rvList.adapter = adapterList


        adapterList.setOnUpdateListener { position ->
            responseList.data[position].locID.apply {
                startActivity(Intent(this@ListActivity, EditActivity::class.java)
                    .putExtra("data", this))
            }
        }

        binding.floatingActionButton.setOnClickListener {
            val intent = Intent(this, CreateActivity::class.java)
            startActivity(intent)
        }
    }


    override fun getListFailure(message: String) {
        Toast.makeText(this, message,Toast.LENGTH_SHORT).show()
    }

    override fun saveSuccess(responseByUid: ResponseByUid) {
        presenter.getList()
        MotionToast.darkColorToast(this,
            "Success Create",
            "Terima kasih",
            MotionToastStyle.SUCCESS,
            MotionToast.GRAVITY_BOTTOM,
            MotionToast.LONG_DURATION,
            ResourcesCompat.getFont(this,R.font.helveticabold))

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

    override fun onClick(view: View, data: Data) {
        showDialogItem(data)
    }

    private fun showDialogItem(data: Data) {
        val alertDialogBuilder = AlertDialog.Builder(this)

        // set title dialog
        alertDialogBuilder.setTitle("Silahkan Pilih Kategori nya")

        // set pesan dari dialog
        alertDialogBuilder
            .setMessage("Klik Salah satu untuk di Create")
            .setIcon(R.mipmap.ic_launcher_round)
            .setCancelable(true)
            .setPositiveButton(
                "Building"
            ) { _, id ->
                CartDialogBuild(data)
            }
            .setNeutralButton(
                "Floor"
            ) { _, id ->
                CartDialogFloor(data)
            }

            .setNegativeButton(
                "Room"
            ) { _, id ->
                CartDialogRoom(data)
            }

        val alertDialog: AlertDialog = alertDialogBuilder.create()

        alertDialog.show()
    }

    private fun CartDialogRoom(data: Data) {
        val cartDialog = layoutInflater.inflate(R.layout.bottom_sheet_dialog, null)
        val dialog = BottomSheetDialog(this)
        dialog.apply {
            setContentView(cartDialog)
            setTitle("")
            setCancelable(true)
        }


        cartDialog.findViewById<TextInputEditText>(R.id.autoLocName).text.toString()

        val nameBuild = arrayOf("RO")
        val arrayBuild =
            ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, nameBuild)
        cartDialog.findViewById<AutoCompleteTextView>(R.id.autoLocType).setAdapter(arrayBuild)

        cartDialog.findViewById<AutoCompleteTextView>(R.id.autoLocType)
            .setOnItemClickListener { adapterView, _, i, _, ->
                cartDialog.findViewById<TextView>(R.id.tvLocType).text =
                    adapterView.getItemAtPosition(i).toString()
            }

        val nameLocCode = arrayOf("${data.locCode}")
        val arrayLocCode =
            ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, nameLocCode)
        cartDialog.findViewById<AutoCompleteTextView>(R.id.autoProjectId).setAdapter(arrayLocCode)

        cartDialog.findViewById<AutoCompleteTextView>(R.id.autoProjectId)
            .setOnItemClickListener { adapterView, _, i, _, ->
                cartDialog.findViewById<TextView>(R.id.tvProjectId).text =
                    adapterView.getItemAtPosition(i).toString()
            }

        cartDialog.findViewById<MaterialButton>(R.id.btnTambah).setOnClickListener {
            val locName = cartDialog.findViewById<TextInputEditText>(R.id.autoLocName).text.toString()
            val locType = cartDialog.findViewById<TextView>(R.id.tvLocType).text.toString()
            val roomCode = cartDialog.findViewById<TextView>(R.id.tvProjectId).text.toString()

            when {
                locName.isEmpty() -> {
                    cartDialog.findViewById<TextInputLayout>(R.id.LocNameTextInputLayout).error = "tidak boleh kosong"
                    cartDialog.findViewById<TextInputLayout>(R.id.LocNameTextInputLayout).requestFocus()
                }

                locType == "null" -> {
                    MotionToast.darkColorToast(this,
                        "Pilih locType anda",
                        "Jangan tergesa - gesa say ☹️",
                        MotionToastStyle.INFO,
                        MotionToast.GRAVITY_BOTTOM,
                        MotionToast.LONG_DURATION,
                        ResourcesCompat.getFont(this,R.font.helveticabold))
                }

                roomCode  == "null" -> {
                    MotionToast.darkColorToast(this,
                        "Pilih Project code anda",
                        "Jangan tergesa - gesa say ☹️",
                        MotionToastStyle.INFO,
                        MotionToast.GRAVITY_BOTTOM,
                        MotionToast.LONG_DURATION,
                        ResourcesCompat.getFont(this,R.font.helveticabold))
                }
                else -> {
                    createPresenter.submit(
                        locName,
                        locType,
                        "",
                        "",
                        roomCode
                    )
                }
            }
        }

        dialog.show()
    }

    private fun CartDialogFloor(data: Data) {
        val cartDialog = layoutInflater.inflate(R.layout.bottom_sheet_dialog, null)
        val dialog = BottomSheetDialog(this)
        dialog.apply {
            setContentView(cartDialog)
            setTitle("")
            setCancelable(true)
        }


        cartDialog.findViewById<TextInputEditText>(R.id.autoLocName).text.toString()

        val nameBuild = arrayOf("FL")
        val arrayBuild =
            ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, nameBuild)
        cartDialog.findViewById<AutoCompleteTextView>(R.id.autoLocType).setAdapter(arrayBuild)

        cartDialog.findViewById<AutoCompleteTextView>(R.id.autoLocType)
            .setOnItemClickListener { adapterView, _, i, _, ->
                cartDialog.findViewById<TextView>(R.id.tvLocType).text =
                    adapterView.getItemAtPosition(i).toString()
            }

        val nameLocCode = arrayOf("${data.locCode}")
        val arrayLocCode =
            ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, nameLocCode)
        cartDialog.findViewById<AutoCompleteTextView>(R.id.autoProjectId).setAdapter(arrayLocCode)

        cartDialog.findViewById<AutoCompleteTextView>(R.id.autoProjectId)
            .setOnItemClickListener { adapterView, _, i, _, ->
                cartDialog.findViewById<TextView>(R.id.tvProjectId).text =
                    adapterView.getItemAtPosition(i).toString()
            }

        cartDialog.findViewById<MaterialButton>(R.id.btnTambah).setOnClickListener {
            val locName = cartDialog.findViewById<TextInputEditText>(R.id.autoLocName).text.toString()
            val locType = cartDialog.findViewById<TextView>(R.id.tvLocType).text.toString()
            val floorCode = cartDialog.findViewById<TextView>(R.id.tvProjectId).text.toString()

            when {
                locName.isEmpty() -> {
                    cartDialog.findViewById<TextInputLayout>(R.id.LocNameTextInputLayout).error = "tidak boleh kosong"
                    cartDialog.findViewById<TextInputLayout>(R.id.LocNameTextInputLayout).requestFocus()
                }

                locType == "null" -> {
                    MotionToast.darkColorToast(this,
                        "Pilih locType anda",
                        "Jangan tergesa - gesa say ☹️",
                        MotionToastStyle.INFO,
                        MotionToast.GRAVITY_BOTTOM,
                        MotionToast.LONG_DURATION,
                        ResourcesCompat.getFont(this,R.font.helveticabold))
                }

                floorCode  == "null" -> {
                    MotionToast.darkColorToast(this,
                        "Pilih Project code anda",
                        "Jangan tergesa - gesa say ☹️",
                        MotionToastStyle.INFO,
                        MotionToast.GRAVITY_BOTTOM,
                        MotionToast.LONG_DURATION,
                        ResourcesCompat.getFont(this,R.font.helveticabold))
                }
                else -> {
                    createPresenter.submit(
                        locName,
                        locType,
                        "",
                        floorCode,
                        ""
                    )
                }
            }
        }

        dialog.show()
    }

    private fun CartDialogBuild(data: Data) {
        val cartDialog = layoutInflater.inflate(R.layout.bottom_sheet_dialog, null)
        val dialog = BottomSheetDialog(this)
        dialog.apply {
            setContentView(cartDialog)
            setTitle("")
            setCancelable(true)
        }


        cartDialog.findViewById<TextInputEditText>(R.id.autoLocName).text.toString()

        val nameBuild = arrayOf("BD")
        val arrayBuild =
            ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, nameBuild)
        cartDialog.findViewById<AutoCompleteTextView>(R.id.autoLocType).setAdapter(arrayBuild)

        cartDialog.findViewById<AutoCompleteTextView>(R.id.autoLocType)
            .setOnItemClickListener { adapterView, _, i, _, ->
                cartDialog.findViewById<TextView>(R.id.tvLocType).text =
                    adapterView.getItemAtPosition(i).toString()
            }

        val nameLocCode = arrayOf("${data.locCode}")
        val arrayLocCode =
            ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, nameLocCode)
        cartDialog.findViewById<AutoCompleteTextView>(R.id.autoProjectId).setAdapter(arrayLocCode)

        cartDialog.findViewById<AutoCompleteTextView>(R.id.autoProjectId)
            .setOnItemClickListener { adapterView, _, i, _, ->
                cartDialog.findViewById<TextView>(R.id.tvProjectId).text =
                    adapterView.getItemAtPosition(i).toString()
            }

        cartDialog.findViewById<MaterialButton>(R.id.btnTambah).setOnClickListener {
            val locName = cartDialog.findViewById<TextInputEditText>(R.id.autoLocName).text.toString()
            val locType = cartDialog.findViewById<TextView>(R.id.tvLocType).text.toString()
            val projectCode = cartDialog.findViewById<TextView>(R.id.tvProjectId).text.toString()

            when {
                locName.isEmpty() -> {
                    cartDialog.findViewById<TextInputLayout>(R.id.LocNameTextInputLayout).error = "tidak boleh kosong"
                    cartDialog.findViewById<TextInputLayout>(R.id.LocNameTextInputLayout).requestFocus()
                }

                locType == "null" -> {
                    MotionToast.darkColorToast(this,
                        "Pilih locType anda",
                        "Jangan tergesa - gesa say ☹️",
                        MotionToastStyle.INFO,
                        MotionToast.GRAVITY_BOTTOM,
                        MotionToast.LONG_DURATION,
                        ResourcesCompat.getFont(this,R.font.helveticabold))
                }

                projectCode  == "null" -> {
                    MotionToast.darkColorToast(this,
                        "Pilih Project code anda",
                        "Jangan tergesa - gesa say ☹️",
                        MotionToastStyle.INFO,
                        MotionToast.GRAVITY_BOTTOM,
                        MotionToast.LONG_DURATION,
                        ResourcesCompat.getFont(this,R.font.helveticabold))
                }
                else -> {
                    createPresenter.submit(
                        locName,
                        locType,
                        projectCode,
                        "",
                        ""
                    )
                }
            }
        }

        dialog.show()
    }


    override fun onDestroy() {
        presenter.unSubscribe()
        super.onDestroy()
    }
}