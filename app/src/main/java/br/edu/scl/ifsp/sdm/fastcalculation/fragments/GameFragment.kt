package br.edu.scl.ifsp.sdm.fastcalculation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.edu.scl.ifsp.sdm.fastcalculation.data.Settings
import br.edu.scl.ifsp.sdm.fastcalculation.databinding.FragmentGameBinding
import br.edu.scl.ifsp.sdm.fastcalculation.utils.Extras.EXTRA_SETTINGS

class GameFragment : Fragment() {
    private lateinit var fragmentGameBinding: FragmentGameBinding
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
        fragmentGameBinding = FragmentGameBinding.inflate(layoutInflater, container, false)

        val onClickListener = View.OnClickListener {
        }

        fragmentGameBinding.apply {
            alternativeOneBt.setOnClickListener(onClickListener)
            alternativeTwoBt.setOnClickListener(onClickListener)
            alternativeThreeBt.setOnClickListener(onClickListener)
        }

        return fragmentGameBinding.root
    }

    companion object {
        @JvmStatic
        fun newInstance(settings: Settings) =
            GameFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(EXTRA_SETTINGS, settings)
                }
            }
    }
}
