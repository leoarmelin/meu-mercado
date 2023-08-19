# Expense Control App

The Expense Control App helps users manage expenses with categories and products. It simplifies expense tracking and includes a unique feature: web scraping for retrieving invoice data from the Brazilian government website (optimized for Paraná state). Even with large item lists, the app processes this quickly, providing a user-friendly experience.

## Key Features

- **Web Scraping for Invoice Data:** The app employs web scraping techniques to extract essential invoice data from the official website of the Brazilian government. This functionality is currently optimized for the state of Paraná.

- **Rapid Item Scanning:** The application boasts a lightning-fast item scanning mechanism. Even when dealing with a substantial number of items, the app swiftly processes the information, thereby providing users with a seamless experience.

## Architecture and Technologies

- **Multi-Modular Architecture:** The app is structured using a multi-modular architecture, incorporating the Model-View-ViewModel (MVVM) design pattern. This ensures a well-organized and maintainable codebase.

- **Jetpack Compose Framework:** The app's user interface (UI) is intuitive, dynamic, and aesthetically pleasing.

- **Dependency Injection with Hilt/Dagger:** Hilt and Dagger are employed for efficient dependency injection, enhancing the app's scalability and testability.

- **Data Parsing and Requests:** Moshi is utilized for seamless data parsing and Retrofit is the go-to solution for handling network requests.

- **Barcode Reading with Camera2 API and MLKit:** The app incorporates the Camera2 API and MLKit to enable accurate and efficient barcode reading, simplifying data entry.

- **Dynamic Animations with Lottie:** Lottie is employed to seamlessly integrate JSON animations.

- **Crash Analytics with Firebase:** Firebase is integrated into the app for robust crash analytics, making easier to address and resolve issues.

## Contributions and Feedback

Contributions to the **Expense Control App** are welcome! Whether you find a bug, want to suggest enhancements, or contribute directly to the codebase, please feel free to create issues and pull requests.
