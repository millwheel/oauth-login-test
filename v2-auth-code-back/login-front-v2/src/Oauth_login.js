import axios from "axios";
import {toast} from "react-toastify";
import {setCookie} from "./Cookie";

function handleOAuthLogin(provider, setIsAuthenticated, setEmail, setName, setError, navigate){
    const sendRequestOAuth = async () => {
        let url = "";
        switch (provider) {
            case "google":
                url = `http://localhost:8080/oauth2/login/google`;
                break;
            case "naver":
                url = `http://localhost:8080/oauth2/login/naver`;
                break;
            case "kakao":
                url = `http://localhost:8080/oauth2/login/kakao`;
                break;
            default:
                throw new Error("Unsupported OAuth provider");
        }
        return await axios.get(url);
    };

    try {
        sendRequestOAuth()
            .then((response) => {
                const { message, accessToken, expiresIn, email, name } = response.data;
                toast.success(message, {
                    toastId: "loginSuccess2",
                });

                setEmail(email);
                setName(name);
                setCookie("access_token", accessToken, expiresIn);
                setIsAuthenticated(true);
                navigate("/");
            })
            .catch((error) => {
                setError(error.response?.data);
                console.error(error.response?.data || error.message);
            });
    } catch (error) {
        setError(error.response?.data);
        console.error(error.response?.data || error.message);
    }

}

export default handleOAuthLogin;
