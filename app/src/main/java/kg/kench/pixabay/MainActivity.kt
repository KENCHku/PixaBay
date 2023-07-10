package kg.kench.pixabay

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import kg.kench.pixabay.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val adapter by lazy {
        PixaAdapter()
    }
    private var page = 1
    private var loadPage = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initClicks()
    }

    private fun initClicks() = with(binding) {
        recyclerViewImages.adapter = adapter
        recyclerViewImages.addOnScrollListener(object :
            Pagination(recyclerViewImages.layoutManager as LinearLayoutManager) {
            override fun loadItems() {
                page++
                searchImage()
            }

            override fun lastPage() = false

            override fun isLoading() = loadPage

        })

        btnSearch.setOnClickListener {
            adapter.arrayList.clear()
            searchImage()
        }
        btnChangePage.setOnClickListener {
            page++
            searchImage()
        }
    }

    private fun searchImage() = with(binding) {
        loadPage = true
        RetrofitService.api.searchImage(etSearch.text.toString(), page = page)
            .enqueue(object : Callback<PixaModel> {
                override fun onResponse(call: Call<PixaModel>, response: Response<PixaModel>) {
                    loadPage = false
                    if (response.isSuccessful) {
                        val hits = response.body()?.hits
                        if (hits != null) {
                            adapter.addImages(hits)
                        }
                    } else {
                        Toast.makeText(this@MainActivity, "${response.code()}", Toast.LENGTH_SHORT)
                            .show()
                        etSearch.text.clear()
                    }
                }

                override fun onFailure(call: Call<PixaModel>, t: Throwable) {
                    loadPage = false
                }

            })
    }
}