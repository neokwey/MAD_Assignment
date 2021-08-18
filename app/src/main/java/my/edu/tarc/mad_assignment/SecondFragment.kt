package my.edu.tarc.mad_assignment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import my.edu.tarc.mad_assignment.databinding.FragmentSecondBinding

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as AppCompatActivity).supportActionBar?.hide()
        binding.buttonSecond.setOnClickListener {
            findNavController().navigate(R.id.action_LoginFragment_to_MainpageFragment)
        }
        binding.txt4getPW.setOnClickListener {
            findNavController().navigate(R.id.action_LoginFragment_to_forgetPWFragment)
        }
        binding.txtReg.setOnClickListener {
            findNavController().navigate(R.id.action_LoginFragment_to_registerFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}