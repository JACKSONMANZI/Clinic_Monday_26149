
import { useEffect, useState } from 'react'
import axios from 'axios'
import './App.css'

function App() {
  const [offices, setOffices] = useState([])

  const [search, setSearch] = useState('')

  useEffect(() => {
    axios.get('http://localhost:8080/api/offices')
      .then(response => {
        setOffices(response.data)
      })
      .catch(error => {
        console.log('Error fetching offices:', error)
      })
  }, [])
  const filteredOffices = offices.filter(office =>
    office.officeName.toLowerCase().includes(search.toLowerCase()) ||
    String(office.officeNumber).includes(search)
  )

  function deleteOffice(id) {
    const confirmDelete = window.confirm(
      'you are going to delete this office?'
    )

    if (confirmDelete) {
      axios.delete(`http://localhost:8080/api/offices/${id}`)
        .then(() => {
          setOffices(currentOffices =>
            currentOffices.filter(office => office.id !== id)
          )
        })
        .catch(error => {
          console.log('Error deleting office:', error)
          alert('Unable to delete office. Please try again.')
        })
    }
  }

  return (
    <div className="app-container">

      <h1>Office Management</h1>

      <h2>Available Offices</h2>

    
<div className="search-container">
  <input
    type="text"
    placeholder="Search by office name or number..."
    value={search}
    onChange={event => setSearch(event.target.value)}
  />
</div>

    
      <table>
        <thead>
          <tr>
            <th>ID</th>
            <th>Office Name</th>
            <th>Office Number</th>
            <th>Action</th>
          </tr>
        </thead>

        <tbody>
          {filteredOffices.length > 0 ? (
            filteredOffices.map(office => (
              <tr key={office.id}>
                <td>{office.id}</td>
                <td>{office.officeName}</td>
                <td>{office.officeNumber}</td>

                <td>
                  <button
                    className="delete-btn"
                    onClick={() => deleteOffice(office.id)}
                  >
                    Delete
                  </button>

                  <button
                    className="update-btn"
                    type="button"
                    
                  >
                    Update
                  </button>
                </td>
              </tr>
            ))
          ) : (
            <tr>
              <td colSpan="4">No offices found.</td>
            </tr>
          )}
        </tbody>
      </table>

    </div>
  )
}

export default App
