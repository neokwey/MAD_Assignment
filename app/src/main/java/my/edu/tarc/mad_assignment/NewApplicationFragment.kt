package my.edu.tarc.mad_assignment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import my.edu.tarc.mad_assignment.databinding.FragmentNewApplicationBinding
//import my.edu.tarc.mad_assignment.databinding.FragmentRegisterBinding

class NewApplicationFragment : Fragment() {
    private var _binding: FragmentNewApplicationBinding? = null
    private val binding get() = _binding!!
    private val vehicleViewModel: VehicleViewModel by activityViewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNewApplicationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as AppCompatActivity).supportActionBar?.show()
        binding.fabAdd.setOnClickListener {
            val intent = Intent(activity, Vehicle_add::class.java)
            startActivity(intent)
        }
/*val adapter = RecyclerAdapter()

//adapter.setVehicle(vehicleViewModel.vehicles)

var username: String = "c1"
vehicleViewModel.select(username)

//vehicleViewModel.

binding.listViewVehicle.adapter = adapter
*/
    }

    companion object {

    }
}