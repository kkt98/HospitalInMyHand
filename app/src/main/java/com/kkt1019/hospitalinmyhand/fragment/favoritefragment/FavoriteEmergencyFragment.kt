package com.kkt1019.hospitalinmyhand.fragment.favoritefragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.kkt1019.hospitalinmyhand.adapter.favoriteadapter.FavoriteEmergencyAdapter
import com.kkt1019.hospitalinmyhand.databinding.FragmentFavoriteEmergencyBinding
import com.kkt1019.hospitalinmyhand.roomdatabase.emergency.EmergencyDataBase
import com.kkt1019.hospitalinmyhand.roomdatabase.hospital.HospitalDataBase
import com.kkt1019.hospitalinmyhand.viewmodel.SharedViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@AndroidEntryPoint
class FavoriteEmergencyFragment : Fragment() {

    private var binding: FragmentFavoriteEmergencyBinding? = null
    private lateinit var adapter: FavoriteEmergencyAdapter
    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoriteEmergencyBinding.inflate(inflater, container, false)

        setupRecyclerView()
        loadFavoriteHospitals()

        return binding?.root
    }

    private fun setupRecyclerView() {
        adapter = FavoriteEmergencyAdapter(requireContext(), emptyList(), sharedViewModel, childFragmentManager)
        binding?.emergencyFavRecycler?.adapter = adapter
        binding?.emergencyFavRecycler?.layoutManager = LinearLayoutManager(context)
    }

    private fun loadFavoriteHospitals() {
        val dao = EmergencyDataBase.getDatabase(requireContext()).emergencyDao()
        CoroutineScope(Dispatchers.IO).launch {
            val emergency = dao.getAll()
            withContext(Dispatchers.Main) {
                adapter.updateData(emergency)
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}