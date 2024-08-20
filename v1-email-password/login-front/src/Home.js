import React from "react";
import { Link } from "react-router-dom";

function Login() {
  return (
    <div>
      <div className="header">
        <h2 className="intro">Login Test App</h2>
        <div>
          <Link to={"/login"}>
            <button className={"login_button"}> login </button>
          </Link>
          <Link to={"/join"}>
            <button className={"join_button"}> join </button>
          </Link>
        </div>
      </div>
      <div className="body">
        <div className="status_text_container">
          <h1>You should login</h1>
        </div>
      </div>
    </div>
  );
}

export default Login;
