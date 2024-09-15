package br.edu.scl.ifsp.sdm.fastcalculation

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import br.edu.scl.ifsp.sdm.fastcalculation.data.Settings
import br.edu.scl.ifsp.sdm.fastcalculation.databinding.ActivityGameBinding
import br.edu.scl.ifsp.sdm.fastcalculation.fragments.GameFragment
import br.edu.scl.ifsp.sdm.fastcalculation.fragments.WelcomeFragment
import br.edu.scl.ifsp.sdm.fastcalculation.interfaces.OnPlayGame
import br.edu.scl.ifsp.sdm.fastcalculation.utils.Extras.EXTRA_SETTINGS

class GameActivity : AppCompatActivity(), OnPlayGame {
    private val activityGameBinding: ActivityGameBinding by lazy {
        ActivityGameBinding.inflate(layoutInflater)
    }

    private lateinit var settings: Settings

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContentView(activityGameBinding.root)

        ViewCompat.setOnApplyWindowInsetsListener(activityGameBinding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setSupportActionBar(activityGameBinding.gameTbIn.gameTb)
        supportActionBar?.apply {
            title = getString(R.string.app_name)
            subtitle = getString(R.string.game)
        }

        settings = intent.getParcelableExtra<Settings>(EXTRA_SETTINGS) ?: Settings()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.gameFl, WelcomeFragment.newInstance(settings))
            .commit()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_game, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            R.id.restartGameMi -> {
                onPlayGame()
                return true
            }
            R.id.exitMi -> {
                finish()
                return true
            }
            else -> { false }
        }
    }

    override fun onPlayGame() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.gameFl, GameFragment.newInstance(settings))
            .commit()
    }
}
