package id.ac.polbeng.triwahyuningsih.utsmobileii.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import id.ac.polbeng.triwahyuningsih.utsmobileii.databinding.ActivityProfileBinding

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Profil Developer"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Mengatur foto profil dari gambar drawable default atau via Glide
        Glide.with(this)
            .load(android.R.drawable.ic_menu_gallery)
            .into(binding.imgProfile)

        // Silakan sesuaikan isi data diri di bawah ini jika diperlukan
        binding.tvDeveloperName.text = "Tri Wahyuningsih"
        binding.tvDeveloperEmail.text = "triwahyuningsih@example.com"
        binding.tvPhone.text = "081234567890"
        binding.tvProdi.text = "D4 Rekayasa Perangkat Lunak"
        binding.tvJurusan.text = "Teknik Informatika"
        binding.tvKampus.text = "Politeknik Negeri Bengkalis"
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}