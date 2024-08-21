import React, { useEffect, useState } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import axios from "axios";
import { toast } from "react-toastify";
import { setCookie } from "./Cookie";

function OAuthCallback({ provider, setIsAuthenticated }) {
  const navigate = useNavigate();
  const location = useLocation();
  const [error, setError] = useState("");

  const handleOAuthCallback = async () => {
    try {
      let url = "";
      switch (provider) {
        case "google":
          break;
        case "naver":
          url = getNaverOAuthCallbackUrl();
          break;
        case "kakao":
          break;
        default:
          throw new Error("Unsupported OAuth provider");
      }

      const response = await axios.get(url);
      console.log("OAuth success:", response.data);
      const { message, accessToken, expiresIn } = response.data;
      toast.success(message, {
        toastId: "loginSuccess2",
      });
      console.log("expires" + expiresIn);

      setCookie("access_token", accessToken, expiresIn);
      setIsAuthenticated(true);
      navigate("/");
    } catch (error) {
      setError(error.response?.data);
      console.error(error.response?.data || error.message);
    }
  };

  const getGoogleOAuthCallbackUrl = () => {};

  const getNaverOAuthCallbackUrl = () => {
    const params = new URLSearchParams(location.search);
    const code = params.get("code");
    const state = params.get("state");
    return `http://localhost:8080/oauth/naver/token?code=${code}&state=${state}`;
  };

  const getKakaoOAuthCallbackUrl = () => {};

  useEffect(() => {
    handleOAuthCallback();
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
