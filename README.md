# MindMaster Quiz App

> [!NOTE]
> The Quiz App is an application that allows users to participate in entertaining quizzes and test their knowledge.
> The app offers various categories of questions and a user-friendly interface. <br>
> Minimal [SDK version](https://apilevels.com) required to run the app: **24**

 ## Overview

- MindMaster is a Kotlin-based Android app quiz app where users can test their knowledge in various categories and be rewarded with different prizes.<br>
- The app is based on the developer's custom design and navigation.<br>
- For this purpose, the Quiz API from [Open Trivia DB](https://opentdb.com) was utilized.<br> Additionally, the app utilized a Joke API from    [Joke API](https://sv443.net/jokeapi/v2/).

 ## Screenshots

 <p float="left">
  <img src="/Screenshots/MindMaster.gif" width="200" />
  <img src="/Screenshots/Screenshot_20231115_093139.png" width="200" />
  <img src="/Screenshots/Screenshot_20231115_093210.png" width="200" />
  <img src="/Screenshots/Screenshot_20231115_093235.png" width="200" />
  <img src="/Screenshots/Screenshot_20231115_093249.png" width="200" />
  <img src="/Screenshots/Screenshot_20231115_093303.png" width="200" />
  <img src="/Screenshots/Screenshot_20231115_093315.png" width="200" />
  <img src="/Screenshots/Screenshot_20231115_093332.png" width="200" />
</p>

## Features

- Selection of different quiz categories.
- Display of multiple-choice questions.
- Awarding of points based on correct answers.
- Display of overall results at the end of the quiz.
- A reward (discount code) upon successful completion of the quiz.


## Tech Usage of Android
- **[MVVM Pattern](https://developer.android.com/topic/architecture)**
- **[Fragments](https://developer.android.com/guide/fragments)**
- **[ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel)**
- **[LiveData](https://developer.android.com/topic/libraries/architecture/livedata)**
- **[Navigation components](https://developer.android.com/guide/navigation/get-started)**
- **[Kapt](https://kotlinlang.org/docs/kapt.html)**
- **[RecyclerView](https://developer.android.com/reference/androidx/recyclerview/widget/RecyclerView)** 


## Libraries Usage
This app utilizes various third-party libraries and technologies:

- **[Android Architecture Components](https://developer.android.com/topic/architecture)** (ViewModel, LiveData)
- **[Room Database](https://developer.android.com/training/data-storage/room)** for caching
- **[Retrofit2](https://github.com/square/retrofit)** for network requests
- **[GSON](https://github.com/google/gson)** for JSON serialization/deserialization
- **[Material 3](https://m3.material.io)** components for UI


## Installation

1. Clone MindMaster Quiz App with **Git**

```git
  git clone https://github.com/Aliiiiii87/MindMaster.git [your path]

```

## Open the App

The app is available on GitHub for cloning and can be used on both Emulator(Android Studio) and mobile devices.


## Usage/Description

Upon opening the app, users briefly sign in with their email address and password. Subsequently, they land on the home screen, where they can view the results of completed quizzes. By clicking on an item, users receive a brief video review and a prize. There are 24 categories available, each offering 20 questions across three difficulty levels. Participants have 20 seconds to answer each question. Different points are awarded for each difficulty level.

## Levels and Pointsystem

- There are 3 difficulty levels and 24 categories.
- easy, medium ,hard

For each difficulty level, there are different points awarded for each correct answer.

- easy   100 Points
- medium 200 Points
- hard   300 Points

There are a total of 20 questions per quiz, with 20 seconds per question.

## Coloborations

 https://github.com/NoctRise

## Authors

[Ali Onur Aksoy](https://github.com/Aliiiiii87)
