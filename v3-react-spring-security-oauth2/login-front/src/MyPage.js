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
      <h2>User Information</h2>
      <p>
        <strong>ID:</strong> {userData.id}
      </p>
      <p>
        <strong>Username:</strong> {userData.username}
      </p>
      <p>
        <strong>Provider:</strong> {userData.provider}
      </p>
      <p>
        <strong>Email:</strong> {userData.email}
      </p>
      <p>
        <strong>Name:</strong> {userData.name}
      </p>
      <p>
        <strong>Authorities:</strong>
      </p>
      <ul>
        {userData.authorities.map((authority, index) => (
          <li key={index}>{authority}</li>
        ))}
      </ul>
      <BackButton to="/" />
    </div>
  );
};

export default MyPage;
