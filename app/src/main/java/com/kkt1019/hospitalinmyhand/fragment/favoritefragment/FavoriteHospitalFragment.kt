package com.kkt1019.hospitalinmyhand.fragment.favoritefragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.kkt1019.hospitalinmyhand.adapter.favoriteadapter.FavoriteHospitalAdapter
import com.kkt1019.hospitalinmyhand.databinding.FragmentFavoirteHospitalBinding
import com.kkt1019.hospitalinmyhand.roomdatabase.hospital.HospitalDataBase
import com.kkt1019.hospitalinmyhand.viewmodel.SharedViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class FavoriteHospitalFragment : Fragment() {

    private var binding: FragmentFavoirteHospitalBinding? = null
    private lateinit var adapter: FavoriteHospitalAdapter
    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoirteHospitalBinding.inflate(inflater, container, false)
        setupRecyclerView()
        loadFavoriteHospitals()

        return binding?.root
    }

    private fun setupRecyclerView() {
        adapter = FavoriteHospitalAdapter(requireContext(), emptyList(), sharedViewModel, childFragmentManager)
        binding?.hospitalFavRecycler?.adapter = adapter
        binding?.hospitalFavRecycler?.layoutManager = LinearLayoutManager(context)
    }

    private fun loadFavoriteHospitals() {
        val dao = HospitalDataBase.getDatabase(requireContext()).hospitalDao()
        CoroutineScope(Dispatchers.IO).launch {
            val hospitals = dao.getAll()
            withContext(Dispatchers.Main) {
                adapter.updateData(hospitals)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}