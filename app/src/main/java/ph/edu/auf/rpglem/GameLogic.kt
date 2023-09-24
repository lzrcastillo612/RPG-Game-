package ph.edu.auf.rpglem

import kotlin.random.Random

class GameLogic(private val updateListener: GameUpdateListener) {

    private var playerHP = 100
    private var enemyHP = 100
    private var playerTurn = true

    interface GameUpdateListener {
        fun onUpdatePlayerHealth(health: Int)
        fun onUpdateEnemyHealth(health: Int)
        fun onAddToBattleLog(message: String)
    }

    fun playerAction(action: String) {
        if (!playerTurn) {
            return
        }

        when (action) {
            "Attack" -> {
                val damage = Random.nextInt(10, 20)
                enemyHP -= damage
                updateListener.onAddToBattleLog("You attacked for $damage damage!")
                playerTurn = false
            }
            "Defend" -> {
                updateListener.onAddToBattleLog("You defend yourself.")
                playerTurn = false
            }
            "Heal" -> {
                val healAmount = Random.nextInt(15, 25)
                playerHP += healAmount
                updateListener.onAddToBattleLog("You healed for $healAmount HP!")
                playerTurn = false
            }
            else -> {
                updateListener.onAddToBattleLog("Invalid action")
            }
        }

        updateUI()
        enemyAction()
    }

    private fun enemyAction() {
        if (playerTurn) {
            return
        }

        val enemyDecision = Random.nextInt(from = 1, until = 4)
        when (enemyDecision) {
            1 -> {
                val damage = Random.nextInt(
                    8,
                    15
                )
                playerHP -= damage
                updateListener.onAddToBattleLog("Enemy attacked for $damage damage!")
                playerTurn = true
            }
            2 -> {
                updateListener.onAddToBattleLog("Enemy is defending.")
                playerTurn = true
            }
            3 -> {
                val healAmount = Random.nextInt(10, 20)
                enemyHP += healAmount
                updateListener.onAddToBattleLog("Enemy healed for $healAmount HP!")
                playerTurn = true
            }
            else -> {
                updateListener.onAddToBattleLog("Enemy made an invalid action")
            }
        }

        updateUI()
    }

    private fun updateUI() {
        updateListener.onUpdatePlayerHealth(playerHP)
        updateListener.onUpdateEnemyHealth(enemyHP)
    }
}
