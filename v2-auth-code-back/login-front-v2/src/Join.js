import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import {
  handelKakaoOAuthLogin,
  handleGoogleOAuthLogin,
  handleNaverOAuthLogin,
} from "./Oauth_code";
import {handleEmailJoin} from "./Email_join";

function Join() {
  const [email, setEmail] = useState("");
  const [name, setName] = useState("");
  const [password, setPassword] = useState("");
  const [passwordConfirm, setPasswordConfirm] = useState("");
  const [error, setError] = useState("");
  const navigate = useNavigate();

  const onEmailJoinSubmit = async (e) => {
    e.preventDefault();
    handleEmailJoin(email, name, password, passwordConfirm, setError, navigate)
  };

  return (
    <div className="frame">
      <div className="container">
        <h1>Join format</h1>
        {error && <p style={{ color: "red" }}>{error}</p>}
        <form onSubmit={onEmailJoinSubmit}>
          <input
            type="email"
            placeholder="Email"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            required
          />
          <input
            type="text"
            placeholder="Name"
            value={name}
            onChange={(e) => setName(e.target.value)}
            required
          />
          <input
            type="password"
            placeholder="Password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            required
          />
          <input
            type="password"
            placeholder="Password Confirm"
            value={passwordConfirm}
            onChange={(e) => setPasswordConfirm(e.target.value)}
            required
          />
          <button className="submit_button" type="submit">
            Submit
          </button>
        </form>
        <div className="oauth2_container">
          <button onClick={handleGoogleOAuthLogin} className="oauth2_button">
            Login with Google
          </button>
          <button onClick={handleNaverOAuthLogin} className="oauth2_button">
            Login with Naver
          </button>
          <button onClick={handelKakaoOAuthLogin} className="oauth2_button">
            Login with Kakao
          </button>
        </div>
      </div>
    </div>
  );
}

export default Join;
