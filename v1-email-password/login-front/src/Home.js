import Button from "./component/Button";
import React from "react";
import { Link } from "react-router-dom";

function Login() {
  return (
    <div>
      <div className="header">
        <h2 className="intro">Login Test App</h2>
        <div>
          <Link to={"/login"}>
            <Button text={"login"} className={"login_button"}></Button>
          </Link>
          <Link to={"/join"}>
            <Button text={"join"} className={"join_button"}></Button>
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
