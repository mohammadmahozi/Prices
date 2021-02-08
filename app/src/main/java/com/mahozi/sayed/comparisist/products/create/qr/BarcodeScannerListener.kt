package com.mahozi.sayed.comparisist.products.create.qr

interface BarcodeScannerListener {
    abstract fun onScanned(result: String)
}