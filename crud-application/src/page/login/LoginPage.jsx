import React, { useState } from "react";
import "./LoginPage.css";
import { Link } from "react-router";

const LoginPage = () => {
  const [formData, setFormData] = useState({
    username: "",
    password: "",
  });

  // Handle input changes
  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setFormData({ ...formData, [name]: value });
  };

  // Handle Login button click
  const handleLogin = async () => {
    try {
      const response = await fetch("http://localhost:5000/api/login", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          email: formData.email,
          password: formData.password,
        }),
      });

      const result = await response.json();

      if (response.ok) {
        alert("Login successful!");
      } else {
        alert(result.message || "Login failed. Try again.");
      }
    } catch (error) {
      console.error("Error:", error);
      alert("An error occurred while logging in.");
    }
  };

  return (
    <div className="container">
      <div className="header">Login</div>
      <div className="underline"></div>
      <div className="inputs">
        <div className="input">
          <input
            type="email"
            name="username"
            placeholder="Enter your email"
            value={formData.email}
            onChange={handleInputChange}
          />
        </div>
        <div className="input">
          <input
            type="password"
            name="password"
            placeholder="Enter your password"
            value={formData.password}
            onChange={handleInputChange}
          />
        </div>
      </div>
      <div className="submit-container">
        <div className="submit" onClick={handleLogin}>
          Login
        </div>
        <div className="submit toggle">
          <Link to="/signup">Signup</Link>
        </div>
      </div>
    </div>
  );
};

export default LoginPage;
