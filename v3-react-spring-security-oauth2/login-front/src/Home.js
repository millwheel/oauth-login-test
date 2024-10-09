import React from "react";
import { Link } from "react-router-dom";

function Home({ isAuthenticated }) {
  return (
    <div>
      <div className="home-header">
        <h2 className="intro">Login Test App</h2>
        <div>
          {isAuthenticated ? (
            <>
              <Link to={"/my-page"}>
                <button className={"my_page_button"}>My Page</button>
              </Link>
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
      <div className="home-body">
        <div className="status_text_container">
          {isAuthenticated ? (
            <>
              <h1> "Welcome. You are logged in." </h1>
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
