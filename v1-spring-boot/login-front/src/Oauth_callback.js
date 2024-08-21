import React, { useEffect, useState } from "react";
import { useNavigate, useLocation } from "react-router-dom";
import axios from "axios";
import { toast } from "react-toastify";

function OAuthCallback({ provider, setIsAuthenticated }) {
  const navigate = useNavigate();
  const location = useLocation();
  const [error, setError] = useState("");

  useEffect(() => {
    const params = new URLSearchParams(location.search);
    const code = params.get("code");
    const state = params.get("state");

    if (code) {
      handleOAuthCallback(code, state);
    }
  }, [location]);

  const handleOAuthCallback = async (code, state) => {
    try {
      let url = "";
      switch (provider) {
        case "google":
          url = "http://localhost:8080/oauth/google/token";
          break;
        case "naver":
          url = "http://localhost:8080/oauth/naver/token";
          break;
        case "kakao":
          url = "http://localhost:8080/oauth/kakao/token";
          break;
        default:
          throw new Error("Unsupported OAuth provider");
      }

      const response = await axios.post(url, { code, state });
      console.log("OAuth success:", response.data);
      toast.success(response.data);

      setIsAuthenticated(true);
      navigate("/");
    } catch (error) {
      setError(error.response?.data);
      console.error(error.response?.data || error.message);
    }
  };

  return (
    <div>
      {error && <p style={{ color: "red" }}>{error}</p>}
      <h2>Redirecting..</h2>
    </div>
  );
}

export default OAuthCallback;
