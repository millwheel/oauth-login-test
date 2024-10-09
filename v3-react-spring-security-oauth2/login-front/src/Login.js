import React, { useState } from "react";
import BackButton from "./BackButton";

function Login() {
  const [error, setError] = useState("");

  const handleLogin = () => {
    window.location.href = "http://localhost:8080/login";
  };

  return (
    <div className="frame">
      <div className="container">
        <h1>Login form</h1>
        {error && <p style={{ color: "red" }}>{error}</p>}
        <div className="oauth2_container">
          <button onClick={handleLogin} className="oauth2_button">
            Login with OAuth2
          </button>
        </div>
      </div>
      <BackButton to="/" />
    </div>
  );
}

export default Login;
