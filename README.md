# Taste&Sip
An Android app for discovering food and cocktail recipes. It fetches real-time recipe data from the [TheMealDB](https://www.themealdb.com/) and [TheCocktailDB](https://www.thecocktaildb.com/) APIs.

<p align="center">
  <img src="https://github.com/user-attachments/assets/df4b4eca-5c5e-46cf-a36b-6a30d45e5871" alt="Taste&Sip app demo" width="280"/>
</p>

---

## Features
- Browse food recipes by category and view their details
- Browse cocktail recipes by category and view their details
- Category selection in a grid view
- Recipe details include image, ingredients, and preparation instructions
- Loading animation powered by Lottie
- Network errors surfaced to the user via Snackbar

---

## Screenshots
<p align="center">
  <img src="https://github.com/user-attachments/assets/7302457e-b8b4-49da-ac75-628b9b8ee8cc" alt="Home screen" width="200"/>
  <img src="https://github.com/user-attachments/assets/ac32285f-8a70-476f-bda8-738acabe20de" alt="Food category screen" width="200"/>
  <img src="https://github.com/user-attachments/assets/2a5f8627-ed30-4e31-afda-d65a16d732c6" alt="Food list" width="200"/>
  <img src="https://github.com/user-attachments/assets/5917c569-8a62-418e-9716-1d91c5980d2c" alt="Recipe detail" width="200"/>
</p>
<p align="center">
  <img src="https://github.com/user-attachments/assets/df33402b-3d62-4570-9fdf-fc13a0985cc4" alt="Cocktail category screen" width="200"/>
  <img src="https://github.com/user-attachments/assets/c3586ee7-5285-42ab-ac88-131b750c5c99" alt="Cocktail list" width="200"/>
  <img src="https://github.com/user-attachments/assets/a0d21bbf-04ed-4fd1-89c6-c83e07f0e7fa" alt="Recipe detail" width="200"/>
</p>

---

## Screen Flow
```
Home Screen (Meal / Cocktail)
    ├── Food Categories → Food List → Food Detail
    └── Cocktail Categories → Cocktail List → Cocktail Detail
```

---

## Tech Stack
| Category | Technology |
|----------|-----------|
| Language | Kotlin |
| UI | View Binding, Material Design, XML Layouts |
| Architecture | MVVM + Repository Pattern |
| Networking | Retrofit 2, Gson |
| Async | Kotlin Coroutines |
| Navigation | Navigation Component, Safe Args |
| Image loading | Picasso |
| Animation | Lottie |
| Lists | RecyclerView, DiffUtil |

---

## Project Structure
```
app/src/main/java/com/example/tastesip/
├── data/
│   ├── api/           # Retrofit services
│   ├── model/         # Data models
│   └── repository/    # MealRepository, CocktailRepository
├── ui/
│   ├── adapter/       # RecyclerView adapters
│   ├── fragment/      # Screen fragments
│   ├── viewmodel/     # ViewModels and Factory classes
│   └── MainActivity.kt
└── util/              # Resource wrapper, Constants, DiffUtil
```
