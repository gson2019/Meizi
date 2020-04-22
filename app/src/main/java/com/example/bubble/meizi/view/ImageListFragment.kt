package com.example.bubble.meizi.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.bubble.meizi.R
import com.example.bubble.meizi.database.FavImgDatabase
import com.example.bubble.meizi.model.Hit
import com.example.bubble.meizi.model.NetResponse
import com.example.bubble.meizi.network.MeiziNetwork
import com.example.bubble.meizi.repository.DataRepository
import com.example.bubble.meizi.viewmodel.MeiziViewModel
import kotlinx.android.synthetic.main.fragment_favorite.*
import kotlinx.android.synthetic.main.fragment_image_list.*

class ImageListFragment : Fragment() {
    private lateinit var meiziViewModel: MeiziViewModel
    private lateinit var adapter: MeiziAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpViewModel()
        setUpUI()
        setObserver()
    }

    private fun retrieveList(hits: List<Hit>) {
        adapter.apply {
            addHits(hits)
            notifyDataSetChanged()
        }
    }

    private fun setUpViewModel() {
        val dataRepository = DataRepository(MeiziNetwork.instance.getPixabayService(), FavImgDatabase.getDatabase(requireActivity().applicationContext)!!.favImgDao)
        meiziViewModel = ViewModelProvider(requireActivity(), MeiziViewModel.MeiziViewModelFactory(dataRepository)).get(MeiziViewModel::class.java)
    }

    private fun setUpUI() {
        val staggeredGridLayoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        meiziRv.layoutManager = staggeredGridLayoutManager
        adapter = MeiziAdapter(requireActivity(), mutableListOf<Hit>(), meiziViewModel.hitMap, object: MeiziAdapter.OnItemClickListener{
            override fun onClick(hit: Hit, position: Int) {
                if (!meiziViewModel.hitMap[hit.id]!!) {
                    meiziViewModel.saveFavImage(hit)
                } else {
                    meiziViewModel.deleteFavImage(hit)
                }
                meiziViewModel.setHitSavedState(hit.id)
            }
        })
        meiziRv.adapter = adapter
    }

    private fun setObserver() {
        meiziViewModel.content.observe(requireActivity(), Observer {
            it.let {
                resource ->
                when(resource.status) {
                    NetResponse.Status.SUCCESS -> {
                        progressBar.visibility = View.GONE
                        resource.data?.let{
                            hits -> retrieveList(hits)
                        }
                    }
                    NetResponse.Status.ERROR -> {
                        progressBar.visibility = View.GONE
                        Toast.makeText(requireActivity(), it.message, Toast.LENGTH_LONG).show()
                    }
                    NetResponse.Status.LOADING -> {
                        progressBar.visibility = View.VISIBLE
                    }
                }
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_image_list, container, false)
    }

    companion object{
        fun newInstance() : ImageListFragment {
            Log.d("Meizi", "ImageList Fragment created")
            return ImageListFragment()
        }
    }
}