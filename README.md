
# 📱 Unimessage – Real-Time SMS Translator App

**Unimessage** is a native Android application that automatically translates incoming SMS messages in real-time using Google's **ML Kit Translator** and **Firebase**.  
This app was developed as a final project for an elective course in Android Studio during my B.Sc. in Software Engineering.

## ✨ Features

- 📩 Translates SMS messages immediately upon arrival.
- 🌐 Supports multiple languages using ML Kit's on-device translation.
- 🔄 Real-time processing in background threads for smooth user experience.
- ☁️ Stores user preferences and translation history in **Firebase Realtime Database**.
- 🧠 Designed with clean architecture and focus on performance and UX.

## 🛠️ Technologies Used

- **Java**  
- **Android Studio**  
- **Firebase Realtime Database**  
- **ML Kit Translate API**  
- **BroadcastReceiver** for SMS listening  
- **Multithreading** (via `AsyncTask` / `Thread`)  
- **Material Design UI**

## 🔧 Setup & Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/your-username/unimessage.git
   ```
2. Open the project in **Android Studio**.
3. Connect your Firebase project and download `google-services.json` into the `app/` folder.
4. Enable **ML Kit Translate API** and **Realtime Database** in your Firebase console.
5. Run the app on an emulator or Android device.

## 📸 Screenshots

| Incoming Message | Translated Message |
|------------------|--------------------|
| ![Original](screenshots/sms_original.png) | ![Translated](screenshots/sms_translated.png) |

## 📂 Project Structure

```
Unimessage/
│
├── app/
│   ├── java/
│   │   └── com.example.unimessage/
│   │       ├── MainActivity.java
│   │       ├── SmsReceiver.java
│   │       ├── Translator.java
│   │       └── FirebaseHelper.java
│   └── res/
│       ├── layout/
│       ├── values/
│       └── drawable/
└── google-services.json
```

## 🚀 Future Improvements

- Allow user to manually input text for translation.
- Add settings for language selection and translation behavior.
- Support for multimedia messages (MMS).
- Improved error handling and offline support.

## 👩‍💻 Author

Ella Rosenberg – [LinkedIn](https://www.linkedin.com/in/ella-rosenberg) | [GitHub](https://github.com/ella-rosenberg)

---

> This project was built as part of my academic studies and is intended for educational and demonstration purposes.
