import React from "react";

function Login() {
  return (
    <div className="frame">
      <div className="container">
        <h1>Login format</h1>
        <input type="email" placeholder="Email" />
        <input type="password" placeholder="Password" />
        <button className="submit_button">Submit</button>
      </div>
    </div>
  );
}

export default Login;
