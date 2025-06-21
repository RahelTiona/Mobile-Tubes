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

        // Set up Insert Materials button click listener
        findViewById<android.widget.Button>(R.id.insertMaterialsButton).setOnClickListener {
            insertMaterialsToFirestore()
        }

        // Set up Insert Stitches button click listener
        findViewById<android.widget.Button>(R.id.insertStitchesButton).setOnClickListener {
            insertStitchesToFirestore()
        }

        // Set up Insert Pattern button click listener
        findViewById<android.widget.Button>(R.id.insertPatternButton).setOnClickListener {
            insertPatternToFirestore()
        }
    }

    private fun insertMaterialsToFirestore() {
        val db = FirebaseFirestore.getInstance()
        val materials = listOf(
            mapOf(
                "type" to "hakpen",
                "name" to "Hakpen Aluminium",
                "image" to "hakpen_aluminium",
                "texture" to "ringan, licin, tidak lengket di benang",
                "project" to "cocok untuk semua jenis benang, terutama benang halus",
                "ukuran_umum" to "2.0 mm – 10.0 mm"
            ),
            mapOf(
                "type" to "hakpen",
                "name" to "Hakpen Plastik",
                "image" to "hakpen_plastik",
                "texture" to "ringan, agak lentur, tidak terlalu licin",
                "project" to "benang besar seperti T-shirt yarn atau chenille",
                "ukuran_umum" to "6.0 mm – 15.0 mm"
            ),
            mapOf(
                "type" to "hakpen",
                "name" to "Hakpen Baja",
                "image" to "hakpen_baja",
                "texture" to "kokoh, kecil",
                "project" to "rautan benang tipis seperti renda atau benang katun tipis",
                "ukuran_umum" to "0.5 mm – 3.5 mm"
            ),
            mapOf(
                "type" to "hakpen",
                "name" to "Hakpen Kayu",
                "image" to "hakpen_kayu",
                "texture" to "hangat di tangan, sedikit kasar (tidak licin)",
                "project" to "cocok untuk pemula agar benang tidak mudah meluncur",
                "ukuran_umum" to "2.5 mm – 10 mm"
            ),
            mapOf(
                "type" to "hakpen",
                "name" to "Hakpen Pegangan Karet / Silikon",
                "image" to "hakpen_pegangan_karet_silikon",
                "texture" to "nyaman digenggam, tidak licin di tangan",
                "project" to "semua proyek – mengurangi pegal saat merajut lama",
                "ukuran_umum" to "2.0 mm – 6.0 mm"
            ),
            mapOf(
                "type" to "jarum_tapestri",
                "name" to "Jarum Tapestri",
                "image" to "jarum_tapestri",
                "texture" to "tumpul, tidak merusak serat benang",
                "project" to "merapikan benang, menyambung bagian rajutan, menambahkan detail wajah/hiasan",
                "ukuran_umum" to "-"
            ),
            mapOf(
                "type" to "stitch_marker",
                "name" to "Stitch marker",
                "image" to "stitch_marker",
                "texture" to "plastik kecil, warna-warni",
                "project" to "menandai titik penting dalam rajutan, seperti increase/decrease atau awal baris",
                "ukuran_umum" to "-"
            ),
            mapOf(
                "type" to "benang",
                "name" to "Milk Cotton",
                "image" to "milk_cotton",
                "texture" to "lembut, halus",
                "project" to "Amigurumi, topi",
                "hakpen" to "2.0 mm - 4.00 mm"
            ),
            mapOf(
                "type" to "benang",
                "name" to "Chenille Yarn0020",
                "image" to "chenille_yarn0020",
                "texture" to "sangat lembut, tebal",
                "project" to "boneka, selimut bayi, bantal",
                "hakpen" to "5.0 mm – 8.0 mm"
            ),
            mapOf(
                "type" to "benang",
                "name" to "Velvet yarn",
                "image" to "velvet_yarn",
                "texture" to "sangat lembut, berbulu",
                "project" to "boneka besar, bantal, selimut bayi",
                "hakpen" to "6.0 mm – 8.0 mm"
            ),
            mapOf(
                "type" to "benang",
                "name" to "Acrylic yarn",
                "image" to "acrylic_yarn",
                "texture" to "lembut, agak berbulu",
                "project" to "selimut, sweater, syal",
                "hakpen" to "4.0 mm – 6.0 mm"
            ),
            mapOf(
                "type" to "benang",
                "name" to "Polyester",
                "image" to "polyester",
                "texture" to "licin, mengkilap",
                "project" to "tas, dompet, taplak",
                "hakpen" to "2.5 mm – 4.5 mm"
            ),
            mapOf(
                "type" to "benang",
                "name" to "T-shirt yarn",
                "image" to "t-shirt_yarn",
                "texture" to "tebal, elastis, sedikit kasar",
                "project" to "tas, keranjang, tatakan, dekorasi rumah",
                "hakpen" to "8.0 mm – 12.0 mm"
            )
        )
        var successCount = 0
        var failCount = 0
        for (material in materials) {
            db.collection("materials")
                .add(material)
                .addOnSuccessListener {
                    successCount++
                    if (successCount + failCount == materials.size) {
                        Toast.makeText(this, "Insert success: $successCount, failed: $failCount", Toast.LENGTH_SHORT).show()
                    }
                }
                .addOnFailureListener {
                    failCount++
                    if (successCount + failCount == materials.size) {
                        Toast.makeText(this, "Insert success: $successCount, failed: $failCount", Toast.LENGTH_SHORT).show()
                    }
                }
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