import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import handleOAuthLogin from "./Oauth_login";
import {handleEmailLogin} from "./Email_login";

function Login({ setIsAuthenticated }) {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");
  const navigate = useNavigate();

  const onEmailLoginSubmit = (e) => {
    e.preventDefault();
    handleEmailLogin(email, password, setIsAuthenticated, setError, navigate);
  };

  return (
    <div className="frame">
      <div className="container">
        <h1>Login format</h1>
        {error && <p style={{ color: "red" }}>{error}</p>}
        <form onSubmit={onEmailLoginSubmit}>
          <input
            type="email"
            placeholder="Email"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            required
          />
          <input
            type="password"
            placeholder="Password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            required
          />
          <button className="submit_button" type="submit">
            Submit
          </button>
        </form>
        <div className="oauth2_container">
          <button onClick={() => handleOAuthLogin("google")} className="oauth2_button">
            Login with Google
          </button>
          <button onClick={() => handleOAuthLogin} className="oauth2_button">
            Login with Naver
          </button>
          <button onClick={() => handleOAuthLogin} className="oauth2_button">
            Login with Kakao
          </button>
        </div>
      </div>
    </div>
  );
}

export default Login;
