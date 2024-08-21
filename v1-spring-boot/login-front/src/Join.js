import React, { useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import { toast } from "react-toastify";

function Join() {
  const [email, setEmail] = useState("");
  const [name, setName] = useState("");
  const [password, setPassword] = useState("");
  const [passwordConfirm, setPasswordConfirm] = useState("");
  const [error, setError] = useState("");
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (password !== passwordConfirm) {
      setError("Passwords do not match");
      return;
    }

    try {
      const response = await axios.post("http://localhost:8080/join", {
        email,
        name,
        password,
      });

      console.log(response.data);
      toast.success(response.data);

      navigate("/");
    } catch (error) {
      setError(error.response?.data);
      console.error(error.response?.data);
    }
  };

  return (
    <div className="frame">
      <div className="container">
        <h1>Join format</h1>
        {error && <p style={{ color: "red" }}>{error}</p>}
        <form onSubmit={handleSubmit}>
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
          <button className="oauth2_button">SignUp with Google</button>
          <button className="oauth2_button">SignUp with Naver</button>
        </div>
      </div>
    </div>
  );
}

export default Join;
