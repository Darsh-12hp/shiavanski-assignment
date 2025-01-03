import React, { useState } from 'react';
import './ViewEmp.css'; // CSS for CRUD page

const Crudpage = () => {
  const [data, setData] = useState([
    { id: 1, name: 'John Doe', email: 'john@example.com' },
    { id: 2, name: 'Jane Smith', email: 'jane@example.com' },
    { id: 3, name: 'Tom Brown', email: 'tom@example.com' },
  ]);
  
  const [showEditModal, setShowEditModal] = useState(false);
  const [currentUser, setCurrentUser] = useState(null); // Store the user being edited
  const [name, setName] = useState('');
  const [email, setEmail] = useState('');

  // Removed Edit and Delete functions as they are no longer needed

  return (
    <div className="crud-container">
      <h1>Welcome to the CRUD Page</h1>
      <table className="crud-table">
        <thead>
          <tr>
            <th>Name</th>
            <th>Email</th>
          </tr>
        </thead>
        <tbody>
          {data.map((user) => (
            <tr key={user.id}>
              <td>{user.name}</td>
              <td>{user.email}</td>
            </tr>
          ))}
        </tbody>
      </table>

      {/* Edit Modal is still present but can be removed as well if you don't need it */}
      {showEditModal && (
        <div className="edit-modal">
          <div className="modal-content">
            <h2>Edit Employee</h2>
            <label>Name</label>
            <input
              type="text"
              value={name}
              onChange={(e) => setName(e.target.value)}
            />
            <label>Email</label>
            <input
              type="email"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
            />
            <button className="edit-btn" onClick={handleUpdate}>
              Update
            </button>
            <button
              className="cancel-btn"
              onClick={() => setShowEditModal(false)} // Close the modal
            >
              Cancel
            </button>
          </div>
        </div>
      )}
    </div>
  );
};

export default Crudpage;
