document.querySelectorAll('input, select').forEach(field => {
    field.addEventListener('input', () => {
        if (field.checkValidity()) {
            field.classList.remove('is-invalid');
        } else {
            field.classList.add('is-invalid');
        }
    });
});

document.getElementById('membershipForm').addEventListener('submit', function (e) {
    e.preventDefault();
    addMembership();
});

function addMembership() {
    // Get form values
    const title = document.getElementById('membershipTitle').value;
    const description = document.getElementById('membershipDescription').value;
    const fees = parseFloat(document.getElementById('membershipFees').value);

    const newMembership = {
        title,
        description,
        fees,
    };

    fetch('http://localhost:8081/api/membership', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(newMembership),
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(data => {
            console.log('Success:', data);
            alert('Membership added successfully!');
            // Update the table with the new membership
            addMembershipToTable(data);
            hideAddMembershipForm();
            document.getElementById('membershipForm').reset();
        })
        .catch((error) => {
            console.error('Error:', error);
            alert('Error adding membership. Please try again.');
        });
}

function addMembershipToTable(membership) {
    const tableBody = document.getElementById('membershipTableBody');
    const newRow = document.createElement('tr');

    newRow.innerHTML = `
        <td>${membership.id}</td>
        <td>${membership.title}</td>
        <td>${membership.description}</td>
        <td>$${membership.fees}</td>
        <td>
            <button class="btn btn-secondary" onclick="editMembership('${membership.id}')">Edit</button>
            <button class="btn btn-danger" onclick="deleteMembership('${membership.id}')">Remove</button>
        </td>
    `;

    tableBody.appendChild(newRow);
}

function editMembership(membershipId) {
    alert("Edit functionality is not yet implemented.");
}

function deleteMembership(membershipId) {
    fetch(`http://localhost:8081/api/membership/${membershipId}`, {
        method: 'DELETE',
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(() => {
            console.log('Success: Membership deleted');
            alert('Membership deleted successfully!');
            // Remove the membership row from the table
            removeMembershipFromTable(membershipId);
        })
        .catch((error) => {
            console.error('Error:', error);
            alert('Error deleting membership. Please try again.');
        });
}

function removeMembershipFromTable(membershipId) {
    const tableBody = document.getElementById('membershipTableBody');
    const rows = tableBody.querySelectorAll('tr');
    rows.forEach(row => {
        if (row.querySelector('td').textContent === membershipId) {
            tableBody.removeChild(row);
        }
    });
}

function showAddMembershipForm() {
    document.getElementById('addMembershipForm').style.display = 'block';
}

function hideAddMembershipForm() {
    document.getElementById('addMembershipForm').style.display = 'none';
}
