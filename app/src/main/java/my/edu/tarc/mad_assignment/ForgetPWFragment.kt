package my.edu.tarc.mad_assignment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import my.edu.tarc.mad_assignment.databinding.FragmentFirstBinding
import my.edu.tarc.mad_assignment.databinding.FragmentForgetPWBinding

class ForgetPWFragment : Fragment() {
    private var _binding: FragmentForgetPWBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentForgetPWBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as AppCompatActivity).supportActionBar?.show()
        binding.button4getPW.setOnClickListener {
            findNavController().navigate(R.id.action_forgetPWFragment_to_LoginFragment)
        }
    }

    companion object {

    }
}