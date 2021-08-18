package my.edu.tarc.mad_assignment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import my.edu.tarc.mad_assignment.databinding.FragmentFirstBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.layLogout.setOnClickListener {
            findNavController().navigate(R.id.action_MainpageFragment_to_LoginFragment)
        }
        binding.layApplication.setOnClickListener {
            findNavController().navigate(R.id.action_MainpageFragment_to_newApplicationFragment)
        }
        binding.layMyProfile.setOnClickListener {
            findNavController().navigate(R.id.action_MainpageFragment_to_profileFragment)
        }
        binding.layPayment.setOnClickListener {
            findNavController().navigate(R.id.action_MainpageFragment_to_paymentFragment)
        }
        binding.layReferral.setOnClickListener {
            findNavController().navigate(R.id.action_MainpageFragment_to_referralFragment)
        }
        binding.layReward.setOnClickListener {
            findNavController().navigate(R.id.action_MainpageFragment_to_rewardFragment)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}