package com.example.bubble.meizi.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager

import com.example.bubble.meizi.R
import com.example.bubble.meizi.database.FavImgDatabase
import com.example.bubble.meizi.model.Hit
import com.example.bubble.meizi.network.MeiziNetwork
import com.example.bubble.meizi.repository.DataRepository
import com.example.bubble.meizi.viewmodel.MeiziViewModel
import kotlinx.android.synthetic.main.fragment_favorite.*

/**
 * A simple [Fragment] subclass.
 */
class FavoriteFragment : Fragment() {
    private lateinit var meiziViewModel: MeiziViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val dataRepository = DataRepository(MeiziNetwork.instance.getPixabayService(), FavImgDatabase.getDatabase(requireActivity().applicationContext)!!.favImgDao)
        meiziViewModel = ViewModelProvider(requireActivity(), MeiziViewModel.MeiziViewModelFactory(dataRepository)).get(MeiziViewModel::class.java)

        meiziViewModel.favHits.observe(viewLifecycleOwner, Observer {
            favRv.adapter = MeiziAdapter(requireActivity(), it.toMutableList(), meiziViewModel.hitMap, object: MeiziAdapter.OnItemClickListener{
                override fun onClick(hit: Hit, position: Int) {
                    meiziViewModel.hitMap[hit.id] = false
                    meiziViewModel.deleteFavImage(hit)
                }
            })
            favRv.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        })
    }

    companion object {
        fun newInstance() : FavoriteFragment {
            Log.d("Meizi", "FavoriteFragment created")
            return FavoriteFragment()
        }
    }

}
