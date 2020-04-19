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
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.bubble.meizi.R
import com.example.bubble.meizi.database.FavImgDatabase
import com.example.bubble.meizi.model.Hit
import com.example.bubble.meizi.network.MeiziNetwork
import com.example.bubble.meizi.repository.DataRepository
import com.example.bubble.meizi.viewmodel.MeiziViewModel
import kotlinx.android.synthetic.main.fragment_image_list.*

class ImageListFragment : Fragment() {
    private lateinit var meiziViewModel: MeiziViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val dataRepository = DataRepository(MeiziNetwork.instance.getPixabayService(), FavImgDatabase.getDatabase(requireActivity().applicationContext)!!.favImgDao())
        meiziViewModel = ViewModelProvider(requireActivity(), MeiziViewModel.MeiziViewModelFactory(dataRepository)).get(MeiziViewModel::class.java)

        val staggeredGridLayoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        meiziRv.layoutManager = staggeredGridLayoutManager
        meiziViewModel.content.observe(requireActivity(), Observer {
            progressBar.visibility = View.INVISIBLE
            meiziRv.adapter = MeiziAdapter(requireContext(), it.toMutableList(), object: MeiziAdapter.OnItemClickListener{
                override fun onClick(hit: Hit, position: Int) {
                    meiziViewModel.saveFavImage(hit)
                    Toast.makeText(requireActivity(), "Item ${position} is saved", Toast.LENGTH_LONG).show()
                }
            })
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