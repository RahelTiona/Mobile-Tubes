package com.example.crocha

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

class StitchTutorialFragment : Fragment() {
    private lateinit var stitchName: String
    private val db = FirebaseFirestore.getInstance()

    companion object {
        private const val ARG_STITCH_NAME = "stitch_name"

        fun newInstance(stitchName: String): StitchTutorialFragment {
            val fragment = StitchTutorialFragment()
            val args = Bundle()
            args.putString(ARG_STITCH_NAME, stitchName)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            stitchName = it.getString(ARG_STITCH_NAME) ?: ""
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_stitch_tutorial, container, false)
        
        fetchStitchData(view)
        
        return view
    }

    private fun fetchStitchData(view: View) {
        db.collection("stitches")
            .whereEqualTo("name", stitchName)
            .get()
            .addOnSuccessListener { documents ->
                if (!documents.isEmpty) {
                    val stitchDoc = documents.documents[0]
                    val link = stitchDoc.getString("link") ?: ""
                    val steps = stitchDoc.get("steps") as? List<String> ?: emptyList()
                    
                    setupYouTubeVideo(view, link)
                    setupSteps(view, steps)
                }
            }
            .addOnFailureListener { exception ->
                Log.w("StitchTutorialFragment", "Error getting stitch data: ", exception)
            }
    }

    private fun setupYouTubeVideo(view: View, videoUrl: String) {
        val webView = view.findViewById<WebView>(R.id.youtubeWebView)
        
        // Extract video ID from YouTube URL
        val videoId = extractVideoId(videoUrl)
        if (videoId.isNotEmpty()) {
            val embedUrl = "https://www.youtube.com/embed/$videoId"
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
            
            webView.settings.javaScriptEnabled = true
            webView.webViewClient = WebViewClient()
            webView.loadData(htmlContent, "text/html", "UTF-8")
        }
    }

    private fun setupSteps(view: View, steps: List<String>) {
        val stepsContainer = view.findViewById<LinearLayout>(R.id.stepsContainer)
        stepsContainer.removeAllViews()
        
        steps.forEachIndexed { index, step ->
            val stepView = TextView(requireContext())
            stepView.text = "${index + 1}. $step"
            stepView.textSize = 16f
            stepView.setPadding(0, 8, 0, 8)
            stepsContainer.addView(stepView)
        }
    }

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