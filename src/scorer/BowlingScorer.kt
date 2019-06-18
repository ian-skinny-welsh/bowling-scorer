package scorer

class BowlingScorer {
    fun main(args: Array<String>){
        println("It lives!")
    }

    fun calculateGameScore(scoreStr: String): Int {
        return 0
    }

    fun calculateFrameScore(frameStr: String): Int {
        val frames: List<String> = frameStr.split("|","||")
        val thisFrame = frames.first()

        if(isSimpleFrame(thisFrame)) return sumSimpleFrame(thisFrame)

        return when(thisFrame.last()){
            '/' -> sumFrameWithSpare(frames.get(1))
            else -> sumStrike(frames.drop(1).take(2))
        }
    }

    fun isSimpleFrame(frameStr: String): Boolean {
        return frameStr.matches("[1-9-]{2}".toRegex())
    }

    fun sumSimpleFrame(twoBalls: String): Int {
        return when(twoBalls){
            "--" -> 0
            else -> returnHighestPinCount(twoBalls)
        }
    }

    fun returnHighestPinCount(twoBalls: String): Int {
        return if(twoBalls.last() == '-') {
                getScoreForBall(twoBalls.first())
            } else {
                getScoreForBall(twoBalls.last())
            }
    }

    fun getScoreForBall(ball: Char): Int {
        return when(ball) {
            '-' -> 0
            '/' -> 10
            'X' -> 10
            else -> ball.toString().toInt()
        }
    }

    fun sumFrameWithSpare(twoBalls: String): Int {
        return 10 + getScoreForBall(twoBalls.first())
    }

    fun sumStrike(next2Frames: List<String>): Int {
        return when(next2Frames.first().length) {
            2 -> 10 + returnHighestPinCount(next2Frames.first())
            else -> 10 + getScoreForBall(next2Frames.first().first()) + getScoreForBall(next2Frames.last().first())
        }
    }
}