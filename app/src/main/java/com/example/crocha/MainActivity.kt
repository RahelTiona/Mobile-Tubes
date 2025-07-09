package com.example.crocha

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Set up Get Started button click listener
        findViewById<android.widget.Button>(R.id.getStartedButton).setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    private fun insertStitchesToFirestore() {
        val db = FirebaseFirestore.getInstance()
        val stitches = listOf(
            mapOf(
                "name" to "Magic Ring",
                "steps" to listOf(
                    "Lingkarkan benang membentuk huruf 'O' di atas dua jari tangan kiri.",
                    "Masukkan hakpen dari depan ke belakang melalui lingkaran.",
                    "Kaitkan benang kerja (yang terhubung ke gulungan benang) dan tarik keluar lewat lingkaran.",
                    "Kaitkan benang sekali lagi dan tarik melalui loop di hakpen → ini membentuk satu rantai (chain) untuk mengunci magic ring.",
                    "Sekarang kamu bisa mulai merajut jumlah tusukan sesuai pola (misalnya 6 tusuk sc) ke dalam lingkaran tersebut."
                ),
                "link" to "https://youtu.be/rQ3jg3qe720?si=Uz8iRRa10BTWVETF"
            ),
            mapOf(
                "name" to "Single Crochet",
                "steps" to listOf(
                    "Masukkan hakpen ke dalam lubang tusukan.",
                    "Kaitkan benang dan tarik keluar (di hakpen sekarang ada 2 loop).",
                    "Kaitkan benang lagi dan tarik melewati kedua loop sekaligus, selesai satu single crochet."
                ),
                "link" to "https://youtu.be/rQ3jg3qe720?si=Uz8iRRa10BTWVETF"
            ),
            mapOf(
                "name" to "Increase",
                "steps" to listOf(
                    "Lakukan 1 tusuk single crochet seperti biasa ke dalam satu lubang.",
                    "Di lubang yang sama, lakukan 1 tusuk single crochet lagi.",
                    "Sekarang satu lubang menghasilkan dua tusukan — itulah increase."
                ),
                "link" to "https://youtu.be/rQ3jg3qe720?si=Uz8iRRa10BTWVETF"
            ),
            mapOf(
                "name" to "Decrease",
                "steps" to listOf(
                    "Masukkan hakpen hanya ke bagian depan dari 2 tusukan berikutnya (front loop).",
                    "Sekarang ada 2 loop di hakpen dari 2 lubang berbeda.",
                    "Kait benang dan tarik melalui dua loop depan tadi.",
                    "Sekarang ada 2 loop di hakpen.",
                    "Kait benang lagi, tarik lewat kedua loop itu, selesai 1 decrease."
                ),
                "link" to "https://youtu.be/rQ3jg3qe720?si=Uz8iRRa10BTWVETF"
            ),
            mapOf(
                "name" to "Double Crochet",
                "steps" to listOf(
                    "Kaitkan benang dulu sebelum memasukkan hakpen (beda dengan sc!).",
                    "Masukkan hakpen ke dalam lubang tusukan.",
                    "Kaitkan benang, tarik keluar (ada 3 loop di hakpen).",
                    "Kaitkan benang, tarik lewat 2 loop pertama (sisa 2 loop).",
                    "Kaitkan lagi, tarik lewat 2 loop terakhir, selesai 1 double crochet."
                ),
                "link" to "https://youtu.be/rQ3jg3qe720?si=Uz8iRRa10BTWVETF"
            )
        )
        var successCount = 0
        var failCount = 0
        for (stitch in stitches) {
            db.collection("stitches")
                .add(stitch)
                .addOnSuccessListener {
                    successCount++
                    if (successCount + failCount == stitches.size) {
                        Toast.makeText(this, "Stitches insert success: $successCount, failed: $failCount", Toast.LENGTH_SHORT).show()
                    }
                }
                .addOnFailureListener {
                    failCount++
                    if (successCount + failCount == stitches.size) {
                        Toast.makeText(this, "Stitches insert success: $successCount, failed: $failCount", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }

    private fun insertPatternToFirestore() {
        val db = FirebaseFirestore.getInstance()
        val patterns = listOf(
            mapOf(
                "name" to "Small Chicken Amigurumi",
                "image" to "chicken_amigurumi",
                "materials" to listOf(
                    "Benang milk cotton 5ply",
                    "Hakpen 2,5 mm",
                    "Jarum tapestri",
                    "Gunting",
                    "Stitch marker"
                ),
                "steps" to listOf(
                    "6 sc di dalam magic ring",
                    "6 inc",
                    "(sc, inc) x6",
                    "18 sc",
                    "18 sc",
                    "4 sc, 4 inc, dec, 4 inc, 4 sc",
                    "25 sc",
                    "4 sc, 4 dec, sc, 4 dec, 4 sc",
                    "(sc, dec) x5, dec",
                    "Isi dengan dakron/kapas",
                    "5 dec, sc",
                    "Potong benang dan amankan ujungnya"
                )
            ),
            mapOf(
                "name" to "Strawberry Amigurumi",
                "image" to "strawberry_amigurumi",
                "materials" to listOf(
                    "Benang milk cotton 5ply",
                    "Hakpen 2,5 mm",
                    "Jarum tapestri",
                    "Gunting",
                    "Stitch marker"
                ),
                "steps" to listOf(
                    "6 sc di dalam magic ring",
                    "(sc, inc) x3",
                    "(2 sc, inc) x3",
                    "(3 sc, inc) x3",
                    "(4 sc, inc) x3",
                    "(5 sc, inc) x3",
                    "(2 sc, inc) x7",
                    "28 sc",
                    "28 sc",
                    "(2 sc, dec) x7",
                    "(sc, dec) x7",
                    "Isi dengan dakron/kapas",
                    "7 dec",
                    "Potong benang dan amankan ujungnya"
                )
            )
        )
        var successCount = 0
        var failCount = 0
        for (pattern in patterns) {
            db.collection("pattern")
                .add(pattern)
                .addOnSuccessListener {
                    successCount++
                    if (successCount + failCount == patterns.size) {
                        Toast.makeText(this, "Pattern insert success: $successCount, failed: $failCount", Toast.LENGTH_SHORT).show()
                    }
                }
                .addOnFailureListener {
                    failCount++
                    if (successCount + failCount == patterns.size) {
                        Toast.makeText(this, "Pattern insert success: $successCount, failed: $failCount", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }
}