package com.kkt1019.hospitalinmyhand.fragment.favoritefragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.kkt1019.hospitalinmyhand.adapter.favoriteadapter.FavoriteEmergencyAdapter
import com.kkt1019.hospitalinmyhand.databinding.FragmentFavoriteEmergencyBinding
import com.kkt1019.hospitalinmyhand.roomdatabase.emergency.EmergencyDataBase
import com.kkt1019.hospitalinmyhand.roomdatabase.hospital.HospitalDataBase
import com.kkt1019.hospitalinmyhand.viewmodel.RoomViewModel
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
    private val roomViewModel: RoomViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoriteEmergencyBinding.inflate(inflater, container, false)

        setupRecyclerView()
        loadFavoriteHospitals()

        //즐겨찾기 삭제시 toast
        roomViewModel.uiMessage.observe(viewLifecycleOwner){
            Toast.makeText(requireContext(), "$it", Toast.LENGTH_SHORT).show()
        }

        return binding?.root
    }

    private fun setupRecyclerView() {
        adapter = FavoriteEmergencyAdapter(roomViewModel, emptyList(), sharedViewModel, childFragmentManager, binding!!.emergencyFavRecycler)
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