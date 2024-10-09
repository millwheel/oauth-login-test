import React from "react";
import BackButton from "./BackButton";

function Login() {
  const serverBaseUrl = "http://localhost:8080";

  const handleLogin = (provider) => {
    window.location.href = `${serverBaseUrl}/oauth2/auth/${provider}`;
  };

  return (
    <div>
      <BackButton to="/" />
      <div className="frame">
        <div className="container">
          <h1>Login</h1>
          <div className="oauth2_container">
            <button
              onClick={() => handleLogin("google")}
              className="oauth2_button"
            >
              Login with Google
            </button>
            <button
              onClick={() => handleLogin("naver")}
              className="oauth2_button"
            >
              Login with Naver
            </button>
            <button
              onClick={() => handleLogin("kakao")}
              className="oauth2_button"
            >
              Login with Kakao
            </button>
          </div>
        </div>
      </div>
    </div>
  );
}

export default Login;
