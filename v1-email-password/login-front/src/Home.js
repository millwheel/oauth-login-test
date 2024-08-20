import React from "react";
import { Link } from "react-router-dom";

function Home({ isAuthenticated }) {
  return (
    <div>
      <div className="header">
        <h2 className="intro">Login Test App</h2>
        <div>
          {isAuthenticated ? (
            <>
              <Link to={"/logout"}>
                <button className={"login_button"}>Logout</button>
              </Link>
            </>
          ) : (
            <>
              <Link to={"/login"}>
                <button className={"login_button"}> login </button>
              </Link>
              <Link to={"/join"}>
                <button className={"join_button"}> join </button>
              </Link>
            </>
          )}
        </div>
      </div>
      <div className="body">
        <div className="status_text_container">
          <h1>
            {isAuthenticated
              ? "Welcome. You are logged in."
              : "You should login"}
          </h1>
        </div>
      </div>
    </div>
  );
}

export default Home;
