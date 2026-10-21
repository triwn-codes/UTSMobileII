package id.ac.polbeng.triwahyuningsih.utsmobileii.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import id.ac.polbeng.triwahyuningsih.utsmobileii.R
import id.ac.polbeng.triwahyuningsih.utsmobileii.adapter.ProductAdapter
import id.ac.polbeng.triwahyuningsih.utsmobileii.databinding.ActivityMainBinding
import id.ac.polbeng.triwahyuningsih.utsmobileii.helpers.Config
import id.ac.polbeng.triwahyuningsih.utsmobileii.model.ProductItem
import id.ac.polbeng.triwahyuningsih.utsmobileii.response.ProductResponse
import id.ac.polbeng.triwahyuningsih.utsmobileii.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    companion object {
        private const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Daftar Produk"

        binding.rvProducts.layoutManager = LinearLayoutManager(this)

        getProductsList()

        // Event Klik pada Menu Bottom Navigation
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_products -> true
                R.id.navigation_profile -> {
                    startActivity(Intent(this, ProfileActivity::class.java))
                    true
                }
                else -> false
            }
        }
    }

    private fun getProductsList() {
        showLoading(true)
        val client = ApiConfig.getApiService().getProducts(limit = 25)
        client.enqueue(object : Callback<ProductResponse> {
            override fun onResponse(
                call: Call<ProductResponse>,
                response: Response<ProductResponse>
            ) {
                showLoading(false)
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        setProductData(responseBody.products)
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<ProductResponse>, t: Throwable) {
                showLoading(false)
                Log.e(TAG, "onFailure: ${t.message}")
                Toast.makeText(this@MainActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun setProductData(products: List<ProductItem>) {
        val adapter = ProductAdapter()
        adapter.submitList(products)
        binding.rvProducts.adapter = adapter

        adapter.setOnItemClickCallback(object : ProductAdapter.OnItemClickCallback {
            override fun onItemClicked(data: ProductItem) {
                val json = Gson().toJson(data)
                val moveIntent = Intent(this@MainActivity, DetailProductActivity::class.java)
                moveIntent.putExtra(Config.EXTRA_PRODUCT, json)
                startActivity(moveIntent)
            }
        })
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}