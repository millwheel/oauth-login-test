import React from "react";
import { Link } from "react-router-dom";

function Home({ isAuthenticated, name, email }) {
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
            </>
          )}
        </div>
      </div>
      <div className="body">
        <div className="status_text_container">
          {isAuthenticated ? (
            <>
              <h1> "Welcome. You are logged in." </h1>
              <h2> email: {email} </h2>
              <h2> name: {name} </h2>
            </>
          ) : (
            <>
              <h1> "You should log in." </h1>
            </>
          )}
        </div>
      </div>
    </div>
  );
}

export default Home;
