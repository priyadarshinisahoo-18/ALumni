# AlumNet

A Java Swing desktop alumni networking demo with student, alumni, and administrator portals.

## Run

Requires JDK 8 or later. From this directory, compile and launch:

```powershell
New-Item -ItemType Directory -Force classes
javac -encoding UTF-8 -d classes (Get-ChildItem -Recurse -Filter *.java src | ForEach-Object { $_.FullName })
java -cp classes alumnet.Main
```

## Demo accounts

- Student: `priya@nietstudent.in` / `student123`
- Alumni: `rahul@alumni.com` / `alumni123`
- Admin: `admin@alumnet.com` / `admin123`

## Features

Student pages include alumni discovery and connection requests, a network view, saved jobs, messaging, and editable profile details. Alumni pages include student discovery, mentorship offers, job posting, messaging, and editable profile details. Admin pages include student/alumni lists, platform reports, and settings.

The starter project uses in-memory data. New accounts and session activity work while the app is running and reset when it is restarted; no external database is required.
