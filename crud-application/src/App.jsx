import { BrowserRouter, Navigate, Route, Routes } from "react-router";
import "./App.css";
import Crudpage from "./componenets/Crud-Page/Crudpage";
import ViewEmp from "./componenets/ViewAll/ViewEmp";
import LoginPage from "./page/login/LoginPage";
import SignupPage from "./page/signup/SignupPage";

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Navigate to="/login" />} />
        <Route path="/login" element={<LoginPage />} />
        <Route path="/signup" element={<SignupPage />} />
        <Route path="/crud" element={<Crudpage />} />
        <Route path="/view" element={<ViewEmp />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
