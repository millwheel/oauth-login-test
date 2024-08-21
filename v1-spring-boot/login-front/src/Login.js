import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import { toast } from "react-toastify";

function Login({ setIsAuthenticated }) {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");
  const navigate = useNavigate();

  const handleEmailLogin = async (e) => {
    e.preventDefault();
    try {
      const response = await axios.post("http://localhost:8080/login", {
        email,
        password,
      });

      console.log(response.data);
      toast.success(response.data);

      setIsAuthenticated(true);
      navigate("/");
    } catch (error) {
      setError(error.response?.data);
      console.error(error.response?.data || error.message);
    }
  };

  const googleClientId = "YOUR_GOOGLE_CLIENT_ID";
  const naverClientId = "YOUR_NAVER_CLIENT_ID";
  const naverRedirectUri = "YOUR_REDIRECT_URI";
  const googleRedirectUri = "YOUR_REDIRECT_URI";

  const handleGoogleLogin = () => {
    const googleAuthUrl = `https://accounts.google.com/o/oauth2/auth?response_type=code&client_id=${googleClientId}&redirect_uri=${googleRedirectUri}&scope=profile%20email`;
    window.location.href = googleAuthUrl;
  };

  const handleNaverLogin = () => {
    const naverAuthUrl = `https://nid.naver.com/oauth2.0/authorize?response_type=code&client_id=${naverClientId}&redirect_uri=${naverRedirectUri}&state=RANDOM_STATE`;
    window.location.href = naverAuthUrl;
  };

  return (
    <div className="frame">
      <div className="container">
        <h1>Login format</h1>
        {error && <p style={{ color: "red" }}>{error}</p>}
        <form onSubmit={handleEmailLogin}>
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
          <button onClick={handleGoogleLogin} className="oauth2_button">
            Login with Google
          </button>
          <button onClick={handleNaverLogin} className="oauth2_button">
            Login with Naver
          </button>
        </div>
      </div>
    </div>
  );
}

export default Login;
