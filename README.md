# Recruitment-Management-System Backend
This repository contains the backend server for a Recruitment Management System. The system allows users to create profiles, upload resumes, and apply for job openings. Admin users can create job openings and view applicant details.

## Features

### User Management
* Users can create profiles and upload resumes (PDF and DOCX formats only).
* Authentication using JWT tokens.
* Resume Processing
* Uploaded resumes are processed using a third-party API to extract relevant information.
* Extracted information is stored in the database.
### Job Management
* Admin users can create and manage job openings.
* Applicants can view and apply for job openings.
### Data Access
* Admin users can view all uploaded resumes and extracted data of applicants.
