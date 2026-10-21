package id.ac.polbeng.triwahyuningsih.utsmobileii.ui

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.gson.Gson
import id.ac.polbeng.triwahyuningsih.utsmobileii.databinding.ActivityDetailProductBinding
import id.ac.polbeng.triwahyuningsih.utsmobileii.helpers.Config
import id.ac.polbeng.triwahyuningsih.utsmobileii.model.ProductItem

class DetailProductActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailProductBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val json = intent.getStringExtra(Config.EXTRA_PRODUCT)
        val product = Gson().fromJson(json, ProductItem::class.java)

        // Tampilkan gambar produk utama dari array 'images'
        if (product.images.isNotEmpty()) {
            Glide.with(this)
                .load(product.images[0])
                .into(binding.imgProduct)
        }

        binding.tvDetailTitle.text = product.title
        binding.tvDetailCategory.text = "Category: ${product.category}"
        binding.tvDetailPrice.text = "Price: $${product.price}"
        binding.tvDetailStockStatus.text = "Stock: ${product.stock} (${product.availabilityStatus})"
        binding.tvDetailDimensions.text = "Dimensions: ${product.dimensions.width} x ${product.dimensions.height} x ${product.dimensions.depth}"
        binding.tvDetailTags.text = "Tags: ${product.tags.joinToString(", ")}"
        binding.tvDetailDescription.text = product.description

        // Default tampilkan 1 review teratas
        if (product.reviews.isNotEmpty()) {
            val topReview = product.reviews[0]
            binding.tvTopReview.text = "${topReview.reviewerName} (${topReview.rating}★):\n\"${topReview.comment}\""
        } else {
            binding.tvTopReview.text = "Belum ada review."
        }

        // Tampilkan Dialog untuk melihat seluruh review
        binding.btnAllReviews.setOnClickListener {
            val allReviewsText = product.reviews.joinToString("\n\n") {
                "${it.reviewerName} (${it.rating}★)\nDate: ${it.date}\n\"${it.comment}\""
            }
            AlertDialog.Builder(this)
                .setTitle("Semua Review Product")
                .setMessage(allReviewsText)
                .setPositiveButton("Tutup", null)
                .show()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}