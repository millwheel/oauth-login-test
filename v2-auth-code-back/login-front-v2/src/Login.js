import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import handleOAuthLogin from "./Oauth_login";

function Login({ setIsAuthenticated, setEmail, setName}) {
  const [error, setError] = useState("");
  const navigate = useNavigate();


  return (
    <div className="frame">
      <div className="container">
        <h1>Login format</h1>
        {error && <p style={{ color: "red" }}>{error}</p>}
        <div className="oauth2_container">
          <button onClick={() => handleOAuthLogin("google", setIsAuthenticated, setEmail, setName, setError, navigate)}
                  className="oauth2_button">
            Login with Google
          </button>
          <button onClick={() => handleOAuthLogin("naver", setIsAuthenticated, setEmail, setName, setError, navigate)}
                  className="oauth2_button">
            Login with Naver
          </button>
          <button onClick={() => handleOAuthLogin("kakao", setIsAuthenticated, setEmail, setName, setError, navigate)} className="oauth2_button">
            Login with Kakao
          </button>
        </div>
      </div>
    </div>
  );
}

export default Login;
