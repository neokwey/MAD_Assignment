package my.edu.tarc.mad_assignment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import my.edu.tarc.mad_assignment.databinding.FragmentNewApplicationBinding
import my.edu.tarc.mad_assignment.databinding.FragmentRegisterBinding

class NewApplicationFragment : Fragment() {
    private var _binding: FragmentNewApplicationBinding? = null
    private val binding get() = _binding!!

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
       /* binding.buttonNewApply.setOnClickListener {
            findNavController().navigate(R.id.action_newApplicationFragment_to_MainpageFragment)
        }*/
    }

    companion object {

    }
}