import {Chessboard} from "react-chessboard";
import {useState} from "react";
import {Piece, Square} from "react-chessboard/dist/chessboard/types";
import {sendMove} from "@/pages/api/backendapi";
import {Chess} from "chess.js";

export default function Home() {
    const [state, setState] = useState("r1bqkbnr/pppp1ppp/2n5/1B2p3/4P3/5N2/PPPP1PPP/RNBQK2R b KQkq - 3 3");
    const [game, setGame] = useState(new Chess())

    const onPieceDrop = (sourceSquare: Square, targetSquare: Square, piece: Piece): boolean => {
        sendMove(sourceSquare.toString() + targetSquare.toString(), state).then(
            (response) => {
                setState(response.data)
            }
        )
        console.log(sourceSquare)
        console.log(targetSquare)
        console.log(piece)
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
