# WSB Travel Buddy

A simple application where you can save your travel memories and inspire others with them.

The app was created as a credit for a college course.

## Firebase setup
Mobile app uses some Firebase services: Authentication, FirestoreDB & Storage.
To ensure the proper functioning of the app, make sure you will enabled all these in yours Firebase Project.

1. Create a new Firebase project
2. Create new Android application within the Firebase project:
   - use `com.example.travelbuddy` package name,
   - during the process, download the `google-services.json` file and put him inside `/app` directory
3. Enable required services within your Firebase project. Create `travels` collection within Firestore DB
