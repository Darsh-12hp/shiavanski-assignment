import React, { useState } from "react";
import "./SignupPage.css";
import { Link } from "react-router";
import axios from "axios";

const SignupPage = () => {
  const [formData, setFormData] = useState({
    fullName: "",
    userName: "",
    passWord: "",
  });

  // Handle input changes
  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setFormData({ ...formData, [name]: value });
  };

  // Handle Sign Up button click
  const handleSignUp = () => {
    axios
      .post(
        "http://localhost:8080/api/v1/empsignup",

        {
          firstName: formData.fullName,
          emailId: formData.userName,
          password: formData.passWord,
        }
      )
      .then((data) => {
        alert("Sigunp SuccessFull");
      })
      .catch((data) => {
        alert("Sigunp Failed");
      });
  };
  return (
    <div className="container">
      <div className="header">SignUp</div>
      <div className="underline"></div>

      <form className="inputs">
        <div className="input">
          <input
            required
            type="text"
            name="fullName"
            placeholder="Enter your name"
            value={formData.name}
            onChange={handleInputChange}
          />
        </div>
        <div className="input">
          <input
            required
            type="email"
            name="userName"
            placeholder="Enter your email"
            value={formData.email}
            onChange={handleInputChange}
          />
        </div>
        <div className="input">
          <input
            required
            type="password"
            name="passWord"
            placeholder="Enter your password"
            value={formData.password}
            onChange={handleInputChange}
          />
        </div>
      </form>
      <div className="submit-container">
        <div className="submit" onClick={handleSignUp}>
          SignUp
        </div>
        <div className="submit toggle">
          <Link to="/login">Switch to Login</Link>
        </div>
      </div>
    </div>
  );
};

export default SignupPage;
