import {Chessboard} from "react-chessboard";
import {useState} from "react";
import {Piece, Square} from "react-chessboard/dist/chessboard/types";

export default function Home() {
    const [state, setState] = useState("r1bqkbnr/pppp1ppp/2n5/1B2p3/4P3/5N2/PPPP1PPP/RNBQK2R");

    const onPieceDrop = (sourceSquare: Square, targetSquare: Square, piece: Piece): boolean => {
        // todo backend shit
        return true
    }

    return (
        <div>
            <Chessboard
                id="BasicBoard"
                position={state}
                onPieceDrop={onPieceDrop}
            />
        </div>
    );
}
