package ph.edu.auf.rpglem

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ScrollView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(), GameLogic.GameUpdateListener {

    private lateinit var gameLogic: GameLogic
    private lateinit var tvGameProgress: TextView
    private lateinit var scrollView: ScrollView
    private lateinit var tvBattleLog: TextView

    @SuppressLint("MissingInflatedId", "CutPasteId")
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnAttack = findViewById<Button>(R.id.btnAttack)
        val btnDefend = findViewById<Button>(R.id.btnDefend)
        val btnHeal = findViewById<Button>(R.id.btnHeal)
        tvGameProgress = findViewById(R.id.tvPlayerHealth)
        scrollView = findViewById(R.id.scrollView2)
        tvBattleLog = findViewById(R.id.tvBattleLog)

        gameLogic = GameLogic(this)

        btnAttack.setOnClickListener {
            gameLogic.playerAction("Attack")
        }

        btnDefend.setOnClickListener {
            gameLogic.playerAction("Defend")
        }

        btnHeal.setOnClickListener {
            gameLogic.playerAction("Heal")
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onUpdatePlayerHealth(health: Int) {
        tvGameProgress.text = "Player HP: $health"
    }

    @SuppressLint("SetTextI18n")
    override fun onUpdateEnemyHealth(health: Int) {
        val tvEnemyHealth = findViewById<TextView>(R.id.tvEnemyHealth)
        tvEnemyHealth.text = "Enemy HP: $health"
    }

    override fun onAddToBattleLog(message: String) {
        tvBattleLog.append("$message\n")
        scrollView.post {
            scrollView.fullScroll(View.FOCUS_DOWN)
        }
    }
}

