# Lokal - Android Application

## Table of Contents
- [Project Description](#project-description)
- [Prerequisites](#prerequisites)
- [Installation and Setup](#installation-and-setup)
- [Running the Application](#running-the-application)
- [Project Structure](#project-structure)
- [Troubleshooting](#troubleshooting)
- [Contributing](#contributing)

---

## Project Description

This is an Android application project built using Kotlin and Gradle build system.

---

## Prerequisites

Before you begin, ensure you have the following installed on your system:

### 1. **Java Development Kit (JDK)**
   - **Required Version**: JDK 17 or higher
   - **Download**: [Oracle JDK](https://www.oracle.com/java/technologies/downloads/) or [OpenJDK](https://openjdk.org/)
   - **Verify Installation**:
     ```bash
     java -version
     ```
     You should see output showing Java version 17 or higher.

### 2. **Android Studio**
   - **Required Version**: Android Studio Hedgehog (2023.1.1) or later
   - **Download**: [Android Studio Official Website](https://developer.android.com/studio)
   - **Installation Steps**:
     - Download the installer for your operating system
     - Run the installer and follow the setup wizard
     - Install the Android SDK when prompted

### 3. **Android SDK**
   - **Minimum SDK**: API 21 (Android 5.0 Lollipop)
   - **Target SDK**: Latest version recommended
   - Android Studio will help you install the required SDK versions

### 4. **Git**
   - **Download**: [Git Official Website](https://git-scm.com/)
   - **Verify Installation**:
     ```bash
     git --version
     ```

---

## Installation and Setup

Follow these step-by-step instructions to set up the project on your local machine:

### Step 1: Clone the Repository

Open your terminal/command prompt and run:

```bash
git clone https://github.com/sanakhan8859/lokal.git
```

**Alternative**: If you don't have Git installed, you can download the ZIP file:
1. Go to https://github.com/sanakhan8859/lokal
2. Click the green "Code" button
3. Select "Download ZIP"
4. Extract the ZIP file to your desired location

### Step 2: Navigate to Project Directory

```bash
cd lokal
```

### Step 3: Open Project in Android Studio

1. **Launch Android Studio**
2. Click on **"Open"** or **"Open an Existing Project"**
3. Navigate to the cloned `lokal` directory
4. Select the folder and click **"OK"**

### Step 4: Gradle Sync

Android Studio will automatically start syncing the Gradle files. If it doesn't:

1. Click on **"File"** → **"Sync Project with Gradle Files"**
2. Wait for the sync to complete (this may take a few minutes on first sync)

**Note**: If you see any SDK or dependency errors:
- Android Studio will show prompts to download missing components
- Click "Install" or "Download" as needed

### Step 5: Configure SDK (If Needed)

1. Go to **"File"** → **"Project Structure"** → **"SDK Location"**
2. Ensure the Android SDK Location is set correctly
3. Click **"Apply"** and **"OK"**

---

## Running the Application

### Option 1: Run on Physical Device

#### Prerequisites:
- Android device with USB debugging enabled
- USB cable to connect device to computer

#### Steps:

1. **Enable Developer Options on Your Device**:
   - Go to **Settings** → **About Phone**
   - Tap **"Build Number"** 7 times
   - Developer Options will be enabled

2. **Enable USB Debugging**:
   - Go to **Settings** → **Developer Options**
   - Toggle **"USB Debugging"** ON
   - Connect your device via USB

3. **Authorize Computer**:
   - When you connect your device, you'll see a popup on your phone
   - Tap **"Allow"** to authorize USB debugging

4. **Run the App**:
   - In Android Studio, select your device from the device dropdown menu at the top
   - Click the **"Run"** button (green play icon) or press **Shift + F10**
   - Wait for the build to complete
   - The app will automatically install and launch on your device

### Option 2: Run on Emulator (Virtual Device)

#### Steps:

1. **Open AVD Manager**:
   - In Android Studio, click on **"Tools"** → **"Device Manager"**
   - Or click the device icon in the toolbar

2. **Create a New Virtual Device** (if you don't have one):
   - Click **"Create Device"**
   - Select a device definition (e.g., Pixel 7)
   - Click **"Next"**

3. **Select System Image**:
   - Choose a system image (Android version)
   - Recommended: Latest stable release
   - Click **"Download"** if the image isn't already downloaded
   - Click **"Next"** and then **"Finish"**

4. **Run the App**:
   - Select your emulator from the device dropdown menu
   - Click the **"Run"** button (green play icon) or press **Shift + F10**
   - The emulator will boot up (first launch may take a few minutes)
   - The app will install and launch automatically

### Option 3: Build APK

To build an installable APK file:

1. **Build Debug APK**:
   ```bash
   ./gradlew assembleDebug
   ```
   Or in Android Studio:
   - Click **"Build"** → **"Build Bundle(s) / APK(s)"** → **"Build APK(s)"**
   
   The APK will be located at:
   ```
   app/build/outputs/apk/debug/app-debug.apk
   ```

2. **Build Release APK** (for distribution):
   ```bash
   ./gradlew assembleRelease
   ```
   **Note**: Release builds require signing configuration

3. **Install APK Manually**:
   - Transfer the APK to your Android device
   - Open the APK file on your device
   - Allow installation from unknown sources if prompted
   - Tap **"Install"**

---

## Project Structure

```
lokal/
├── .idea/                  # Android Studio configuration files
├── app/                    # Main application module
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/       # Kotlin/Java source files
│   │   │   ├── res/        # Resources (layouts, drawables, etc.)
│   │   │   └── AndroidManifest.xml
│   │   └── test/           # Unit tests
│   └── build.gradle.kts    # App-level build configuration
├── gradle/                 # Gradle wrapper files
├── .gitignore             # Git ignore rules
├── build.gradle.kts       # Project-level build configuration
├── gradle.properties      # Gradle properties
├── gradlew                # Gradle wrapper script (Unix/Mac)
├── gradlew.bat            # Gradle wrapper script (Windows)
└── settings.gradle.kts    # Gradle settings
```

---

## Troubleshooting

### Common Issues and Solutions

#### 1. **Gradle Sync Failed**
   - **Solution**: 
     - Check your internet connection
     - Click **"File"** → **"Invalidate Caches"** → **"Invalidate and Restart"**
     - Ensure you have the correct JDK version installed

#### 2. **SDK Not Found**
   - **Solution**:
     - Go to **"File"** → **"Project Structure"** → **"SDK Location"**
     - Download required SDK versions via **"Tools"** → **"SDK Manager"**

#### 3. **Device Not Detected**
   - **Solution**:
     - Ensure USB debugging is enabled on your device
     - Try a different USB cable or port
     - Install device-specific USB drivers (for Windows)
     - Run `adb devices` in terminal to check if device is detected

#### 4. **Build Errors**
   - **Solution**:
     - Clean the project: **"Build"** → **"Clean Project"**
     - Rebuild: **"Build"** → **"Rebuild Project"**
     - Update Gradle: Check for Gradle updates in `gradle/wrapper/gradle-wrapper.properties`

#### 5. **App Crashes on Launch**
   - **Solution**:
     - Check Logcat in Android Studio for error messages
     - Verify minimum SDK version compatibility with your device
     - Clear app data and cache on device

#### 6. **Slow Emulator Performance**
   - **Solution**:
     - Ensure hardware acceleration (Intel HAXM or AMD Hypervisor) is installed
     - Allocate more RAM to the emulator in AVD settings
     - Use x86 system images instead of ARM

---

## Building from Command Line

If you prefer using the command line:

### Windows:
```bash
# Debug build
gradlew.bat assembleDebug

# Run tests
gradlew.bat test

# Install on connected device
gradlew.bat installDebug
```

### macOS/Linux:
```bash
# Debug build
./gradlew assembleDebug

# Run tests
./gradlew test

# Install on connected device
./gradlew installDebug
```

---

## Additional Resources

- [Android Developer Documentation](https://developer.android.com/docs)
- [Kotlin Documentation](https://kotlinlang.org/docs/home.html)
- [Gradle Build Tool](https://gradle.org/guides/)
- [Android Studio User Guide](https://developer.android.com/studio/intro)

---

## Contributing

If you'd like to contribute to this project:

1. Fork the repository
2. Create a new branch (`git checkout -b feature/your-feature`)
3. Make your changes
4. Commit your changes (`git commit -m 'Add some feature'`)
5. Push to the branch (`git push origin feature/your-feature`)
6. Open a Pull Request

---

## Support

If you encounter any issues not covered in this README:

1. Check the [Issues](https://github.com/sanakhan8859/lokal/issues) page
2. Create a new issue with detailed information about your problem
3. Include error messages, screenshots, and system information

---

## License

This project's license information is not currently specified. Please check with the repository owner.

---

## Contact

For questions or suggestions, please open an issue on the GitHub repository.

---

**Happy Coding! 🚀**
