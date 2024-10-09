import React, { useEffect, useState } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import axios from "axios";
import { toast } from "react-toastify";
import { setCookie } from "./Cookie";

function OAuthCallback({ provider, setIsAuthenticated, setEmail, setName }) {
  const navigate = useNavigate();
  const location = useLocation();
  const [error, setError] = useState("");

  const handleOAuthLogin = async () => {
    let url = "";
    const params = new URLSearchParams(location.search);
    switch (provider) {
      case "google":
        url = getGoogleOAuthCallbackUrl(params);
        break;
      case "naver":
        url = getNaverOAuthCallbackUrl(params);
        break;
      case "kakao":
        url = getKakaoOAuthCallbackUrl(params);
        break;
      default:
        throw new Error("Unsupported OAuth provider");
    }
    return await axios.get(url);
  };

  const getGoogleOAuthCallbackUrl = (params) => {
    const code = params.get("code");
    return `http://localhost:8080/oauth/google/token?code=${code}`;
  };

  const getNaverOAuthCallbackUrl = (params) => {
    const code = params.get("code");
    const state = params.get("state");
    return `http://localhost:8080/oauth/naver/token?code=${code}&state=${state}`;
  };

  const getKakaoOAuthCallbackUrl = (params) => {
    const code = params.get("code");
    return `http://localhost:8080/oauth/kakao/token?code=${code}`;
  };

  useEffect(() => {
    handleOAuthLogin()
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

export default OAuthCallback;
