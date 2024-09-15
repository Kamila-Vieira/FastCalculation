package br.edu.scl.ifsp.sdm.fastcalculation

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import br.edu.scl.ifsp.sdm.fastcalculation.data.GameResult
import br.edu.scl.ifsp.sdm.fastcalculation.databinding.ActivityResultBinding
import br.edu.scl.ifsp.sdm.fastcalculation.utils.Extras

class ResultActivity : AppCompatActivity() {
    private lateinit var gameResult: GameResult
    private val activityResultBinding: ActivityResultBinding by lazy {
        ActivityResultBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContentView(activityResultBinding.root)

        ViewCompat.setOnApplyWindowInsetsListener(activityResultBinding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setSupportActionBar(activityResultBinding.gameTbIn.gameTb)
        supportActionBar?.apply {
            title = getString(R.string.app_name)
            subtitle = getString(R.string.result)
        }

        gameResult = intent.getParcelableExtra<GameResult>(Extras.EXTRA_RESULT) ?: GameResult()

        activityResultBinding.apply {
            "%.1f".format(gameResult.points).also {
                pointsValueTv.text = it
            }
            restartBt.setOnClickListener {

            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_game, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            R.id.restartGameMi -> {
                return true
            }
            R.id.exitMi -> {
                finish()
                return true
            }
            else -> { false }
        }
    }
}