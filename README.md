 Space Encyclopedia 🚀

A professional, interactive Android application designed to explore the planets of our solar system. This app features a modern UI, multi-language support, and customizable reading options.

🌟 Key Features

* **Professional Splash Screen:** Implements the latest **Android 12+ Splash Screen API** for a seamless startup experience.
* **Localization (Multi-language):** Full support for **English** and **Arabic** with instant UI direction switching (LTR/RTL).
* **Customizable Typography:** Users can choose between **Small, Medium, and Large** text sizes for better accessibility.
* **Data Persistence:** Uses `SharedPreferences` to remember the user's preferred language, text size, and last selected planet even after closing the app.
* **Interactive UI:** Smooth scale animations when interacting with planet icons and visual indicators for the selected planet.

🛠 Tech Stack

* **Language:** Java
* **UI Framework:** XML (Material Design 3)
* **Minimum SDK:** API 24 (Android 7.0)
* **Libraries:** `androidx.core:core-splashscreen`

🚀 Installation & Setup

1. **Clone the Repository:**
```bash
git clone https://github.com/yourusername/space-encyclopedia.git

```


2. **Open in Android Studio:**
Select "Open an existing project" and navigate to the project folder.
3. **Sync Gradle:**
Wait for the project to sync. If you encounter a "Directory does not exist" error, go to `File > Invalidate Caches... > Invalidate and Restart`.
4. **Run the App:**
Connect your device or start an emulator and click the **Run** button.

📁 Project Structure Highlights

* `MainActivity.java`: Handles the core logic, language switching, and UI updates.
* `res/values/strings.xml`: Contains English strings.
* `res/values-ar/strings.xml`: Contains Arabic translations.
* `res/values/themes.xml`: Defines the Splash Screen style and main application theme.

👨‍💻 Author

Yousef
Space Enthusiast & Android Developer
`![App Preview](screenshots/main_screen.png)`

**هل تريدني أن أضيف أي قسم آخر، مثل "خطط التطوير المستقبلية" (Future Enhancements)؟**

