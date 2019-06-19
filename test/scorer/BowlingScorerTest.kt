package scorer

import io.kotlintest.data.forall
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import io.kotlintest.tables.row

class BowlingScorerTest : StringSpec({

    val testee = BowlingScorer()

    "integration test should calculate score for whole game" {
        forall(
            row("--|--|--|--|--|--|--|--|--|--||", 0),
            row("1-|2-|--|--|--|--|--|--|--|--||", 3),
            row("1-|2/|2-|--|--|--|--|--|--|--||", 15),
            row("9-|9-|9-|9-|9-|9-|9-|9-|9-|9-||", 90),
            row("5/|5/|5/|5/|5/|5/|5/|5/|5/|5/||5", 150),
            row("X|7/|9-|X|-8|8/|-6|X|X|X||81", 167),
            row("X|X|X|X|X|X|X|X|X|X||XX", 300)
        ) { testData, expected ->
            testee.calculateGameScore(testData) shouldBe expected
        }
    }

    "the score for a simple frame should be the sum of the pins" {
        forall(
            row("--|--|--|--|--|--|--|--|--|--||", 0),
            row("1-|--|--|--|--|--|--|--|--|--||", 1),
            row("-1|--|--|--|--|--|--|--|--|--||", 1),
            row("2-|--|--|--|--|--|--|--||", 2),
            row("-9|--|--||", 9),
            row("25|--||", 7),
            row("X||51", 16)
        ) { testData, expected ->
            testee.calculateFrameScore(testee.splitIntoFrames(testData)) shouldBe expected
        }
    }

    "the score for a frame with spare should be 10 plus the next ball" {
        forall(
            row("1/|3-|--|--|--|--|--|--|--|--||", 13),
            row("2/|-5|--|--|--|--|--|--|--|--||", 10),
            row("3/|X|--|--|--|--|--|--|--|--||", 20),
            row("3/||5", 15),
            row("3/||X", 20)
        ) { testData, expected ->
            testee.calculateFrameScore(testee.splitIntoFrames(testData)) shouldBe expected
        }
    }


    "the score for a strike frame should be 10 plus the next two balls" {
        forall(
            row("X|--|--|--|--|--|--|--|--|--||", 10),
            row("X|3-|--|--|--|--|--|--|--|--||", 13),
            row("X|-5|--|--|--|--|--|--|--|--||", 15),
            row("X|15|--|--|--|--|--|--|--|--||", 16),
            row("X|4/|--|--|--|--|--|--|--|--||", 20),
            row("X|X|--|--|--|--|--|--|--|--||", 20),
            row("X|X|4-|--|--|--|--|--|--|--||", 24),
            row("X|X|X|--|--|--|--|--|--|--||", 30),
            row("X||36", 19),
            row("X||X3", 23),
            row("X||XX", 30)
        ) { testData, expected ->
            testee.calculateFrameScore(testee.splitIntoFrames(testData)) shouldBe expected
        }
    }
})