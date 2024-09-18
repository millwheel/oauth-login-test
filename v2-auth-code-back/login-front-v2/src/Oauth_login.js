import axios from "axios";
import React, {useEffect, useState} from "react";
import {toast} from "react-toastify";
import {setCookie} from "./Cookie";
import {useNavigate} from "react-router-dom";


function handleOAuthLogin(provider){
    const navigate = useNavigate();
    const [error, setError] = useState("");

    const handleOAuthCallback = async () => {
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

    useEffect(() => {
        handleOAuthCallback()
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
    }, []);

    return (
        <div>
            {error && (
                <div style={{ color: "red" }}>
                    <p>Error: {error.message || "Unknown error occurred"}</p>
                    <p>Status: {error.status}</p>
                    <p>Timestamp: {error.timestamp}</p>
                </div>
            )}
            <h2>Redirecting..</h2>
        </div>
    );
}

export default handleOAuthLogin;
