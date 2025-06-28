import React, { useState, useEffect } from 'react';
import '../../styles/admin/ControlUsersPage.css';

function ControlUsersPage() {
  const [users, setUsers] = useState([]);

  const fetchUsers = () => {
    const token = localStorage.getItem('token');

    fetch('http://localhost:8080/api/admin/allUser', {
      method: 'GET',
      headers: {
        'Authorization': `Bearer ${token}`,
        'Content-Type': 'application/json',
      },
    })
      .then(res => {
        if (!res.ok) throw new Error(`Error: ${res.status}`);
        return res.json();
      })
      .then(data => setUsers(data))
      .catch(err => console.error('Error fetching users:', err));
  };

  useEffect(() => {
    fetchUsers();
  }, []);

  return (
    <div className="control-users-container">
      <h2>User Management</h2>
      <button className="refresh-button" onClick={fetchUsers}>Refresh Data</button>
      <table className="users-table">
        <thead>
          <tr>
            <th>ID</th>
            <th>Username</th>
            <th>Email</th>
            <th>Role</th>
            <th>Change Role</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          {users.map(user => (
            <tr key={user.id}>
              <td>{user.id}</td>
              <td>{user.username}</td>
              <td>{user.email}</td>
              <td>{user.role}</td>
              <td>
                <select defaultValue={user.role} className="role-select">
                  <option value="STUDENT">STUDENT</option>
                  <option value="ADMIN">ADMIN</option>
                  <option value="TEACHER">TEACHER</option>
                </select>
              </td>
              <td>
                <button className="action-button change-role">Change Role</button>
                <button className="action-button ban-user">Ban</button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

export default ControlUsersPage;
