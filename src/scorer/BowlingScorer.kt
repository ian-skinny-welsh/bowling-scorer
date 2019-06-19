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
        var score: Int = 0
        for (i in 1..frameCount) {
            score += calculateFrameScore(frames, i == frameCount)
            frames = frames.drop(1)
        }
        return score
    }

    fun splitIntoFrames(scoreStr: String): List<String> {
        return scoreStr.split("||","|")
    }

    fun calculateFrameScore(frames: List<String>, lastFrame: Boolean = false): Int {
        val thisFrame = frames.first()
        val frameType = getFrameType(thisFrame)

        return when (frameType) {
            SIMPLE_FRAME -> returnHighestPinCount(thisFrame)

            SPARE_FRAME -> sumFrameWithSpare(frames.drop(1).first())

            STRIKE_FRAME -> sumStrike(frames.drop(1).take(2), lastFrame)
        }
    }

    fun getFrameType(frameStr: String): FrameType {
        return FrameType.values().first { fType -> fType.isThisType(frameStr) }
    }

    fun returnHighestPinCount(twoBalls: String): Int {
        return if(twoBalls.last() == MISS) {
                getScoreForBall(twoBalls.first())
            } else {
                getScoreForBall(twoBalls.last())
            }
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

    fun sumStrike(next2Frames: List<String>, lastFrame: Boolean): Int {
        val nextFrame = next2Frames.first()
        return when(nextFrame.length) {
            2 -> STRIKE_SCORE + sumTwoExtraBalls(nextFrame, lastFrame)
            else -> STRIKE_SCORE + getScoreForBall(nextFrame.first()) + getScoreForBall(next2Frames.last().first())
        }
    }

    fun sumTwoExtraBalls (twoBalls: String, lastFrame: Boolean): Int {

        return when {
            lastFrame -> getScoreForBall(twoBalls.first()) + getScoreForBall(twoBalls.last())

            SIMPLE_FRAME.isThisType(twoBalls) -> returnHighestPinCount(twoBalls)

            else -> SPARE_SCORE
        }
    }
}