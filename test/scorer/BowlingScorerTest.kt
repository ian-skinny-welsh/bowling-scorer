package scorer

import io.kotlintest.data.forall
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import io.kotlintest.tables.row

class BowlingScorerTest : StringSpec({

    val testee = BowlingScorer()

    "integration test should calculate score for whole game" {
        testee.calculateGameScore("--|--|--|--|--|--|--|--|--|--||") shouldBe 0
    }

    "the score for a simple frame should be the sum of the pins" {
        forall(
            row("--|--|--|--|--|--|--|--|--|--||", 0),
            row("1-|--|--|--|--|--|--|--|--|--||", 1),
            row("-1|--|--|--|--|--|--|--|--|--||", 1),
            row("2-|--|--|--|--|--|--|--|--|--||", 2),
            row("-9|--|--|--|--|--|--|--|--|--||", 9),
            row("29|--|--|--|--|--|--|--|--|--||", 9)
        ) { testData, expected ->
            testee.calculateFrameScore(testData) shouldBe expected
        }
    }

    "the score for a frame with spare should be 10 plus the next ball" {
        forall(
            row("1/|3-|--|--|--|--|--|--|--|--||", 13),
            row("2/|-5|--|--|--|--|--|--|--|--||", 10),
            row("3/|X|--|--|--|--|--|--|--|--||", 20)
        ) { testData, expected ->
            testee.calculateFrameScore(testData) shouldBe expected
        }
    }


    "the score for a strike frame should be 10 plus the next two balls" {
        forall(
            row("X|--|--|--|--|--|--|--|--|--||", 10),
            row("X|3-|--|--|--|--|--|--|--|--||", 13),
            row("X|-5|--|--|--|--|--|--|--|--||", 15),
            row("X|4/|--|--|--|--|--|--|--|--||", 20),
            row("X|X|--|--|--|--|--|--|--|--||", 20),
            row("X|X|4-|--|--|--|--|--|--|--||", 24),
            row("X|X|X|--|--|--|--|--|--|--||", 30)

        ) { testData, expected ->
            testee.calculateFrameScore(testData) shouldBe expected
        }
    }
})