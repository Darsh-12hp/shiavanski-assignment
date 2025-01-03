import React, { useState } from 'react';
import './Crudpage.css'; // CSS for CRUD page

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

  const handleEdit = (id) => {
    const userToEdit = data.find(user => user.id === id);
    setCurrentUser(userToEdit);
    setName(userToEdit.name);
    setEmail(userToEdit.email);
    setShowEditModal(true); // Show the modal
  };

  const handleDelete = (id) => {
    setData(data.filter(user => user.id !== id));
  };

  const handleUpdate = async () => {
    try {
      // Call your API to update the employee
      const response = await fetch(`http://localhost:5000/api/updateemp/${currentUser.id}`, {
        method: 'PUT',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          name,
          email,
        }),
      });

      if (response.ok) {
        // Update the data state to reflect changes
        setData(data.map(user => (user.id === currentUser.id ? { ...user, name, email } : user)));
        setShowEditModal(false); // Close the modal
      } else {
        alert('Failed to update employee.');
      }
    } catch (error) {
      console.error('Error updating employee:', error);
      alert('An error occurred while updating employee data.');
    }
  };

  const handleViewAll = () => {
    // Your logic to view all employees
    alert('Viewing all employees');
  };

  return (
    <div className="crud-container">
      <h1>Welcome to the CRUD Page</h1>
      <table className="crud-table">
        <thead>
          <tr>
            <th>Name</th>
            <th>Email</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          {data.map((user) => (
            <tr key={user.id}>
              <td>{user.name}</td>
              <td>{user.email}</td>
              <td>
                <button
                  className="edit-btn"
                  onClick={() => handleEdit(user.id)}
                >
                  Edit
                </button>
                <button
                  className="delete-btn"
                  onClick={() => handleDelete(user.id)}
                >
                  Delete
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>

      {/* View All Employees Button */}
      <div className="view-all-container">
        <button
          className="view-all-btn"
          onClick={handleViewAll}
        >
          View All Employees
        </button>
      </div>

      {/* Edit Modal */}
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
