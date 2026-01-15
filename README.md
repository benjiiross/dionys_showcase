# Dionys 🍷

Note: This is a Showcase Repository. It contains the architecture, UI components, and technical patterns of the Dionys project. The full business logic and production assets are maintained in a private repository.

[![Kotlin](https://img.shields.io/badge/kotlin-%237F52FF.svg?style=for-the-badge&logo=kotlin&logoColor=white)](https://kotlinlang.org/)
[![Compose Multiplatform](https://img.shields.io/badge/Compose%20Multiplatform-v1.5.12-blue)](https://www.jetbrains.com/lp/compose-multiplatform/)
[![Platform](https://img.shields.io/badge/Platform-Android%20|%20iOS-blue)](#)

**Dionys** is a modern, cross-platform board game companion designed to digitize and enhance social
gaming experiences. Built with **Kotlin Multiplatform (KMP)** and **Compose Multiplatform**, it
delivers a seamless native experience on both Android and iOS from a single codebase.

The app features a **Neo-Brutalist** design and offers 4 distinct game modes categorized for various
social contexts: *Duo, Family, Friends and more*.

---

## ✨ Features

- 🎮 **4 Launch Game Modes:** Includes specialized logic for *Purple*, *Timeout*, and *Truth or
  Dare*.
- 🎨 **Neo-Brutalist UI:** A bold, high-contrast visual style implemented with Material 3.
- 👥 **Multi-Context Categories:** Tailored experiences for families, couples, or parties.
- 🚀 **Performant Animations:** Smooth transitions powered by **Compottie** (Lottie for Compose).
- 📱 **True Cross-Platform:** 100% shared business logic and UI across iOS and Android.

---

## 🛠 Tech Stack & Architecture

This project follows **Clean Architecture** principles to ensure scalability, maintainability, and
testability.

### Core Technologies

- **Language:** Kotlin
- **UI Framework:** Compose Multiplatform (Material 3)
- **Dependency Injection:** [Koin](https://insert-koin.io/)
- **Navigation:** Jetpack Compose Navigation (Multiplatform)
- **Asynchronous Programming:** Kotlin Coroutines
- **Animations:** Compottie (Lottie)
- **Quality Tools:** Detekt (Static Analysis), Ktfmt (Code Formatting)

### Architecture Overview

The code is organized into feature-based modules, each respecting the onion layers:

- **Domain:** Pure business logic and entity definitions.
- **UseCase:** Orchestrates the flow of data to and from the domain.
- **Infrastructure:** Data source implementations.
- **UI/ViewModel:** Reactive UI components and state management using `Lifecycle ViewModel`.

```text
src/commonMain/kotlin/.../dionys/games/[game_name]
├── domain          # Business Rules
├── usecase         # Application Logic
├── infrastructure  # Data & Repo Implementations
├── ui              # Compose Screens & Components
└── viewmodel       # State Management
```

## 📸 Screenshots & Demo

<p align="center">
  <img src="dionys_demo.gif" width="320" title="Dionys Demo Animation" alt="Dionys Demo Animation">
  <br>
  <i>Showcasing the Neo-Brutalist UI</i>
</p>

## 🚀 Getting Started

### Prerequisites

- **Android Studio Ladybug** (or newer)
- **Xcode** (for iOS builds)
- **JDK 11** or higher

### Installation

1. Clone the repository:

```bash
    git clone https://github.com/benjiiross/dionys.git
```

2. Open in Android Studio and sync Gradle.

3. Run the composeApp on your preferred device.

### Code Quality

Ensure the code meets the project's quality standards:

```bash
    ./gradlew detekt (Run static analysis)
    ./gradlew ktfmtCheck (Check formatting)
```

## 🤝 Collaboration

This project is developed in collaboration with [@yaelbuet-09](https://github.com/yaelbuet-09) (Product Design & Game Design).

Developed by Benjamin Rossignol - [Portfolio](https://benjaminrossignol.vercel.app/)
