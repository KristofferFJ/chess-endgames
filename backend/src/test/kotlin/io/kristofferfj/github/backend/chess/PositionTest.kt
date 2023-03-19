package io.kristofferfj.github.backend.chess

import io.kristofferfj.github.backend.utils.Constants
import io.kristofferfj.github.backend.utils.FenUtils
import org.junit.jupiter.api.Test

class PositionTest {

    @Test
    fun moveScholarsMate() {
        val position = FenUtils.readFen(Constants.STARTING_POSITION)
        position.move("e2e4")
        assert(position.toFen() == "rnbqkbnr/pppppppp/8/8/4P3/8/PPPP1PPP/RNBQKBNR b KQkq e3 0 1")
        position.move("e7e5")
        assert(position.toFen() == "rnbqkbnr/pppp1ppp/8/4p3/4P3/8/PPPP1PPP/RNBQKBNR w KQkq e6 0 2")
        position.move("f1c4")
        assert(position.toFen() == "rnbqkbnr/pppp1ppp/8/4p3/2B1P3/8/PPPP1PPP/RNBQK1NR b KQkq - 1 2")
        position.move("b8c6")
        assert(position.toFen() == "r1bqkbnr/pppp1ppp/2n5/4p3/2B1P3/8/PPPP1PPP/RNBQK1NR w KQkq - 2 3")
        position.move("d1h5")
        assert(position.toFen() == "r1bqkbnr/pppp1ppp/2n5/4p2Q/2B1P3/8/PPPP1PPP/RNB1K1NR b KQkq - 3 3")
        position.move("d7d6")
        assert(position.toFen() == "r1bqkbnr/ppp2ppp/2np4/4p2Q/2B1P3/8/PPPP1PPP/RNB1K1NR w KQkq - 0 4")
        position.move("h5f7")
        assert(position.toFen() == "r1bqkbnr/ppp2Qpp/2np4/4p3/2B1P3/8/PPPP1PPP/RNB1K1NR b KQkq - 0 4")
        println(position.print())
    }
}