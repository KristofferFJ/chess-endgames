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

    @Test
    fun moveFoolsMate() {
        val position = FenUtils.readFen(Constants.STARTING_POSITION)
        position.move("f2f3")
        assert(position.toFen() == "rnbqkbnr/pppppppp/8/8/8/5P2/PPPPP1PP/RNBQKBNR b KQkq - 0 1")
        position.move("e7e5")
        assert(position.toFen() == "rnbqkbnr/pppp1ppp/8/4p3/8/5P2/PPPPP1PP/RNBQKBNR w KQkq e6 0 2")
        position.move("g2g4")
        assert(position.toFen() == "rnbqkbnr/pppp1ppp/8/4p3/6P1/5P2/PPPPP2P/RNBQKBNR b KQkq g3 0 2")
        position.move("d8h4")
        assert(position.toFen() == "rnb1kbnr/pppp1ppp/8/4p3/6Pq/5P2/PPPPP2P/RNBQKBNR w KQkq - 1 3")
        println(position.print())
    }

    @Test
    fun moveWhiteKingSideCastling() {
        val position = FenUtils.readFen(Constants.STARTING_POSITION)
        position.move("e2e4")
        assert(position.toFen() == "rnbqkbnr/pppppppp/8/8/4P3/8/PPPP1PPP/RNBQKBNR b KQkq e3 0 1")
        position.move("e7e5")
        assert(position.toFen() == "rnbqkbnr/pppp1ppp/8/4p3/4P3/8/PPPP1PPP/RNBQKBNR w KQkq e6 0 2")
        position.move("g1f3")
        assert(position.toFen() == "rnbqkbnr/pppp1ppp/8/4p3/4P3/5N2/PPPP1PPP/RNBQKB1R b KQkq - 1 2")
        position.move("b8c6")
        assert(position.toFen() == "r1bqkbnr/pppp1ppp/2n5/4p3/4P3/5N2/PPPP1PPP/RNBQKB1R w KQkq - 2 3")
        position.move("f1b5")
        assert(position.toFen() == Constants.RUY_LOPEZ)
        position.move("a7a6")
        assert(position.toFen() == "r1bqkbnr/1ppp1ppp/p1n5/1B2p3/4P3/5N2/PPPP1PPP/RNBQK2R w KQkq - 0 4")
        position.move("e1g1")
        assert(position.toFen() == "r1bqkbnr/1ppp1ppp/p1n5/1B2p3/4P3/5N2/PPPP1PPP/RNBQ1RK1 b kq - 1 4")
        println(position.print())
    }

    @Test
    fun moveBlackQueenSideCastling() {
        val position = FenUtils.readFen(Constants.STARTING_POSITION)
        position.move("e2e4")
        assert(position.toFen() == "rnbqkbnr/pppppppp/8/8/4P3/8/PPPP1PPP/RNBQKBNR b KQkq e3 0 1")
        position.move("b8c6")
        assert(position.toFen() == "r1bqkbnr/pppppppp/2n5/8/4P3/8/PPPP1PPP/RNBQKBNR w KQkq - 1 2")
        position.move("g1f3")
        assert(position.toFen() == "r1bqkbnr/pppppppp/2n5/8/4P3/5N2/PPPP1PPP/RNBQKB1R b KQkq - 2 2")
        position.move("d7d5")
        assert(position.toFen() == "r1bqkbnr/ppp1pppp/2n5/3p4/4P3/5N2/PPPP1PPP/RNBQKB1R w KQkq d6 0 3")
        position.move("e1e2")
        assert(position.toFen() == "r1bqkbnr/ppp1pppp/2n5/3p4/4P3/5N2/PPPPKPPP/RNBQ1B1R b kq - 1 3")
        position.move("c8g4")
        assert(position.toFen() == "r2qkbnr/ppp1pppp/2n5/3p4/4P1b1/5N2/PPPPKPPP/RNBQ1B1R w kq - 2 4")
        position.move("d2d3")
        assert(position.toFen() == "r2qkbnr/ppp1pppp/2n5/3p4/4P1b1/3P1N2/PPP1KPPP/RNBQ1B1R b kq - 0 4")
        position.move("d8d7")
        assert(position.toFen() == "r3kbnr/pppqpppp/2n5/3p4/4P1b1/3P1N2/PPP1KPPP/RNBQ1B1R w kq - 1 5")
        position.move("h2h3")
        assert(position.toFen() == "r3kbnr/pppqpppp/2n5/3p4/4P1b1/3P1N1P/PPP1KPP1/RNBQ1B1R b kq - 0 5")
        position.move("e8c8")
        assert(position.toFen() == "2kr1bnr/pppqpppp/2n5/3p4/4P1b1/3P1N1P/PPP1KPP1/RNBQ1B1R w - - 1 6")
    }
}
