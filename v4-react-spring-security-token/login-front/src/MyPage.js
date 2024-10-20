import React, { useEffect, useState } from "react";
import axios from "axios";
import BackButton from "./BackButton";

const MyPage = () => {
  const [userData, setUserData] = useState(null);

  const fetchUserData = async () => {
    return await axios.get("/users/db");
  };

  useEffect(() => {
    fetchUserData()
      .then((response) => {
        setUserData(response.data);
      })
      .catch((error) => {
        console.error("Failed to fetch user data:", error);
      });
  }, []);

  if (!userData) {
    return <div>Loading user information...</div>;
  }

  return (
    <div>
      <BackButton to="/" />
      <div className="frame">
        <div className="container">
          <h1>User Information</h1>
          <ul>
            <li>
              <strong>ID:</strong> {userData.id}
            </li>
            <li>
              <strong>Provider:</strong> {userData.provider}
            </li>
            <li>
              <strong>Email:</strong> {userData.email}
            </li>
            <li>
              <strong>Name:</strong> {userData.name}
            </li>
            <li>
              <strong>Authorities:</strong>
              <ul className="authority-list">
                {userData.authorities.map((authority, index) => (
                  <li key={index} className="authority-item">
                    {authority}
                  </li>
                ))}
              </ul>
            </li>
          </ul>
        </div>
      </div>
    </div>
  );
};

export default MyPage;
