# Taste&Sip

Yemek ve kokteyl tariflerini keşfetmenizi sağlayan Android uygulaması. [TheMealDB](https://www.themealdb.com/) ve [TheCocktailDB](https://www.thecocktaildb.com/) API'lerinden gerçek zamanlı tarif verileri çeker.

<p align="center">
  <img src="https://github.com/user-attachments/assets/df4b4eca-5c5e-46cf-a36b-6a30d45e5871" alt="Taste&Sip uygulama demosu" width="280"/>
</p>

## Özellikler

- Yemek tariflerini kategorilere göre listeleme ve detay görüntüleme
- Kokteyl tariflerini kategorilere göre listeleme ve detay görüntüleme
- Grid görünümünde kategori seçimi
- Tarif detaylarında görsel, malzemeler ve hazırlık talimatları
- Lottie ile yükleme animasyonu
- Ağ hatalarında Snackbar ile kullanıcı bilgilendirmesi

## Ekran Görüntüleri

<p align="center">
  <img src="https://github.com/user-attachments/assets/7302457e-b8b4-49da-ac75-628b9b8ee8cc" alt="Ana ekran" width="200"/>
  <img src="https://github.com/user-attachments/assets/ac32285f-8a70-476f-bda8-738acabe20de" alt="Yemek Kategori ekranı" width="200"/>
  <img src="https://github.com/user-attachments/assets/2a5f8627-ed30-4e31-afda-d65a16d732c6" alt="Yemek listesi" width="200"/>
  <img src="https://github.com/user-attachments/assets/5917c569-8a62-418e-9716-1d91c5980d2c" alt="Tarif detayı" width="200"/>
</p>

<p align="center">
  <img src="https://github.com/user-attachments/assets/df33402b-3d62-4570-9fdf-fc13a0985cc4" alt="Kokteyl Kategori ekranı" width="200"/>
  <img src="https://github.com/user-attachments/assets/c3586ee7-5285-42ab-ac88-131b750c5c99" alt="Kokteyl listesi" width="200"/>
  <img src="https://github.com/user-attachments/assets/a0d21bbf-04ed-4fd1-89c6-c83e07f0e7fa" alt="Tarif detayı" width="200"/>
</p>

## Ekran Akışı

```
Ana Ekran (Meal / Cocktail)
    ├── Yemek Kategorileri → Yemek Listesi → Yemek Detayı
    └── Kokteyl Kategorileri → Kokteyl Listesi → Kokteyl Detayı
```

## Teknolojiler

| Kategori | Teknoloji |
|----------|-----------|
| Dil | Kotlin |
| UI | View Binding, Material Design, XML Layouts |
| Mimari | MVVM + Repository Pattern |
| Ağ | Retrofit 2, Gson |
| Asenkron | Kotlin Coroutines |
| Navigasyon | Navigation Component, Safe Args |
| Görsel | Picasso |
| Animasyon | Lottie |
| Liste | RecyclerView, DiffUtil |

## Proje Yapısı

```
app/src/main/java/com/example/tastesip/
├── data/
│   ├── api/           # Retrofit servisleri
│   ├── model/         # Veri modelleri
│   └── repository/    # MealRepository, CocktailRepository
├── ui/
│   ├── adapter/       # RecyclerView adapter'ları
│   ├── fragment/      # Ekran fragment'leri
│   ├── viewmodel/     # ViewModel'ler ve Factory sınıfları
│   └── MainActivity.kt
└── util/              # Resource wrapper, Constants, DiffUtil
```

