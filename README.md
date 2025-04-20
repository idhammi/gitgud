# GitGud

GitGud is an Android application built with Jetpack Compose that allows users to search GitHub profiles, view detailed user information, and explore their public repositories. The app is modular, supports offline mode.

---

## 🏗️ Modular Project Structure

| Module                | Description                                      |
|-----------------------|--------------------------------------------------|
| `:app`                | Application entry point, handles navigation and dependency graph |
| `:core:model`         | Domain data models shared across modules         |
| `:core:network`       | Retrofit API services and DTOs                   |
| `:core:database`      | Room database, DAO interfaces, and entities      |
| `:core:data`          | Repository implementations and data mappers      |
| `:core:common`        | Shared utilities                                 |
| `:core:designsystem`  | Shared base components and theme definitions     |
| `:core:ui`            | Shared UI components                             |
| `:feature:search`     | Handles user search and user list screen         |
| `:feature:user`       | Manages user detail screen and repositories list |

---

## 🚀 Tech Stack

- ✅ Kotlin & Jetpack Compose
- ✅ Retrofit + Moshi (network layer)
- ✅ Room Database (offline caching)
- ✅ Kotlin Coroutines + Flow
- ✅ Hilt (dependency injection)
- ✅ MVI-lite architecture
- ✅ Gradle Kotlin DSL & Modularization

---

## ✨ Features

- 🔍 Search GitHub users by username
- 📄 Display list of search results
- 👤 View detailed user profiles
- 📦 Browse public repositories of a user
- 📶 Offline support using Room cache (except for search, repository list, for now)
- ⚠️ Error handling and empty state UI
- 🔁 Debounced search input

---

## 🧪 Testing

- ✅ **Unit Tests** for ViewModel and Repository (using Mockito)
- ✅ **UI Tests** with Jetpack Compose UI Testing framework
- ✅ **MockWebServer** for mocking API responses

---

- 📌 Minimum SDK: 21
- 🎯 Target SDK: 35

---

## 📋 Developer Notes
- The architecture is scalable and open to future enhancements like pagination, theming, and recent search history

---
