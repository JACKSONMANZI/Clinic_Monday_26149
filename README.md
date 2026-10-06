## Query Layer

### A1 — Find patients by last name
*"Who are all the patients with a given last name?"*
`findByLastNameIgnoreCaseOrderByFirstNameAsc`

### A2 — Appointments by status
*"Show me every appointment currently in a given status, soonest first."*
`findByStatusOrderByAppointmentDateAsc`

### A3 — Appointments in a date range
*"What appointments are scheduled between two dates?"*
`findByAppointmentDateBetweenOrderByAppointmentDateAsc`

### A4 — No double booking
*"Is this doctor already booked on that date?"*
`existsByDoctorIdAndAppointmentDateAndStatusNot`

### B1 — Doctors by specialization
*"Which doctors hold a given specialization?"*
`findBySpecializationName`

### B2 — Doctors without an office
*"Which doctors haven't been assigned an office yet?"*
`findWithoutOffice`

### B3 — Specializations nobody offers
*"Which specializations currently have zero doctors?"*
`findUnused`

### B4 — Patients of a doctor
*"Which patients has this doctor actually treated?"*
`findPatientsByDoctorId`

### C1 — Appointments per status
*"How many appointments are in each status right now?"*
`countByStatus`

### C2 — Frequent patients
*"Which patients visit often, and who visits the most?"*
`findFrequentPatients`

### C3 — Busiest office
*"Which office handles the most appointments?"*
`findBusiestOfficeStats`

### C4 — Doctor is absent
*"Cancel everything for this doctor on this day, except what's already done."*
`cancelDay`

### Bonus — Appointments page by page
*"Give me appointments in manageable pages instead of the whole list."*
`findAll(Pageable)`

### Bonus — Clean old cancellations
*"Delete old cancelled appointments we no longer need to keep."*
`deleteCancelledBefore`

## Known issues

- A4 (`POST /api/appointments/save`) currently returns a `500` error instead of
  correctly saving or returning `409 Conflict`. The conflict-check logic and the
  doctor/patient lookup are in place, but a transaction/commit error remains
  unresolved.