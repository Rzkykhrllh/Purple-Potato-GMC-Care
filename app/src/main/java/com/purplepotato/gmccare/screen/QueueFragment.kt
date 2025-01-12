package com.purplepotato.gmccare.screen

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.purplepotato.gmccare.R
import com.purplepotato.gmccare.model.Pasien
import com.purplepotato.gmccare.pref.Preferences
import kotlinx.android.synthetic.main.fragment_queue.*
import java.util.*


class QueueFragment : Fragment(), View.OnClickListener {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var sharedPreferences: Preferences
    private var timeLeftInMillis = START_TIMER

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_queue, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPreferences = Preferences(requireContext())
        drawerLayout = requireActivity().findViewById(R.id.drawer_layout)

        btnOpenDrawerInQueue.setOnClickListener(this)
        isQueued()

        Log.d("queue", "udah di queue fragment")
        updateCalling()
        updateRuang1()
        updateRuang2()
        updateRuang3()
        updateRuang4()
        updateRuang5()
        updateRuang6()

    }

    private fun isQueued() {
        val isQueued = sharedPreferences.getIsQueued()
        if (isQueued) {
            tvNotQueued.visibility = View.GONE
            val num = sharedPreferences.getUserQueueNumber()
            tvYourQueueNumber.text = num.toString()
        } else {
            tvYourQueueNumber.visibility = View.INVISIBLE
            tv_nomor_mu.visibility = View.INVISIBLE
            tvNotQueued.visibility = View.VISIBLE
        }

    }

    private fun updateCalling() {
        //function to update current number
        val userQueueNumber = sharedPreferences.getUserQueueNumber()
        try {
            FirebaseDatabase
                .getInstance()
                .reference
                .child("CALLING").addValueEventListener(
                    object : ValueEventListener {
                        override fun onDataChange(p0: DataSnapshot) {
                            val temp = p0.getValue(Pasien::class.java)

                            Log.d("nomer calling", "$temp")

                            temp?.let {
                                tvCurrentTicketNumber.text = temp.no_antrian
                                tv_title2.text = "silahkan menuju ke ruang ${temp.status}"

                                when (userQueueNumber - temp.no_antrian.toInt()) {
                                    10 -> {
                                        sendNotification("10 nomor lagi menuju nomor anda")
                                    }

                                    5 -> {
                                        sendNotification("5 nomor lagi menuju nomor anda")
                                    }

                                    3 -> {
                                        sendNotification("3 nomor lagi menuju nomor anda")
                                    }

                                    1 -> {
                                        sendNotification("1 nomor lagi menuju nomor anda")
                                    }
                                    0 -> {
                                        sendNotification("Nomor anda telah dipanggil")
                                        setTimer()
                                        sharedPreferences.setUserQueueNumber(-1)
                                        sharedPreferences.setIsQueued(false)
                                        val snackBar = Snackbar.make(
                                            queue_main_layout,
                                            "Nomor anda telah dipanggil",
                                            Snackbar.LENGTH_INDEFINITE
                                        )
                                        snackBar.setAction("OKE") {
                                            snackBar.dismiss()
                                        }
                                        snackBar.show()
                                    }
                                }
                            }

                            //entar manggil fungsi notif dan timer disini
                        }

                        override fun onCancelled(p0: DatabaseError) {
                            Toast.makeText(activity, "Nomor gagal diambil", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                )
        } catch (e: RuntimeException) {
            Log.d("updateCalling", e.message as String)
        }


    }

    private fun updateRuang1() {
        var nomor = ""
        try {
            FirebaseDatabase
                .getInstance()
                .getReference("RUANG_1").addValueEventListener(
                    object : ValueEventListener {
                        override fun onDataChange(p0: DataSnapshot) {

                            for (child in p0.children) {
                                val data = child.getValue(Pasien::class.java)
                                Log.d("cek nomor", "aye aye")

                                if (data != null) {
                                    nomor = data.no_antrian
                                    Log.d("nomor ruang 1", "$nomor")
                                }

                            }
                            num_room_1.text = nomor
                        }

                        override fun onCancelled(p0: DatabaseError) {
                            Toast.makeText(activity, "Nomor gagal diambil", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                )

        } catch (e: RuntimeException) {
            Log.d("updateRuang1", e.message as String)
        }

    }

    private fun updateRuang2() {
        var nomor = ""
        try {
            FirebaseDatabase
                .getInstance()
                .getReference("RUANG_2").addValueEventListener(
                    object : ValueEventListener {
                        override fun onDataChange(p0: DataSnapshot) {


                            for (child in p0.children) {
                                val data = child.getValue(Pasien::class.java)
                                Log.d("cek nomor", "aye aye")

                                if (data != null) {
                                    nomor = data.no_antrian
                                    Log.d("nomor ruang 2", "$nomor")
                                }

                            }
                            num_room_2.text = nomor
                        }

                        override fun onCancelled(p0: DatabaseError) {
                            Toast.makeText(activity, "Nomor gagal diambil", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                )

        } catch (e: RuntimeException) {
            Log.d("updateRuang2", e.message as String)
        }

    }

    private fun updateRuang3() {
        var nomor = ""
        try {
            FirebaseDatabase
                .getInstance()
                .getReference("RUANG_3").addValueEventListener(
                    object : ValueEventListener {
                        override fun onDataChange(p0: DataSnapshot) {


                            for (child in p0.children) {
                                val data = child.getValue(Pasien::class.java)
                                Log.d("cek nomor", "aye aye")

                                if (data != null) {
                                    nomor = data.no_antrian
                                    Log.d("nomor ruang 3", "$nomor")
                                }

                            }
                            num_room_3.text = nomor
                        }

                        override fun onCancelled(p0: DatabaseError) {
                            Toast.makeText(activity, "Nomor gagal diambil", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                )
        } catch (e: RuntimeException) {
            Log.d("updateRuang3", e.message as String)
        }


    }

    private fun updateRuang4() {
        var nomor = ""
        try {
            FirebaseDatabase
                .getInstance()
                .getReference("RUANG_4").addValueEventListener(
                    object : ValueEventListener {
                        override fun onDataChange(p0: DataSnapshot) {


                            for (child in p0.children) {
                                val data = child.getValue(Pasien::class.java)
                                Log.d("cek nomor", "aye aye")

                                if (data != null) {
                                    nomor = data.no_antrian
                                    Log.d("nomor ruang 4", "$nomor")
                                }

                            }
                            num_room_4.text = nomor
                        }

                        override fun onCancelled(p0: DatabaseError) {
                            Toast.makeText(activity, "Nomor gagal diambil", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                )
        } catch (e: RuntimeException) {
            Log.d("updateRuang4", e.message as String)
        }
    }

    private fun updateRuang5() {
        var nomor = ""
        try {
            FirebaseDatabase
                .getInstance()
                .getReference("RUANG_5").addValueEventListener(
                    object : ValueEventListener {
                        override fun onDataChange(p0: DataSnapshot) {


                            for (child in p0.children) {
                                val data = child.getValue(Pasien::class.java)
                                Log.d("cek nomor", "aye aye")

                                if (data != null) {
                                    nomor = data.no_antrian
                                    Log.d("nomor ruang 5", "$nomor")
                                }

                            }
                            num_room_5.text = nomor
                        }

                        override fun onCancelled(p0: DatabaseError) {
                            Toast.makeText(activity, "Nomor gagal diambil", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                )

        } catch (e: RuntimeException) {
            Log.d("updateRuang5", e.message as String)
        }

    }

    private fun updateRuang6() {
        var nomor = ""
        try {
            FirebaseDatabase
                .getInstance()
                .getReference("RUANG_6").addValueEventListener(
                    object : ValueEventListener {
                        override fun onDataChange(p0: DataSnapshot) {


                            for (child in p0.children) {
                                val data = child.getValue(Pasien::class.java)
                                Log.d("cek nomor", "aye aye")

                                if (data != null) {
                                    nomor = data.no_antrian
                                    Log.d("nomor ruang 6", "$nomor")
                                }

                            }
                            num_room_6.text = nomor
                        }

                        override fun onCancelled(p0: DatabaseError) {
                            Toast.makeText(activity, "Nomor gagal diambil", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                )
        } catch (e: RuntimeException) {
            Log.d("updateRuang6", e.message as String)
        }

    }

    private fun sendNotification(message: String) {
        val mNotificationManager =
            requireContext().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val builder = NotificationCompat.Builder(requireContext(), CHANNEL_ID)
            .setContentTitle("Reminder")
            .setContentText(message)
            .setSmallIcon(R.drawable.ic_baseline_notifications)
            .setAutoCancel(true)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH
            )

            builder.setChannelId(CHANNEL_ID)

            mNotificationManager.createNotificationChannel(notificationChannel)
        }

        val notification = builder.build()

        mNotificationManager.notify(NOTIFICATION_ID, notification)

    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btnOpenDrawerInQueue -> {
                drawerLayout.openDrawer(GravityCompat.START)
            }
        }
    }

    private fun setTimer() {
        tvTimer.visibility = View.VISIBLE
        val timer = object : CountDownTimer(START_TIMER, 1_000) {
            override fun onTick(millisUntilFinished: Long) {
                timeLeftInMillis = millisUntilFinished
                updateCountDownText()
            }

            override fun onFinish() {
                tvTimer.visibility = View.GONE
            }
        }.start()
    }

    private fun updateCountDownText() {
        val minutes = (timeLeftInMillis / 1000 / 60).toInt()
        val second = ((timeLeftInMillis / 1000) % 60).toInt()

        val timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, second)

        tvTimer.text = timeLeftFormatted
    }

    companion object {
        private const val CHANNEL_ID = "channel_1"
        private const val CHANNEL_NAME = "notification"
        private const val NOTIFICATION_ID = 1
        private const val START_TIMER: Long = 1_000 * 60 * 10
    }

}