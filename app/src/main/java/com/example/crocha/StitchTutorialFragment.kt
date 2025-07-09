// 📦 Mengimpor library yang diperlukan
package com.example.crocha

// 🔧 Impor library Android dan Firebase
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.firebase.firestore.FirebaseFirestore

// Definisi Fragment yang menampilkan tutorial stitch
class StitchTutorialFragment : Fragment() {

    // Nama stitch yang akan diambil datanya dari Firestore
    private lateinit var stitchName: String

    // Inisialisasi koneksi ke database Firestore
    private val db = FirebaseFirestore.getInstance()

    companion object {
        private const val ARG_STITCH_NAME = "stitch_name"

        // Membuat instance baru fragment dengan parameter nama stitch
        fun newInstance(stitchName: String): StitchTutorialFragment {
            val fragment = StitchTutorialFragment()
            val args = Bundle()
            args.putString(ARG_STITCH_NAME, stitchName)
            fragment.arguments = args
            return fragment
        }
    }

    // Mengambil data "stitchName" dari arguments saat fragment dibuat
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            stitchName = it.getString(ARG_STITCH_NAME) ?: ""
        }
    }

    // Menghubungkan fragment dengan layout XML-nya dan mulai ambil data dari Firestore
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_stitch_tutorial, container, false)

        // Panggil fungsi untuk ambil data dari Firestore
        fetchStitchData(view)

        return view
    }

    //Fungsi untuk ambil data tutorial berdasarkan nama stitch
    private fun fetchStitchData(view: View) {
        db.collection("stitches")
            .whereEqualTo("name", stitchName) // Cari berdasarkan nama
            .get()
            .addOnSuccessListener { documents ->
                if (!documents.isEmpty) {
                    val stitchDoc = documents.documents[0]

                    // Ambil link YouTube dan langkah-langkah
                    val link = stitchDoc.getString("link") ?: ""
                    val steps = stitchDoc.get("steps") as? List<String> ?: emptyList()

                    // Tampilkan video YouTube dan langkahnya
                    setupYouTubeVideo(view, link)
                    setupSteps(view, steps)
                }
            }
            .addOnFailureListener { exception ->
                Log.w("StitchTutorialFragment", "Error getting stitch data: ", exception)
            }
    }

    // Menampilkan video YouTube ke dalam WebView
    private fun setupYouTubeVideo(view: View, videoUrl: String) {
        val webView = view.findViewById<WebView>(R.id.youtubeWebView)

        //Ambil ID video dari URL
        val videoId = extractVideoId(videoUrl)

        if (videoId.isNotEmpty()) {
            val embedUrl = "https://www.youtube.com/embed/$videoId"

            // HTML iframe untuk tampilkan video
            val htmlContent = """
                <html>
                <body style="margin:0; padding:0;">
                    <iframe width="100%" height="100%" 
                            src="$embedUrl" 
                            frameborder="0" 
                            allowfullscreen>
                    </iframe>
                </body>
                </html>
            """.trimIndent()

            // Setel WebView agar bisa jalankan JavaScript dan tampilkan video
            webView.settings.javaScriptEnabled = true
            webView.webViewClient = WebViewClient()
            webView.loadData(htmlContent, "text/html", "UTF-8")
        }
    }

    // Menampilkan langkah-langkah sebagai daftar TextView
    private fun setupSteps(view: View, steps: List<String>) {
        val stepsContainer = view.findViewById<LinearLayout>(R.id.stepsContainer)
        stepsContainer.removeAllViews() // Hapus dulu isi sebelumnya

        // Tambahkan TextView untuk setiap langkah
        steps.forEachIndexed { index, step ->
            val stepView = TextView(requireContext())
            stepView.text = "${index + 1}. $step"
            stepView.textSize = 16f
            stepView.setPadding(0, 8, 0, 8)
            stepsContainer.addView(stepView)
        }
    }

    // Ekstrak ID video dari link YouTube
    private fun extractVideoId(url: String): String {
        return try {
            if (url.contains("youtube.com/watch?v=")) {
                url.split("v=")[1].split("&")[0]
            } else if (url.contains("youtu.be/")) {
                url.split("youtu.be/")[1].split("?")[0]
            } else {
                ""
            }
        } catch (e: Exception) {
            ""
        }
    }
}
