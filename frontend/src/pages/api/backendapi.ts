import axios from "axios";

const axiosInstance = axios.create({
    baseURL: "http://localhost:8080"
});

export const get = (url) => {
    return axiosInstance.get(url);
};

export const post = (url, data) => {
    console.log(url)
    console.log(data)
    return axiosInstance.post(url, data);
};

export const sendMove = (algebraicMove: string, fen: string) => {
    return post("stockfish", {fen: fen, algebraicMove: algebraicMove})
}
