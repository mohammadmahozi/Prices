package com.mahozi.sayed.comparisist.products.qr

interface ScanningResultListener {
    abstract fun onScanned(result: String)
}