package br.edu.scl.ifsp.sdm.fastcalculation.fragments

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import br.edu.scl.ifsp.sdm.fastcalculation.ResultActivity
import br.edu.scl.ifsp.sdm.fastcalculation.data.GameResult
import br.edu.scl.ifsp.sdm.fastcalculation.data.Settings
import br.edu.scl.ifsp.sdm.fastcalculation.databinding.FragmentGameBinding
import br.edu.scl.ifsp.sdm.fastcalculation.utils.CalculationGame
import br.edu.scl.ifsp.sdm.fastcalculation.utils.Extras.EXTRA_RESULT
import br.edu.scl.ifsp.sdm.fastcalculation.utils.Extras.EXTRA_SETTINGS

class GameFragment : Fragment() {
    private lateinit var fragmentGameBinding: FragmentGameBinding
    private lateinit var settings: Settings
    private lateinit var calculationGame: CalculationGame
    private var currentRound: CalculationGame.Round? = null
    private var startRoundTime = 0L
    private var totalGameTime = 0L
    private var hits = 0
    private var playedRounds = 0
    private var roundDeadlineHandler = object : Handler(Looper.getMainLooper()){
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            totalGameTime += settings.roundsInterval
            play()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            settings = it.getParcelable<Settings>(EXTRA_SETTINGS) ?: Settings()
        }
        calculationGame = CalculationGame(settings.rounds)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentGameBinding = FragmentGameBinding.inflate(layoutInflater, container, false)

        val onClickListener = View.OnClickListener {
            val value = (it as Button).text.toString().toInt()
            if (value == currentRound?.answer){
                totalGameTime += System.currentTimeMillis() - startRoundTime
                hits++
            } else {
                totalGameTime += settings.roundsInterval
                hits--
            }
            roundDeadlineHandler.removeMessages(MSG_ROUND_DEADLINE)
            play()
        }

        fragmentGameBinding.apply {
            alternativeOneBt.setOnClickListener(onClickListener)
            alternativeTwoBt.setOnClickListener(onClickListener)
            alternativeThreeBt.setOnClickListener(onClickListener)
        }
        play()

        return fragmentGameBinding.root
    }

    companion object {
        private const val MSG_ROUND_DEADLINE = 0
        @JvmStatic
        fun newInstance(settings: Settings) =
            GameFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(EXTRA_SETTINGS, settings)
                }
            }
    }

    private fun play(){
        currentRound = calculationGame.nextRound()
        if (currentRound != null) {
            playedRounds++
            fragmentGameBinding.apply {
                "Round: ${currentRound!!.round}/${settings.rounds}".also {
                    roundTv.text = it
                }
                questionTv.text = currentRound!!.question
                alternativeOneBt.text = currentRound!!.alt1.toString()
                alternativeTwoBt.text = currentRound!!.alt2.toString()
                alternativeThreeBt.text = currentRound!!.alt3.toString()
            }
            startRoundTime = System.currentTimeMillis()
            roundDeadlineHandler.sendEmptyMessageDelayed(MSG_ROUND_DEADLINE, settings.roundsInterval)
        }else{
            if(playedRounds == settings.rounds && activity != null) {
                val points = hits * 10f / (totalGameTime / 1000L)
                val gameResult = GameResult(points, settings)
                val resultActivityIntent = Intent(activity, ResultActivity::class.java)

                resultActivityIntent.putExtra(EXTRA_RESULT, gameResult)
                startActivity(resultActivityIntent)
                requireActivity().finish()
            }
        }
    }
}
