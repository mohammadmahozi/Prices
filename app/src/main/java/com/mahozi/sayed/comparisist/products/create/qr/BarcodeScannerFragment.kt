package com.mahozi.sayed.comparisist.products.create.qr
import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.util.Size

import android.view.*
import android.widget.Toast
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageCapture
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.mahozi.sayed.comparisist.databinding.FragmentQrScanBinding
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


class BarcodeScannerFragment : Fragment() {

    private var _binding: FragmentQrScanBinding? = null
    private val binding get() = _binding!!


    private lateinit var cameraExecutor: ExecutorService


    private lateinit var previewView: PreviewView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Request camera permissions
        if (allPermissionsGranted()) {
            startCamera()
        }

        else {
            ActivityCompat.requestPermissions(
                requireActivity(), REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS
            )
        }

        cameraExecutor = Executors.newSingleThreadExecutor()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentQrScanBinding.inflate(inflater, container, false)


        previewView = binding.previewView


        return binding.root

    }


    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())

        cameraProviderFuture.addListener(Runnable {
            // Used to bind the lifecycle of cameras to the lifecycle owner
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

            // Preview
            val preview = Preview.Builder().build().also {

                it.setSurfaceProvider(previewView.surfaceProvider)
            }


            val imageCapture = ImageCapture.Builder().build()


            // Select back camera as a default
            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA


            val imageAnalysis = ImageAnalysis.Builder().setTargetResolution(Size(1280, 720))
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST).build()


            val analyzer: ImageAnalysis.Analyzer = BarcodeAnalyzer(object : BarcodeScannerListener {
                override fun onScanned(result: String) {
                    requireActivity().runOnUiThread {

                        val navController = binding.root.findNavController()
                        navController.previousBackStackEntry?.savedStateHandle?.set(SCANNED_BARCODE_KEY, result)
                        navController.popBackStack()


                        imageAnalysis.clearAnalyzer()

                        cameraProvider.unbindAll()

                    }
                }
            })


            imageAnalysis.setAnalyzer(cameraExecutor, analyzer)

            //preview.setSurfaceProvider(binding.previewView.surfaceProvider)

            cameraProvider.bindToLifecycle(this, cameraSelector, imageAnalysis, preview)


            try {
                // Unbind use cases before rebinding
                cameraProvider.unbindAll()

                // Bind use cases to camera
                cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageAnalysis, imageCapture)

            }

            catch(exc: Exception) {
                Log.e(TAG, "Use case binding failed", exc)
            }

        }, ContextCompat.getMainExecutor(requireContext()))
    }


    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(
            requireActivity().baseContext, it) == PackageManager.PERMISSION_GRANTED
    }



    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>, grantResults:
        IntArray) {
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (allPermissionsGranted()) {
                startCamera()
            } else {
                Toast.makeText(requireContext(),
                    "Permissions not granted by the user.",
                    Toast.LENGTH_SHORT).show()
                requireActivity().finish()
            }
        }
    }


    companion object {
        private const val TAG = "QRScanFragment"
        private const val REQUEST_CODE_PERMISSIONS = 10
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)

        const val SCANNED_BARCODE_KEY = "scannedBaCode"

    }


}








