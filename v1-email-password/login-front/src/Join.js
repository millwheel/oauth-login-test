import React from "react";

function Join() {
  return (
    <div className="frame">
      <div className="container">
        <h1>Join format</h1>
        <input type="email" placeholder="Email" />
        <input type="text" placeholder="Name" />
        <input type="password" placeholder="Password" />
        <input type="password" placeholder="Password Confirm" />
        <button className="submit_button">Submit</button>
      </div>
    </div>
  );
}

export default Join;
