import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tastesip.data.model.CocktailCategory
import com.example.tastesip.data.model.MealCategory
import com.example.tastesip.data.repository.CocktailRepository
import com.example.tastesip.data.repository.MealRepository
import com.example.tastesip.util.Resource
import kotlinx.coroutines.launch

class CategoryViewModel(
    private val mealRepository: MealRepository,
    private val cocktailRepository: CocktailRepository
) : ViewModel() {

    private val _mealCategories = MutableLiveData<Resource<List<MealCategory>>>()
    val mealCategories: LiveData<Resource<List<MealCategory>>> = _mealCategories

    private val _cocktailCategories = MutableLiveData<Resource<List<CocktailCategory>>>()
    val cocktailCategories: LiveData<Resource<List<CocktailCategory>>> = _cocktailCategories

    init {
        fetchMealCategories()
        fetchCocktailCategories()
    }

    private fun fetchMealCategories() {
        viewModelScope.launch {
            _mealCategories.value = mealRepository.getMealCategories()
        }
    }

    private fun fetchCocktailCategories() {
        viewModelScope.launch {
            _cocktailCategories.value = cocktailRepository.getCocktailCategories()
        }
    }
}