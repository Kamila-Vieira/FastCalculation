package br.edu.scl.ifsp.sdm.fastcalculation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.edu.scl.ifsp.sdm.fastcalculation.R
import br.edu.scl.ifsp.sdm.fastcalculation.data.Settings
import br.edu.scl.ifsp.sdm.fastcalculation.databinding.FragmentWelcomeBinding
import br.edu.scl.ifsp.sdm.fastcalculation.utils.Extras.EXTRA_SETTINGS

class WelcomeFragment : Fragment() {
    private lateinit var fragmentWelcomeBinding: FragmentWelcomeBinding
    private lateinit var settings: Settings

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            settings = it.getParcelable<Settings>(EXTRA_SETTINGS) ?: Settings()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentWelcomeBinding = FragmentWelcomeBinding
            .inflate(layoutInflater, container, false)
            .apply {
                "${getString(R.string.welcome)}, ${settings.playerName}!".also {
                    welcomeTv.text = it
                }
            }
        return fragmentWelcomeBinding.root
    }

    companion object {
        @JvmStatic
        fun newInstance(settings: Settings) =
            WelcomeFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(EXTRA_SETTINGS, settings)
                }
            }
    }
}