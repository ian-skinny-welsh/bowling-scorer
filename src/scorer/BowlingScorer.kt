package scorer

import scorer.FrameType.*

class BowlingScorer {
    private val STRIKE = 'X'
    private val SPARE = '/'
    private val MISS = '-'
    private val STRIKE_SCORE = 10
    private val SPARE_SCORE = 10
    private val MISS_SCORE = 0

    fun calculateGameScore(scoreStr: String): Int {
        var frames: List<String> = splitIntoFrames(scoreStr)
        val frameCount: Int = frames.size - 1
        var score = 0

        for (i in 1..frameCount) {
            score += calculateFrameScore(frames)
            frames = frames.drop(1)
        }
        return score
    }

    fun splitIntoFrames(scoreStr: String): List<String> {
        return scoreStr.split("||","|")
    }

    fun calculateFrameScore(frames: List<String>): Int {
        val thisFrame = frames.first()

        return when (getFrameType(thisFrame)) {
            SIMPLE_FRAME -> sumBothBalls(thisFrame)

            SPARE_FRAME -> sumFrameWithSpare(frames.drop(1).first())

            STRIKE_FRAME -> sumStrike(frames.drop(1).take(2))
        }
    }

    fun getFrameType(frameStr: String): FrameType {
        return FrameType.values().first { fType -> fType.isThisType(frameStr) }
    }

    fun sumBothBalls(twoBalls: String): Int {
        return getScoreForBall(twoBalls.first()) + getScoreForBall(twoBalls.last())
    }

    fun getScoreForBall(ball: Char): Int {
        return when(ball) {
            MISS -> MISS_SCORE
            SPARE -> SPARE_SCORE
            STRIKE -> STRIKE_SCORE
            else -> ball.toString().toInt()
        }
    }

    fun sumFrameWithSpare(twoBalls: String): Int {
        return SPARE_SCORE + getScoreForBall(twoBalls.first())
    }

    fun sumStrike(next2Frames: List<String>): Int {
        val nextFrame = next2Frames.first()
        return when {
            nextFrame.length == 2 -> STRIKE_SCORE + sumTwoExtraBalls(nextFrame)
            else -> STRIKE_SCORE + getScoreForBall(nextFrame.first()) + getScoreForBall(next2Frames.last().first())
        }
    }

    fun sumTwoExtraBalls (twoBalls: String): Int {

        return when {
            SPARE_FRAME.isThisType(twoBalls) -> SPARE_SCORE

            else -> sumBothBalls(twoBalls)
        }
    }
}