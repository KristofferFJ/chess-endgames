package io.kristofferfj.github.backend.utils

import io.kristofferfj.github.backend.rules.Color
import io.kristofferfj.github.backend.utils.Constants.Companion.RUY_LOPEZ
import org.junit.jupiter.api.Test


class FenUtilsTest {

    @Test
    fun readFenTest() {
        val game = FenUtils.readFen(RUY_LOPEZ)
        assert(
            game.print() == "r bqkbnr\n" +
                    "pppp ppp\n" +
                    "  n     \n" +
                    " B  p   \n" +
                    "    P   \n" +
                    "     N  \n" +
                    "PPPP PPP\n" +
                    "RNBQK  R\n"
        )
        assert(game.whiteKingSideCastle)
        assert(game.whiteQueenSideCastle)
        assert(game.blackKingSideCastle)
        assert(game.blackQueenSideCastle)
        assert(game.toMove == Color.B)
        assert(game.enPassantSquare == null)
        assert(game.halfMoveClock == 3)
        assert(game.fullMoveClock == 3)
    }

    @Test
    fun toFenTest() {
        val game = FenUtils.readFen(RUY_LOPEZ)
        assert(FenUtils.toFen(game) == RUY_LOPEZ)
    }

    @Test
    fun conversionSpeed() {
        val startTime = System.currentTimeMillis()
        repeat(100000) {
            val game = FenUtils.readFen(RUY_LOPEZ)
            assert(FenUtils.toFen(game) == RUY_LOPEZ)
        }
        assert(System.currentTimeMillis() - startTime < 1000)
    }
}